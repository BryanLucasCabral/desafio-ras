package com.desafio.api.service;

import com.desafio.api.dtos.ClienteDTO;
import com.desafio.api.constants.SituacaoConta;
import com.desafio.api.dtos.ContaDTO;
import com.desafio.api.exception.CampoIncorretoException;
import com.desafio.api.exception.ConsultaInvalidaException;
import com.desafio.api.model.Cliente;
import com.desafio.api.model.Conta;
import com.desafio.api.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {
    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ContaService contaService;

    private Conta conta;
    private ContaDTO contaDTO;
    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Cliente Teste");

        conta = new Conta();
        conta.setId(1L);
        conta.setReferencia("01-2023");
        conta.setValor(100.0);
        conta.setSituacao(SituacaoConta.PAGA);
        conta.setCliente(cliente);

        contaDTO = new ContaDTO();
        contaDTO.setId(1L);
        contaDTO.setReferencia("01-2023");
        contaDTO.setValor(100.0);
        contaDTO.setSituacao(SituacaoConta.PAGA);
        contaDTO.setCliente(clienteDTO);
    }

    @Test
    @DisplayName("Deve cadastrar conta com sucesso quando dados são válidos")
    void cadastrarConta_DeveRetornarContaDTO_QuandoDadosValidos() {
        when(clienteService.buscarClientePorId(anyLong())).thenReturn(clienteDTO);
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        ContaDTO resultado = contaService.cadastrarConta(1L, contaDTO);

        assertNotNull(resultado);
        assertEquals(contaDTO.getReferencia(), resultado.getReferencia());
        verify(contaRepository, times(1)).save(any(Conta.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar conta com valor negativo")
    void cadastrarConta_DeveLancarExcecao_QuandoValorNegativo() {
        contaDTO.setValor(-10.0);

        when(clienteService.buscarClientePorId(anyLong())).thenReturn(clienteDTO);

        assertThrows(CampoIncorretoException.class, () ->
                contaService.cadastrarConta(1L, contaDTO));
    }

    @Test
    @DisplayName("Deve atualizar conta com sucesso")
    void atualizarConta_DeveRetornarContaAtualizada_QuandoDadosValidos() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        ContaDTO resultado = contaService.atualizarConta(1L, contaDTO);

        assertNotNull(resultado);
        assertEquals(contaDTO.getReferencia(), resultado.getReferencia());
        verify(contaRepository, times(1)).save(any(Conta.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar conta inexistente")
    void atualizarConta_DeveLancarExcecao_QuandoContaInexistente() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ConsultaInvalidaException.class, () ->
                contaService.atualizarConta(1L, contaDTO));
    }

    @Test
    @DisplayName("Deve cancelar conta com sucesso")
    void deletarConta_DeveCancelarConta_QuandoIdExistente() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        assertDoesNotThrow(() -> contaService.deletarConta(1L));
        assertEquals(SituacaoConta.CANCELADA, conta.getSituacao());
        verify(contaRepository, times(1)).save(any(Conta.class));
    }

    @Test
    @DisplayName("Deve listar contas por ID do cliente")
    void listarContasPeloIdCliente_DeveRetornarLista_QuandoClienteExiste() {
        when(clienteService.buscarClientePorId(anyLong())).thenReturn(clienteDTO);
        when(contaRepository.findByClienteId(anyLong())).thenReturn(Arrays.asList(conta));

        List<ContaDTO> resultado = contaService.listarContasPeloIdCliente(1L);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve buscar conta por ID com sucesso")
    void buscarContaPeloId_DeveRetornarContaDTO_QuandoIdExistente() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));

        ContaDTO resultado = contaService.buscarContaPeloId(1L);

        assertNotNull(resultado);
        assertEquals(contaDTO.getId(), resultado.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar conta por ID inexistente")
    void buscarContaPeloId_DeveLancarExcecao_QuandoIdInexistente() {
        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ConsultaInvalidaException.class, () ->
                contaService.buscarContaPeloId(1L));
    }

    @Test
    @DisplayName("Deve validar conta corretamente")
    void validarConta_DeveLancarExcecoes_QuandoDadosInvalidos() {
        // Teste para valor negativo
        ContaDTO contaInvalida = new ContaDTO();
        contaInvalida.setValor(-10.0);
        assertThrows(CampoIncorretoException.class, () ->
                contaService.validarConta(contaInvalida));

        // Teste para cliente nulo
        contaInvalida.setValor(10.0);
        contaInvalida.setCliente(null);
        assertThrows(CampoIncorretoException.class, () ->
                contaService.validarConta(contaInvalida));

        // Teste para situação cancelada
        contaInvalida.setCliente(clienteDTO);
        contaInvalida.setSituacao(SituacaoConta.CANCELADA);
        assertThrows(CampoIncorretoException.class, () ->
                contaService.validarConta(contaInvalida));
    }
}