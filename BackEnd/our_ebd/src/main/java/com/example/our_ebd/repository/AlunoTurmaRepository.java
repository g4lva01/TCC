package com.example.our_ebd.repository;

import com.example.our_ebd.model.AlunoTurma;
import com.example.our_ebd.model.AlunoTurmaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, AlunoTurmaId> {
}
