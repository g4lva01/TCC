package com.example.our_ebd.repository;

import com.example.our_ebd.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByNomeIgnoreCase(String nome);
}
