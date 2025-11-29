package com.example.our_ebd.repository;

import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChamadaRepository extends JpaRepository<Chamada, Long> {
    List<Chamada> findByTurmaId(Long turmaId);
    Optional<Chamada> findByTurmaAndDataChamada(Turma turma, LocalDate dataChamada);
    List<Chamada> findByDataChamada(LocalDate dataChamada);
}
