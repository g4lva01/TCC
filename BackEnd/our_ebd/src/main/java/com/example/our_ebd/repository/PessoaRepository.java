package com.example.our_ebd.repository;

import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByMatricula(Integer matricula);
    Optional<Pessoa> findByNome(String nome);
    List<Pessoa> findByTurma(Turma turma);

    Optional<Pessoa> findByNomeIgnoreCase(String nome);
}
