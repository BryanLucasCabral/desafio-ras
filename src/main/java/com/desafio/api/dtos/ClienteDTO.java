package com.desafio.api.dtos;

import com.desafio.api.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    public Cliente toModal() {
        Cliente cliente = new Cliente();

        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setEmail(this.email);
        cliente.setTelefone(this.telefone);

        return cliente;
    }
}
