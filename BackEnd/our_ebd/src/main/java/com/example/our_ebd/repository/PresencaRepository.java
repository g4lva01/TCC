package com.example.our_ebd.repository;

import com.example.our_ebd.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByChamadaId(Long chamadaId);
}
