package com.example.our_ebd.controller;

import com.example.our_ebd.model.Perfil;
import com.example.our_ebd.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfis")
@CrossOrigin(origins = "*")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping("/{id}")
    public Perfil criar(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @PutMapping("/{id}")
    public Perfil atualizar(@PathVariable Long id, @RequestBody Perfil perfilAtualizado) {
        return perfilRepository.findById(id).map(perfil -> {
            perfil.setNome(perfilAtualizado.getNome());
            return perfilRepository.save(perfil);
        }).orElseGet(()-> {
            perfilAtualizado.setId(id);
            return perfilRepository.save(perfilAtualizado);
        });
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        perfilRepository.deleteById(id);
    }
}
