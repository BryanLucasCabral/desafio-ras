package com.desafio.api.service;

import ch.qos.logback.core.net.server.Client;
import com.desafio.api.dtos.ClienteDTO;
import com.desafio.api.exception.ConsultaInvalidaException;
import com.desafio.api.exception.CpfJaCadastradoException;
import com.desafio.api.model.Cliente;
import com.desafio.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO){

        validarCliente(clienteDTO);

        Cliente cliente = clienteDTO.toModal();

        return clienteRepository.save(cliente).toDTO();
    }

    public ClienteDTO buscarClientePorId(Long id){

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ConsultaInvalidaException("Não foi encontrado cliente para esse ID: " + id));

        return cliente.toDTO();
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO dadosCliente) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ConsultaInvalidaException("Não foi encontrado cliente para esse ID: " + id));

        cliente.setNome(dadosCliente.getNome());
        cliente.setEmail(dadosCliente.getEmail());
        cliente.setCpf(dadosCliente.getCpf());

        clienteRepository.save(cliente);
        return cliente.toDTO();
    }

    public void deletarCliente(Long id){

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ConsultaInvalidaException("Não foi encontrado cliente para esse ID: " + id));

        clienteRepository.deleteById(id);
    }

    public Page<ClienteDTO> listarClientes(Pageable paginacao){
        return clienteRepository.findAll(paginacao).map(Cliente::toDTO);
    }

    private void validarCliente(ClienteDTO clienteDTO){
        if (clienteRepository.existsByCpf(clienteDTO.getCpf())){
            throw new CpfJaCadastradoException("Já existe um cliente com este CPF cadastrado.");
        }
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())){
            throw new CpfJaCadastradoException("Já existe um cliente com este EMAIL cadastrado.");
        }
    }

    @Autowired
    private ClienteRepository clienteRepository;
}
