package com.example.our_ebd.controller;

import com.example.our_ebd.model.Pessoa;
import com.example.our_ebd.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Pessoa> buscarPorId(@PathVariable Long id) {
        return pessoaRepository.findById(id);
    }

    @GetMapping("/matricula/{matricula}")
    public Optional<Pessoa> buscarPorMatricula(@PathVariable Integer matricula) {
        return pessoaRepository.findByMatricula(matricula);
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoa.setNome(pessoaAtualizada.getNome());
            pessoa.setDataDeNascimento(pessoaAtualizada.getDataDeNascimento());
            pessoa.setMatricula(pessoaAtualizada.getMatricula());
            return pessoaRepository.save(pessoa);
        }).orElseGet(() -> {
            pessoaAtualizada.setId(id);
            return pessoaRepository.save(pessoaAtualizada);
        });
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pessoaRepository.deleteById(id);
    }
}
