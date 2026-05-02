package com.example.our_ebd.repository;

import com.example.our_ebd.model.ChamadaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChamadaLogRepository extends JpaRepository<ChamadaLog, Long> {
    List<ChamadaLog> findByChamadaIdOrderByDataAlteracaoDesc(Long chamadaId);
}
