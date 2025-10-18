package com.example.our_ebd.service;

import com.example.our_ebd.model.UsuarioAutenticacao;
import com.example.our_ebd.repository.UsuarioAutenticacaoRepository;
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

    @Override
    public UserDetails loadUserByUsername(String matriculaStr) throws UsernameNotFoundException {
        Integer matricula = Integer.parseInt(matriculaStr);
        UsuarioAutenticacao usuario = usuarioRepo.findByPessoaMatricula(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        List<SimpleGrantedAuthority> authorities = usuario.getPessoa().getPerfis().stream()
                .map(p -> new SimpleGrantedAuthority("ROLE_" + p.getPerfil().getNome().toUpperCase()))
                .toList();

        return new User(matriculaStr, usuario.getSenha(), authorities);
    }
}
