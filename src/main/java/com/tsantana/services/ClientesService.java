package com.tsantana.services;

import com.tsantana.dtos.*;
import com.tsantana.exceptions.UnprocessableException;
import com.tsantana.models.Cliente;
import com.tsantana.models.Transacao;
import com.tsantana.repositories.ClientesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClientesService {

    @Inject
    ClientesRepository clientesRepository;

    public PostTransacaoResponse createTransacao(
            final int id,
            final PostTransacaoRequest postTransacaoRequest) {
        validateTransacao(postTransacaoRequest);
        final var cliente = getCliente(id);

        final var valorTransacao = Integer.parseInt(postTransacaoRequest.valor());

        int novoSaldo = cliente.saldo();

        if (postTransacaoRequest.tipo().equals("d")) {
            novoSaldo -= valorTransacao;

            if (cliente.limite() + novoSaldo < 0) {
                throw new UnprocessableException();
            }
        } else if (postTransacaoRequest.tipo().equals("c")) {
            novoSaldo += valorTransacao;
        }

        final var transacao = new Transacao(
                valorTransacao,
                postTransacaoRequest.tipo(),
                postTransacaoRequest.descricao(),
                LocalDateTime.now());

        final var transacoes = cliente.transacoes().stream()
                .sorted(Comparator.comparing(Transacao::data).reversed())
                .limit(9)
                .collect(Collectors.toCollection(LinkedList::new));

        transacoes.addFirst(transacao);

        clientesRepository.update(new Cliente(cliente.id(), cliente.limite(), novoSaldo, transacoes));

        return new PostTransacaoResponse(cliente.limite(), novoSaldo);
    }

    private void validateTransacao(PostTransacaoRequest transacaoRequest) {
        if (!transacaoRequest.tipo().equals("d") && !transacaoRequest.tipo().equals("c")) {
            throw new UnprocessableException();
        }

        if (transacaoRequest.valor() == null ||
                transacaoRequest.valor().isBlank() ||
                    transacaoRequest.valor().contains(".") ||
                        Integer.parseInt(transacaoRequest.valor()) <= 0) {
            throw new UnprocessableException();
        }

        if (transacaoRequest.descricao() == null ||
                transacaoRequest.descricao().isBlank() ||
                transacaoRequest.descricao().length() > 10) {
            throw new UnprocessableException();
        }
    }

    public ExtratoResponse getExtrato(final int id) {
        final var cliente = getCliente(id);

        return new ExtratoResponse(
                new SaldoResponse(
                        cliente.saldo(),
                        LocalDateTime.now(),
                        cliente.limite()),
                cliente.transacoes().stream().map(transacao -> new TransacaoDetailResponse(
                        transacao.valor(),
                        transacao.tipo(),
                        transacao.descricao(),
                        transacao.data().toString()
                )).toList()
        );
    }

    private Cliente getCliente(final int id) {
        return clientesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }
}
