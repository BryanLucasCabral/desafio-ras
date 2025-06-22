package com.desafio.api.model;

import com.desafio.api.dtos.ClienteDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @NotNull(message = "O Campo Nome é Obrigatório.")
    @Column(name = "NOME_CLIENTE", nullable = false)
    private String nome;

    @NotNull(message = "O Campo Cpf é Obrigatório.")
    @Column(name = "CPF_CLIENTE", nullable = false, unique = true)
    private String cpf;

    @Column(name = "TELEFONE_CLIENTE")
    private String telefone;

    @Column(name = "EMAIL_CLIENTE")
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conta> contas;

    public ClienteDTO toDTO(){
        ClienteDTO dto = new ClienteDTO();

        dto.setId(id);
        dto.setCpf(cpf);
        dto.setNome(nome);
        dto.setTelefone(telefone);
        dto.setEmail(email);

        return dto;
    }
}
