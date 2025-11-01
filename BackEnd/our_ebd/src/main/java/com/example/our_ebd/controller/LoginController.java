package com.example.our_ebd.controller;

import com.example.our_ebd.dto.AlterarSenhaRequest;
import com.example.our_ebd.dto.AtualizarPerfisRequest;
import com.example.our_ebd.dto.CriarLoginRequest;
import com.example.our_ebd.dto.LoginRequest;
import com.example.our_ebd.model.Perfil;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.PessoaPerfil;
import com.example.our_ebd.model.UsuarioAutenticacao;
import com.example.our_ebd.repository.PerfilRepository;
import com.example.our_ebd.repository.PessoaPerfilRepository;
import com.example.our_ebd.repository.PessoaRepository;
import com.example.our_ebd.repository.UsuarioAutenticacaoRepository;
import com.example.our_ebd.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioAutenticacaoRepository usuarioAutenticacaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PessoaPerfilRepository pessoaPerfilRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    private void vincularPerfilAluno(Pessoa pessoa) {
        Perfil perfilAluno = perfilRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Perfil aluno não encontrado"));

        PessoaPerfil vinculo = new PessoaPerfil();
        vinculo.setPessoa(pessoa);
        vinculo.setPerfil(perfilAluno);

        pessoaPerfilRepository.save(vinculo);
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String identificador = request.getIdentificador();
        String senha = request.getSenha();

        UsuarioAutenticacao usuario;

        try {
            Integer matricula = Integer.parseInt(identificador);
            usuario = usuarioAutenticacaoRepository.findByPessoa_Matricula(matricula)
                    .orElse(null);
        } catch (NumberFormatException e) {
            usuario = usuarioAutenticacaoRepository.findByPessoa_NomeIgnoreCase(identificador)
                    .orElse(null);
        }

        if (usuario == null || !passwordEncoder.matches(senha, usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }

        String token = jwtService.gerarToken(String.valueOf(usuario));

        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "nome", usuario.getPessoa().getNome(),
                "roles", usuario.getRoles()
        ));
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarLogin(@RequestBody CriarLoginRequest request) {
        if (!request.getSenha().equals(request.getCfSenha())) {
            return ResponseEntity.badRequest().body("As senhas não coincidem.");
        }

        if (usuarioAutenticacaoRepository.existsByPessoa_Matricula(request.getMatricula())) {
            return ResponseEntity.badRequest().body("Matrícula já cadastrada.");
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setDataDeNascimento(request.getDtNascimento());
        pessoa.setMatricula(request.getMatricula());
        pessoaRepository.save(pessoa);

        UsuarioAutenticacao usuario = new UsuarioAutenticacao();
        usuario.setPessoa(pessoa);
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuarioAutenticacaoRepository.save(usuario);

        vincularPerfilAluno(pessoa);

        return ResponseEntity.ok("Login criado com sucesso!");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterarSenha(@RequestBody AlterarSenhaRequest request) {
        if (!request.getNovaSenha().equals(request.getConfirmarSenha())) {
            return ResponseEntity.badRequest().body("As senhas não coincidem.");
        }

        Optional<Pessoa> pessoaOpt;

        try {
            Integer matricula = Integer.parseInt(request.getIdentificador());
            pessoaOpt = pessoaRepository.findByMatricula(matricula);
        } catch (NumberFormatException e) {
            pessoaOpt = pessoaRepository.findByNome(request.getIdentificador());
        }

        if (pessoaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        Pessoa pessoa = pessoaOpt.get();
        Optional<UsuarioAutenticacao> usuarioOpt = usuarioAutenticacaoRepository.findByPessoa(pessoa);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário de autenticação não encontrado.");
        }

        UsuarioAutenticacao usuario = usuarioOpt.get();
        usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        usuarioAutenticacaoRepository.save(usuario);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }

    @PutMapping("/perfil")
    public ResponseEntity<?> atualizarPerfis(@RequestBody AtualizarPerfisRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        List<Perfil> perfis = perfilRepository.findAllById(request.getPerfilIds());

        pessoaPerfilRepository.deleteByPessoa(pessoa); // limpa perfis antigos
        for (Perfil perfil : perfis) {
            PessoaPerfil vinculo = new PessoaPerfil();
            vinculo.setPessoa(pessoa);
            vinculo.setPerfil(perfil);
            pessoaPerfilRepository.save(vinculo);
        }

        return ResponseEntity.ok("Perfis atualizados com sucesso!");
    }

    @GetMapping("/logins")
    public ResponseEntity<?> listarLogins() {
        List<UsuarioAutenticacao> usuarios = usuarioAutenticacaoRepository.findAll();

        List<Map<String, Object>> resultado = usuarios.stream().map(u -> {
            List<String> perfis = pessoaPerfilRepository.findByPessoa(u.getPessoa()).stream()
                    .map(pp -> pp.getPerfil().getNome())
                    .collect(Collectors.toList());

            return Map.of(
                    "id", u.getPessoa().getId(),
                    "nome", u.getPessoa().getNome(),
                    "matricula", u.getPessoa().getMatricula(),
                    "roles", u.getRoles(),
                    "perfis", perfis
            );
        }).toList();

        return ResponseEntity.ok(resultado);
    }
}
