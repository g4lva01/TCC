package com.example.our_ebd.repository;

import com.example.our_ebd.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByTurmaId(Long turmaId);
    List<Atividade> findByProfessorId(Long professorId);
    boolean existsByTituloAndNumeroLicaoAndTurmaId(String titulo, Integer numeroLicao, Long turmaId);
    Optional<Atividade> findByTituloAndNumeroLicaoAndTurmaId(String titulo, Integer numeroLicao, Long turmaId);
}
