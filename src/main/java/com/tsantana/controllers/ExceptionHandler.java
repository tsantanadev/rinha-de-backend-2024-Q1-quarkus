package com.tsantana.controllers;

import com.tsantana.exceptions.UnprocessableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<UnprocessableException> {


    @Override
    public Response toResponse(UnprocessableException e) {
        return Response.status(422).build();
    }
}
