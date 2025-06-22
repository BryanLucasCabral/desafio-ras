package com.desafio.api.controller;

import com.desafio.api.dtos.ClienteDTO;
import com.desafio.api.model.Cliente;
import com.desafio.api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrarCliente(clienteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@Valid @PathVariable("id") Long id, @Valid @RequestBody ClienteDTO dadosClienteDTO){
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(id);

        if (Objects.isNull(clienteDTO)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(clienteService.atualizarCliente(id, dadosClienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@Valid @PathVariable("id") Long id){
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(id);

        if (Objects.isNull(clienteDTO)){
            return ResponseEntity.notFound().build();
        }

        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listarClientes(@Valid @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)Pageable paginacao){
        return ResponseEntity.ok().body(clienteService.listarClientes(paginacao));
    }

    @Autowired
    private ClienteService clienteService;
}
