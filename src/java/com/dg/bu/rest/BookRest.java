/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.bu.rest;

import com.dg.bu.cqrs.UsuarioCqrs;
import com.dg.bu.dao.UsuarioDao;
import com.dg.bu.model.User;
import com.dg.bu.appservice.UsuariosAppService;
import com.dg.bu.model.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import com.dg.bu.viewmodel.UserViewModel;
import jakarta.ws.rs.core.Response;
import com.dg.bu.cqrs.BookCqrs;
import com.dg.bu.dao.BookDao;
import java.util.List;

/**
 *
 * @author damia
 */
public class BookRest {

    @POST
    @Path("/insertBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertBook(Book book) {
        BookCqrs bookCQRS = new BookCqrs();
        Book insertedBook = bookCQRS.registerBook(book);

        if (insertedBook != null) {
            return Response.status(Response.Status.CREATED).entity(insertedBook).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al insertar el libro").build();
        }
    }

    @PUT
    @Path("/updateBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        BookCqrs bookCQRS = new BookCqrs();
        Book updatedBook = bookCQRS.updateBook(book);

        if (updatedBook != null) {
            return Response.ok(updatedBook).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Libro no encontrado o error en la actualización").build();
        }
    }

    @GET
    @Path("/getBook/{idBook}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("idBook") Long idBook) {
        BookDao bookdao = new BookDao();
        Book book = bookdao.getBookById(idBook);

        if (book != null) {
            return Response.ok(book).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Libro no encontrado").build();
        }
    }

    @GET
    @Path("/getAllBooks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        BookDao bookdao = new BookDao();
        List<Book> books = bookdao.getAllBooks();

        return Response.ok(books).build();
    }

    @DELETE
    @Path("/deleteBook/{idBook}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("idBook") Long idBook) {
        BookCqrs bookCQRS = new BookCqrs();
        boolean deleted = bookCQRS.deleteBook(idBook);

        if (deleted) {
            return Response.ok("Libro eliminado con éxito").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Libro no encontrado o error en la eliminación").build();
        }
    }

}
