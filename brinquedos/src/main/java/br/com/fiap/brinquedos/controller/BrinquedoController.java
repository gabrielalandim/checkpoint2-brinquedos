package br.com.fiap.brinquedos.controller;

import br.com.fiap.brinquedos.model.Brinquedo;
import br.com.fiap.brinquedos.repository.BrinquedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brinquedos")
public class BrinquedoController {

    @Autowired
    private BrinquedoRepository repository;

    // CREATE: Criar um novo brinquedo (POST)
    @PostMapping
    public ResponseEntity<Brinquedo> criarBrinquedo(@RequestBody Brinquedo brinquedo) {
        Brinquedo novoBrinquedo = repository.save(brinquedo);
        return new ResponseEntity<>(novoBrinquedo, HttpStatus.CREATED);
    }

    // READ: Buscar todos os brinquedos (GET)
    @GetMapping
    public ResponseEntity<List<Brinquedo>> listarTodos() {
        List<Brinquedo> brinquedos = repository.findAll();
        return ResponseEntity.ok(brinquedos);
    }
}
