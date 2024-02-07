package com.tsantana.config;

import com.tsantana.models.Cliente;
import com.tsantana.repositories.ClientesRepository;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    ClientesRepository clientesRepository;

    @Startup
    void onStart() {
        if (clientesRepository.findAll().list().isEmpty()){
            System.out.println("Inserting documents");
            clientesRepository.persist(new Cliente(1, 100000));
            clientesRepository.persist(new Cliente(2, 80000));
            clientesRepository.persist(new Cliente(3, 1000000));
            clientesRepository.persist(new Cliente(4, 10000000));
            clientesRepository.persist(new Cliente(5, 500000));
        }
    }

}
