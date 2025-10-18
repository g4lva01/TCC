package com.example.our_ebd.repository;

import com.example.our_ebd.model.UsuarioAutenticacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioAutenticacaoRepository extends JpaRepository<UsuarioAutenticacao, Long> {
    Optional<UsuarioAutenticacao> findByPessoaMatricula(Integer matricula);
}
