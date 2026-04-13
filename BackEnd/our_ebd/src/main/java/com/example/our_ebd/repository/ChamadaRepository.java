package com.example.our_ebd.repository;

import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChamadaRepository extends JpaRepository<Chamada, Long> {
    List<Chamada> findByTurmaId(Long turmaId);
    Optional<Chamada> findByTurmaAndDataChamada(Turma turma, LocalDate dataChamada);
    List<Chamada> findByDataChamada(LocalDate dataChamada);

    @Query("SELECT c.dataChamada FROM Chamada c " +
            "WHERE c.dataChamada BETWEEN :inicio AND :fim")
    List<LocalDate> findDatasChamadas(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT c.dataChamada FROM Chamada c " +
            "WHERE c.turma.nome = :nomeTurma " +
            "AND c.dataChamada BETWEEN :inicio AND :fim")
    List<LocalDate> findDatasChamadasPorTurma(@Param("nomeTurma") String nomeTurma, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
