package com.tsantana.controllers;

import com.tsantana.dtos.ExtratoResponse;
import com.tsantana.dtos.PostTransacaoRequest;
import com.tsantana.services.ClientesService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.Body;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
public class ClientesController {

    @Inject
    ClientesService clientesService;

    @POST
    @Path("/{id}/transacoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTransacao(
            @PathParam("id") final int id,
            @Valid final PostTransacaoRequest postTransacaoRequest) {
        final var response = clientesService.createTransacao(id, postTransacaoRequest);

        return Response.ok(response).build();
    }

    @GET
    @Path("{id}/extrato")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExtrato(@PathParam("id") final int id) {
        final ExtratoResponse response = clientesService.getExtrato(id);

        return Response.ok(response).build();
    }
}
