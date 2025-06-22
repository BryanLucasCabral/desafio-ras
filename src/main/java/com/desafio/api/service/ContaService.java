package com.desafio.api.service;

import com.desafio.api.constants.SituacaoConta;
import com.desafio.api.dtos.ContaDTO;
import com.desafio.api.exception.CampoIncorretoException;
import com.desafio.api.exception.ConsultaInvalidaException;
import com.desafio.api.model.Cliente;
import com.desafio.api.model.Conta;
import com.desafio.api.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaService {

    public ContaDTO cadastrarConta(Long idCliente, ContaDTO contaDTO){

        Conta conta = contaDTO.toModal();
        Cliente cliente = clienteService.buscarClientePorId(idCliente).toModal();

        conta.setCliente(cliente);

        validarConta(conta.toDTO());
        return contaRepository.save(conta).toDTO();
    }

    public ContaDTO atualizarConta(Long id, ContaDTO contaDTO){

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ConsultaInvalidaException("Não foi encontrado conta com esse ID:" + id));

        conta.setReferencia(contaDTO.getReferencia());
        conta.setValor(contaDTO.getValor());
        conta.setSituacao(contaDTO.getSituacao());

        validarConta(conta.toDTO());

        return contaRepository.save(conta).toDTO();
    }

    public void deletarConta(Long id){

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ConsultaInvalidaException("Não foi encontrado conta com esse ID:" + id));

        conta.setSituacao(SituacaoConta.CANCELADA);
        contaRepository.save(conta);
    }

    public List<ContaDTO> listarContasPeloIdCliente(Long id){

        clienteService.buscarClientePorId(id);

        List<ContaDTO> contas = contaRepository.findByClienteId(id).stream().map(Conta::toDTO).collect(Collectors.toList());

        return contas;
    }

    public ContaDTO buscarContaPeloId(Long id){

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ConsultaInvalidaException("Não foi encontrado conta com esse ID:" + id));

        return conta.toDTO();
    }

    void validarConta(ContaDTO contaDTO){
        if (contaDTO.getValor() < 0){
            throw new CampoIncorretoException("Não foi possível criar uma conta com o valor menor que 0.");
        }
        if (contaDTO.getCliente() == null){
            throw new CampoIncorretoException("Não foi possível criar uma conta sem um cliente associado.");
        }
        if (contaDTO.getSituacao() == SituacaoConta.CANCELADA){
            throw new CampoIncorretoException("Não foi possível criar uma conta com a situação: " + SituacaoConta.CANCELADA);
        }
    }

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteService clienteService;
}
