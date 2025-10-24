package com.example.our_ebd.repository;

import com.example.our_ebd.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByTurmaId(Long turmaId);
    List<Atividade> findByProfessorId(Long professorId);
}
