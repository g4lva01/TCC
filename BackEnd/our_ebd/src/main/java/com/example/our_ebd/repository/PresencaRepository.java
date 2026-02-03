package com.example.our_ebd.repository;

import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByChamadaId(Long chamadaId);
    long countByAluno_IdAndChamada_Turma_IdAndPresenteTrue(Long alunoId, Long turmaId);
    long countByAluno_IdAndChamada_Turma_IdAndLevouBibliaTrue(Long alunoId, Long turmaId);
    @Query("SELECT DISTINCT p.chamada.turma.id FROM Presenca p WHERE p.aluno.id = :alunoId")
    List<Long> findDistinctTurmaIdsByAlunoId(@Param("alunoId") Long alunoId);
    long countByAlunoAndPresenteTrueAndChamada_DataChamadaBetween(
            Pessoa aluno,
            LocalDate inicio,
            LocalDate fim
    );
    void deleteByAluno(Pessoa aluno);
}
