package com.example.our_ebd.service;

import com.example.our_ebd.model.UsuarioAutenticacao;
import com.example.our_ebd.repository.UsuarioAutenticacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioAutenticacaoRepository usuarioRepo;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UsuarioAutenticacao usuario;

        try {
            Integer matricula = Integer.parseInt(login);
            usuario = usuarioRepo.findByPessoaMatricula(matricula)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado pela matrícula: " + matricula));
        } catch (NumberFormatException e) {
            usuario = usuarioRepo.findByPessoa_NomeIgnoreCase(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado pelo login: " + login));
        }

        List<SimpleGrantedAuthority> authorities = usuario.getPessoa().getPerfis().stream()
                .map(p -> new SimpleGrantedAuthority("ROLE_" + p.getPerfil().getNome().toUpperCase()))
                .toList();

        return new User(login, usuario.getSenha(), authorities);
    }
}
