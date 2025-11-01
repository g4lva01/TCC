package com.example.our_ebd.repository;

import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.model.PessoaPerfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaPerfilRepository extends JpaRepository<PessoaPerfil, Long> {
    boolean existsByPessoaIdAndPerfilId(Long pessoaId, Long perfilId);
    void deleteByPessoa(Pessoa pessoa);
    List<PessoaPerfil> findByPessoa(Pessoa pessoa);
}
