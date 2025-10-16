package com.example.our_ebd.repository;

import com.example.our_ebd.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByMatricula(Integer matricula);
}
