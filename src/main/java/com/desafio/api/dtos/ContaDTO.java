package com.desafio.api.dtos;

import com.desafio.api.constants.SituacaoConta;
import com.desafio.api.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {

    private Long id;
    private String referencia;
    private Double valor;
    private SituacaoConta situacao;
    private ClienteDTO cliente;

    public Conta toModal(){
        Conta conta = new Conta();

        conta.setId(this.id);
        conta.setSituacao(this.situacao);
        conta.setReferencia(this.referencia);
        conta.setValor(this.valor);
        if (this.cliente != null) {
            conta.setCliente(this.cliente.toModal());
        }

        return conta;
    }
}
