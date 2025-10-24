package com.example.our_ebd.controller;

import com.example.our_ebd.model.Chamada;
import com.example.our_ebd.repository.ChamadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamada")
@CrossOrigin(origins = "*")
public class ChamadaController {
    @Autowired
    private ChamadaRepository chamadaRepository;

    @PostMapping
    public ResponseEntity<?> registrarChamada(@RequestBody Chamada chamada) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAutorizado = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROFESSOR") || a.getAuthority().equals("ROLE_GESTOR"));

        if (!isAutorizado) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Apenas professores ou gestores podem registrar chamadas.");
        }

        return ResponseEntity.ok(chamadaRepository.save(chamada));
    }

    @GetMapping
    public List<Chamada> listarChamadas() {
        return chamadaRepository.findAll();
    }

    @GetMapping("/turma/{turmaId}")
    public List<Chamada> listarPorTurma(@PathVariable Long turmaId) {
        return chamadaRepository.findByTurmaId(turmaId);
    }
}
