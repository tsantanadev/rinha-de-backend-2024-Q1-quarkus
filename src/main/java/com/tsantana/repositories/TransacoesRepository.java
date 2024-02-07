package com.tsantana.repositories;

import com.tsantana.models.Transacao;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class TransacoesRepository implements PanacheMongoRepository<Transacao> {
    public List<Transacao> findAllByClienteId(int clienteId) {
        return find("idCliente", clienteId).stream()
                .sorted((t1, t2) -> t2.data().compareTo(t1.data()))
            .toList();
    }
}
