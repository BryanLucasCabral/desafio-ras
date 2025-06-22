package com.desafio.api.controller;

import com.desafio.api.dtos.ContaDTO;
import com.desafio.api.model.Conta;
import com.desafio.api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class ContaController {

    @PostMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<ContaDTO> cadastrarConta(@Valid @PathVariable("idCliente") Long id, @RequestBody ContaDTO contaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.cadastrarConta(id, contaDTO));
    }

    @PutMapping("/contas/{id}")
    public ResponseEntity<ContaDTO> atualizarConta(@Valid @PathVariable("id") Long id, @RequestBody ContaDTO dadosConta) {

        ContaDTO contaDTO = contaService.buscarContaPeloId(id);

        if (Objects.isNull(contaDTO)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(contaService.atualizarConta(id, dadosConta));
    }

    @DeleteMapping("/contas/{id}")
    public ResponseEntity<Void> deletarConta(@Valid @PathVariable("id") Long id) {
        ContaDTO contaDTO = contaService.buscarContaPeloId(id);

        if (Objects.isNull(contaDTO)) {
            return ResponseEntity.notFound().build();
        }

        contaService.deletarConta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<List<ContaDTO>> listarContas(@Valid @PathVariable("idCliente") Long id) {
        ContaDTO contaDTO = contaService.buscarContaPeloId(id);

        if (Objects.isNull(contaDTO)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(contaService.listarContasPeloIdCliente(id));
    }

    @Autowired
    private ContaService contaService;
}
