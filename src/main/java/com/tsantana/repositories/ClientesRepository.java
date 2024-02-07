package com.tsantana.repositories;

import com.tsantana.models.Cliente;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ClientesRepository implements PanacheMongoRepository<Cliente> {

    public Optional<Cliente> findById(final int id) {
        return find("_id", id).firstResultOptional();
    }
}
