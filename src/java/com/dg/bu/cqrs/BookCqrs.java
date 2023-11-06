package com.dg.bu.cqrs;

import com.dg.bu.dao.BookDao;
import com.dg.bu.model.Book;

public class BookCqrs {

    private BookDao bookDao;

    public Book registerBook(Book book) {
        // Verifica si el libro ya existe en la base de datos
        //if (bookDao.checkBookExistence(book.getName())) {
        //    System.out.println("El libro ya existe en la base de datos.");
        //    return null;
        //}

        // Puedes realizar otras validaciones antes de registrar el libro
        // Inserta el libro en la base de datos
        book = bookDao.insertBook(book);

        return book;
    }

    public Book updateBook(Book book) {
        // Verifica si el libro existe en la base de datos

        if (book != null) {
            // Realiza las actualizaciones en el libro existente
            book.setAuthor(book.getAuthor());
            book.setLibroBase64(book.getLibroBase64());
            book.setName(book.getName());
            book.setUniversity(book.getUniversity());

            // Realiza la actualización en la base de datos
            book = bookDao.updateBook(book);

            return book;
        }

        // Si el libro no existe, puedes manejar el caso de acuerdo a tus necesidades
        return null;
    }

    public boolean deleteBook(Long idBook) {

        if (idBook != null) {

            if (bookDao.deleteBook(idBook)) {
                return true; // La eliminación fue exitosa
            }
        }

        return false; // No se encontró el libro o no se pudo eliminar
    }

}
