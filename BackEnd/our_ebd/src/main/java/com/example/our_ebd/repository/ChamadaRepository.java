package com.example.our_ebd.repository;

import com.example.our_ebd.model.Chamada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadaRepository extends JpaRepository<Chamada, Long> {
    List<Chamada> findByTurmaId(Long turmaId);
}
