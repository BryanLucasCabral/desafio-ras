package com.desafio.api.service;

import com.desafio.api.dtos.ClienteDTO;
import com.desafio.api.exception.ConsultaInvalidaException;
import com.desafio.api.exception.CpfJaCadastradoException;
import com.desafio.api.model.Cliente;
import com.desafio.api.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Teste");
        cliente.setEmail("teste@teste.com");
        cliente.setCpf("12345678900");

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Teste");
        clienteDTO.setEmail("teste@teste.com");
        clienteDTO.setCpf("12345678900");
    }

    @Test
    @DisplayName("Deve retornar ClienteDTO ao cadastrar com dados válidos")
    void testCadastrarCliente_DeveRetornarClienteDTO_QuandoDadosValidos() {
        when(clienteRepository.existsByCpf(any())).thenReturn(false);
        when(clienteRepository.existsByEmail(any())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO resultado = clienteService.cadastrarCliente(clienteDTO);

        assertNotNull(resultado);
        assertEquals(clienteDTO.getNome(), resultado.getNome());
        assertEquals(clienteDTO.getEmail(), resultado.getEmail());
        assertEquals(clienteDTO.getCpf(), resultado.getCpf());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com CPF já existente")
    void testCadastrarCliente_DeveLancarExcecao_QuandoCpfJaCadastrado() {
        when(clienteRepository.existsByCpf(any())).thenReturn(true);

        assertThrows(CpfJaCadastradoException.class, () -> clienteService.cadastrarCliente(clienteDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar com email já existente")
    void testCadastrarCliente_DeveLancarExcecao_QuandoEmailJaCadastrado() {
        when(clienteRepository.existsByCpf(any())).thenReturn(false);
        when(clienteRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(CpfJaCadastradoException.class, () -> clienteService.cadastrarCliente(clienteDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve retornar ClienteDTO ao buscar por ID existente")
    void testBuscarClientePorId_DeveRetornarClienteDTO_QuandoIdExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteDTO resultado = clienteService.buscarClientePorId(1L);

        assertNotNull(resultado);
        assertEquals(cliente.getNome(), resultado.getNome());
        assertEquals(cliente.getEmail(), resultado.getEmail());
        assertEquals(cliente.getCpf(), resultado.getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por ID inexistente")
    void testBuscarClientePorId_DeveLancarExcecao_QuandoIdInexistente() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ConsultaInvalidaException.class, () -> clienteService.buscarClientePorId(1L));
    }

    @Test
    @DisplayName("Deve retornar cliente atualizado com dados válidos")
    void testAtualizarCliente_DeveRetornarClienteAtualizado_QuandoDadosValidos() {
        ClienteDTO dadosAtualizados = new ClienteDTO();
        dadosAtualizados.setNome("João Silva Atualizado");
        dadosAtualizados.setEmail("joao.atualizado@example.com");
        dadosAtualizados.setCpf("12345678900");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO resultado = clienteService.atualizarCliente(1L, dadosAtualizados);

        assertNotNull(resultado);
        assertEquals(dadosAtualizados.getNome(), resultado.getNome());
        assertEquals(dadosAtualizados.getEmail(), resultado.getEmail());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com ID inexistente")
    void testAtualizarCliente_DeveLancarExcecao_QuandoIdInexistente() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ConsultaInvalidaException.class,
                () -> clienteService.atualizarCliente(1L, clienteDTO));
    }

    @Test
    @DisplayName("Deve deletar cliente com ID existente")
    void testDeletarCliente_DeveDeletarCliente_QuandoIdExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1L);

        assertDoesNotThrow(() -> clienteService.deletarCliente(1L));
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar com ID inexistente")
    void testDeletarCliente_DeveLancarExcecao_QuandoIdInexistente() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ConsultaInvalidaException.class,
                () -> clienteService.deletarCliente(1L));
        verify(clienteRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve retornar página de clientes")
    void testListarClientes_DeveRetornarPaginaDeClientes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> clientePage = new PageImpl<>(Collections.singletonList(cliente), pageable, 1);

        when(clienteRepository.findAll(pageable)).thenReturn(clientePage);

        Page<ClienteDTO> resultado = clienteService.listarClientes(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(cliente.getNome(), resultado.getContent().get(0).getNome());
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não houver clientes")
    void testListarClientes_DeveRetornarPaginaVazia_QuandoNaoHouverClientes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> clientePage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(clienteRepository.findAll(pageable)).thenReturn(clientePage);

        Page<ClienteDTO> resultado = clienteService.listarClientes(pageable);

        assertNotNull(resultado);
        assertEquals(0, resultado.getTotalElements());
    }
}