package com.example.our_ebd.repository;

import com.example.our_ebd.dto.AlunoFrequenciaDTO;
import com.example.our_ebd.dto.FaltasDiaDTO;
import com.example.our_ebd.dto.TurmaFrequenciaDTO;
import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByChamadaId(Long chamadaId);
    long countByAluno_IdAndChamada_Turma_IdAndPresenteTrue(Long alunoId, Long turmaId);
    long countByAluno_IdAndChamada_Turma_IdAndLevouBibliaTrue(Long alunoId, Long turmaId);
    @Query("SELECT DISTINCT p.chamada.turma.id FROM Presenca p WHERE p.aluno.id = :alunoId")
    List<Long> findDistinctTurmaIdsByAlunoId(@Param("alunoId") Long alunoId);
    List<Presenca> findByAluno(Pessoa nome);
    List<Presenca> findByAlunoAndChamada_DataChamadaBetween(Pessoa aluno, LocalDate inicio, LocalDate fim);

    @Query(value = "SELECT p.aluno_id AS alunoId, a.nome AS nomeAluno, " +
            "SUM(CASE WHEN p.presente = true THEN 1 ELSE 0 END) AS presencas, " +
            "SUM(CASE WHEN p.presente = false THEN 1 ELSE 0 END) AS faltas " +
            "FROM presenca p " +
            "JOIN pessoa a ON a.id = p.aluno_id " +
            "JOIN chamada c ON c.id = p.chamada_id " +
            "WHERE c.data_chamada BETWEEN :inicio AND :fim " +
            "GROUP BY p.aluno_id, a.nome " +
            "ORDER BY presencas DESC",
            nativeQuery = true)
    List<AlunoFrequenciaDTO> findTopPresencas(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT new com.example.our_ebd.dto.AlunoFrequenciaDTO(p.aluno.id, p.aluno.nome, " +
            "COUNT(CASE WHEN p.presente = true THEN 1 ELSE 0 END), " +
            "COUNT(CASE WHEN p.presente = false THEN 1 ELSE 0 END)) " +
            "FROM Presenca p " +
            "WHERE p.chamada.dataChamada BETWEEN :inicio AND :fim " +
            "GROUP BY p.aluno.id, p.aluno.nome " +
            "ORDER BY SUM(CASE WHEN p.presente = false THEN 1 ELSE 0 END) DESC")
    List<AlunoFrequenciaDTO> findTopFaltas(@Param("inicio") LocalDate inicio,  @Param("fim") LocalDate fim);

    @Query("SELECT new com.example.our_ebd.dto.TurmaFrequenciaDTO(c.turma.id, c.turma.nome, " +
            "AVG(CASE WHEN p.presente = true THEN 1 ELSE 0 END)) " +
            "FROM Presenca p " +
            "JOIN p.chamada c " +
            "WHERE c.dataChamada BETWEEN :inicio AND :fim " +
            "GROUP BY c.turma.id, c.turma.nome " +
            "ORDER BY AVG(CASE WHEN p.presente = true THEN 1 ELSE 0 END) DESC")
    List<TurmaFrequenciaDTO> findTurmasComMaiorFrequencia(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT new com.example.our_ebd.dto.FaltasDiaDTO(c.dataChamada, " +
            "COUNT(CASE WHEN p.presente = false THEN 1 ELSE 0 END)) " +
            "FROM Presenca p " +
            "JOIN p.chamada c " +
            "WHERE c.dataChamada BETWEEN :inicio AND :fim " +
            "GROUP BY c.dataChamada " +
            "ORDER BY SUM(CASE WHEN p.presente = false THEN 1 ELSE 0 END) DESC")
    List<FaltasDiaDTO> findDiasComMaisFaltas(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    Optional<Presenca> findByAlunoAndChamada(Pessoa alunoId, Chamada chamada);
    long countByAlunoAndPresenteTrueAndChamada_DataChamadaBetween(Pessoa aluno, LocalDate inicio, LocalDate fim);
    void deleteByAluno(Pessoa aluno);
    void deleteByChamada(Chamada chamada);
}
