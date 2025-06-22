package com.desafio.api.model;

import com.desafio.api.constants.SituacaoConta;
import com.desafio.api.dtos.ContaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_CONTA")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA")
    private Long id;

    @NotNull(message = "O Campo Referência é Obrigatório.")
    @Column(name = "REFERENCIA_CONTA", nullable = false)
    @Pattern(regexp = "^(0[1-9]|1[0-2])-(\\d{4})$", message = "Formato inválido. Use MM-AAAA ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy")
    private String referencia;

    @NotNull(message = "O Campo Valor é Obrigatório.")
    @Column(name = "VALOR_CONTA", nullable = false)
    private Double valor;

    @NotNull(message = "O Campo Situação é Obrigatório.")
    @Column(name = "SITUACAO_CONTA", nullable = false)
    private SituacaoConta situacao;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID", nullable = false)
    private Cliente cliente;

    public ContaDTO toDTO(){
        ContaDTO dto = new ContaDTO();

        dto.setId(id);
        dto.setReferencia(referencia);
        dto.setValor(valor);
        dto.setSituacao(situacao);
        dto.setCliente(cliente.toDTO());

        return dto;
    }
}
