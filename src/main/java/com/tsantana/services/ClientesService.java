package com.tsantana.services;

import com.tsantana.dtos.*;
import com.tsantana.models.Cliente;
import com.tsantana.models.Transacao;
import com.tsantana.repositories.ClientesRepository;
import com.tsantana.repositories.TransacoesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ClientesService {

    @Inject
    ClientesRepository clientesRepository;

    @Inject
    TransacoesRepository transacoesRepository;

    public PostTransacaoResponse createTransacao(
            final int id,
            final PostTransacaoRequest postTransacaoRequest) {
        validateTipo(postTransacaoRequest.tipo());
        final var cliente = getCliente(id);

        final var transacoes = transacoesRepository.findAllByClienteId(cliente.id());
        int novoSaldo = calculateSaldo(transacoes);

        if (postTransacaoRequest.tipo().equals("d")) {
            novoSaldo -= postTransacaoRequest.valor();

            if (cliente.limite() + novoSaldo < 0) {
                throw new BadRequestException();
            }
        } else if (postTransacaoRequest.tipo().equals("c")) {
            novoSaldo += postTransacaoRequest.valor();
        }

        final var entity = new Transacao(
                null,
                cliente.id(),
                postTransacaoRequest.valor(),
                postTransacaoRequest.tipo(),
                postTransacaoRequest.descricao(),
                LocalDateTime.now());

        transacoesRepository.persist(entity);

        return new PostTransacaoResponse(cliente.limite(), novoSaldo);
    }

    private void validateTipo(String tipo) {
        if (!tipo.equals("d") && !tipo.equals("c")) {
            throw new ValidationException("Tipo inválido");
        }
    }

    public ExtratoResponse getExtrato(final int id) {
        final var cliente = getCliente(id);

        final var transacoes = transacoesRepository.findAllByClienteId(cliente.id());

        final var saldo = calculateSaldo(transacoes);

        return new ExtratoResponse(
                new SaldoResponse(
                        saldo,
                        LocalDateTime.now(),
                        cliente.limite()),
                transacoes.stream()
                        .limit(10)
                        .map(transacao -> new TransacaoDetailResponse(
                                transacao.valor(),
                                transacao.tipo(),
                                transacao.descricao(),
                                transacao.data().toString()))
                        .toList()
        );
    }

    private Cliente getCliente(final int id) {
        return clientesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    private int calculateSaldo(final List<Transacao> transacoes) {
        return transacoes.stream()
                .mapToInt(transacao -> {
                    if (transacao.tipo().equals("d")) {
                        return -transacao.valor();
                    } else {
                        return transacao.valor();
                    }
                })
                .sum();
    }
}
