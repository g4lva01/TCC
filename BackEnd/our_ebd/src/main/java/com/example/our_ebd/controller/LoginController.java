package com.example.our_ebd.controller;

import com.example.our_ebd.dto.*;
import com.example.our_ebd.model.*;
import com.example.our_ebd.repository.*;
import com.example.our_ebd.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
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

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private PresencaRepository presencaRepository;

    private void vincularPerfilAluno(Pessoa pessoa) {
        Perfil perfilAluno = perfilRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Perfil aluno não encontrado"));

        PessoaPerfil vinculo = new PessoaPerfil(pessoa, perfilAluno);
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

        String token = jwtService.gerarToken(usuario);

        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "nome", usuario.getPessoa().getNome(),
                "roles", usuario.getRoles(),
                "id", usuario.getPessoa().getId()
        ));
    }

    @Transactional
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

        int idade = Period.between(pessoa.getDataDeNascimento(), LocalDate.now()).getYears();

        Turma turma = turmaRepository.findAll().stream() .filter(
                t -> t.getLimiteDeIdade() == null || idade <= t.getLimiteDeIdade())
                .min(Comparator.comparingInt(t -> t.getLimiteDeIdade() == null ? Integer.MAX_VALUE : t.getLimiteDeIdade()))
                .orElseThrow(() -> new RuntimeException("Nenhuma turma compatível com a idade"));
        pessoa.setTurma(turma);

        pessoaRepository.save(pessoa);

        UsuarioAutenticacao usuario = new UsuarioAutenticacao();
        usuario.setPessoa(pessoa);
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRoles(List.of("ROLE_ALUNO"));
        usuarioAutenticacaoRepository.save(usuario);

        vincularPerfilAluno(pessoa);

        return ResponseEntity.ok(Map.of("message", "Login criado com sucesso!"));
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

        return ResponseEntity.ok(Map.of("message", "Senha atualizada com sucesso!"));
    }

    @PutMapping("/perfil")
    @Transactional
    public ResponseEntity<?> atualizarPerfis(@RequestBody AtualizarPerfisRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        UsuarioAutenticacao usuario = usuarioAutenticacaoRepository.findByPessoa(pessoa)
                .orElseThrow(() -> new RuntimeException("Usuário de autenticação não encontrado"));

        Set<Long> novosIds = new HashSet<>(request.getPerfilIds());
        List<PessoaPerfil> atuais = pessoaPerfilRepository.findByPessoa(pessoa);
        Set<Long> atuaisIds = atuais.stream()
                .map(pp -> pp.getPerfil().getId())
                .collect(Collectors.toSet());

        Set<Long> adicionar = new HashSet<>(novosIds);
        adicionar.removeAll(atuaisIds);

        Set<Long> remover = new HashSet<>(atuaisIds);
        remover.removeAll(novosIds);

        for (Long id : adicionar) {
            Perfil perfil = perfilRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + id));
            pessoaPerfilRepository.save(new PessoaPerfil(pessoa, perfil));

            String role = "ROLE_" + perfil.getNome().toUpperCase();
            if (!usuario.getRoles().contains(role)) {
                usuario.getRoles().add(role);
            }
        }

        for (PessoaPerfil pp : atuais) {
            if (remover.contains(pp.getPerfil().getId())) {
                pessoaPerfilRepository.delete(pp);

                String role = "ROLE_" + pp.getPerfil().getNome().toUpperCase();
                usuario.getRoles().remove(role);
            }
        }

        usuarioAutenticacaoRepository.save(usuario);

        return ResponseEntity.ok(Map.of("message", "Perfis e roles atualizados com sucesso!"));
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

    @GetMapping("/pesquisar")
    public ResponseEntity<?> pesquisar(@RequestParam String query) {
        Optional<Pessoa> aluno;

        try {
            Integer matricula = Integer.parseInt(query);
            aluno = pessoaRepository.findByMatricula(matricula);
        } catch (NumberFormatException e) {
            aluno = pessoaRepository.findByNomeIgnoreCase(query);
        }

        if (aluno.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado.");
        }

        Pessoa p = aluno.get();

        Map<String, Object> resultado = Map.of(
                "id", p.getId(),
                "nome", p.getNome(),
                "matricula", p.getMatricula(),
                "dtNascimento", p.getDataDeNascimento()
        );

        return ResponseEntity.ok(resultado);
    }

    @Transactional
    @DeleteMapping("/desmatricular/{id}")
    public ResponseEntity<?> desmatricularAluno(@PathVariable Long id) {
        Pessoa aluno = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        // Apaga perfis e login
        pessoaPerfilRepository.deleteByPessoa(aluno);
        usuarioAutenticacaoRepository.findByPessoa(aluno)
                .ifPresent(usuarioAutenticacaoRepository::delete);
        // Apaga histórico de presenças
        presencaRepository.deleteByAluno(aluno);
        // Agora pode apagar o aluno
        pessoaRepository.delete(aluno);

        return ResponseEntity.ok(Map.of("message", "Aluno e histórico excluídos com sucesso!"));
    }


    @Transactional
    @PostMapping("/matricular")
    public ResponseEntity<?> matricularAluno(@RequestBody MatricularAlunoRequest request) {
        if (pessoaRepository.findByMatricula(request.getMatricula()).isPresent()) {
            return ResponseEntity.badRequest().body("Matrícula já cadastrada.");
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setDataDeNascimento(request.getDtNascimento());
        pessoa.setMatricula(request.getMatricula());

        int idade = Period.between(pessoa.getDataDeNascimento(), LocalDate.now()).getYears();

        Turma turma = turmaRepository.findAll().stream()
                .filter(t -> t.getLimiteDeIdade() == null || idade <= t.getLimiteDeIdade())
                .min(Comparator.comparingInt(t -> t.getLimiteDeIdade() == null ? Integer.MAX_VALUE : t.getLimiteDeIdade()))
                .orElseThrow(() -> new RuntimeException("Nenhuma turma compatível com a idade"));

        pessoa.setTurma(turma);
        pessoaRepository.save(pessoa);

        vincularPerfilAluno(pessoa);

        Map<String, Object> resultado = Map.of(
                "id", pessoa.getId(),
                "nome", pessoa.getNome(),
                "matricula", pessoa.getMatricula(),
                "dtNascimento", pessoa.getDataDeNascimento()
        );

        return ResponseEntity.ok(resultado);
    }
}
