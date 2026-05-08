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

    // 1. CREATE: Criar um novo brinquedo (POST)
    @PostMapping
    public ResponseEntity<Brinquedo> criarBrinquedo(@RequestBody Brinquedo brinquedo) {
        Brinquedo novoBrinquedo = repository.save(brinquedo);
        return new ResponseEntity<>(novoBrinquedo, HttpStatus.CREATED);
    }

    // 2. READ: Buscar todos os brinquedos (GET)
    @GetMapping
    public ResponseEntity<List<Brinquedo>> listarTodos() {
        List<Brinquedo> brinquedos = repository.findAll();
        return ResponseEntity.ok(brinquedos);
    }

    // 3. UPDATE: Atualizar um brinquedo existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Brinquedo> atualizarBrinquedo(@PathVariable Long id, @RequestBody Brinquedo brinquedoAtualizado) {
        return repository.findById(id)
                .map(brinquedo -> {
                    brinquedo.setNome(brinquedoAtualizado.getNome());
                    brinquedo.setTipo(brinquedoAtualizado.getTipo());
                    brinquedo.setClassificacao(brinquedoAtualizado.getClassificacao());
                    brinquedo.setTamanho(brinquedoAtualizado.getTamanho());
                    brinquedo.setPreco(brinquedoAtualizado.getPreco());
                    Brinquedo atualizado = repository.save(brinquedo);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. DELETE: Excluir um brinquedo pelo ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBrinquedo(@PathVariable Long id) {
        return repository.findById(id)
                .map(brinquedo -> {
                    repository.delete(brinquedo);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
