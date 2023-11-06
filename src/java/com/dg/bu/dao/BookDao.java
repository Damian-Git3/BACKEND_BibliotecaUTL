package com.dg.bu.dao;

import com.dg.bu.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BookDao {

    private Connection connection;

    public Book insertBook(Book book) {
        String insertQuery = "INSERT INTO books (author, libroBase64, name, university) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getLibroBase64());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setString(4, book.getUniversity());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    book.setIdBook(id); // Establecer el ID generado en el objeto Book
                    System.out.println("El libro ha sido insertado con éxito. ID generado: " + id);
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si la inserción no fue exitosa o no se generó un ID
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT idBook, author, libroBase64, name, university FROM books";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long idBook = resultSet.getLong("idBook");
                String author = resultSet.getString("author");
                String libroBase64 = resultSet.getString("libroBase64");
                String name = resultSet.getString("name");
                String university = resultSet.getString("university");

                Book book = new Book(idBook, author, libroBase64, name, university);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public Book updateBook(Book book) {
        String updateQuery = "UPDATE books SET author = ?, libroBase64 = ?, name = ?, university = ? WHERE idBook = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getLibroBase64());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setString(4, book.getUniversity());
            preparedStatement.setLong(5, book.getIdBook());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("El libro con ID " + book.getIdBook() + " ha sido actualizado con éxito.");

                // Después de la actualización, recupera el libro actualizado de la base de datos
                Book updatedBook = getBookById(book.getIdBook());
                return updatedBook;
            } else {
                System.out.println("No se encontró ningún libro con ID " + book.getIdBook());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si la actualización no fue exitosa o no se encontró el libro
    }

    public boolean deleteBook(Long idBook) {
        String deleteQuery = "DELETE FROM books WHERE idBook = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, idBook);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("El libro con id " + idBook + " ha sido eliminado con éxito.");
                return true; // La eliminación fue exitosa
            } else {
                System.out.println("No se encontró ningún libro con id " + idBook);
                return false; // No se encontró el libro o no se pudo eliminar
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Ocurrió un error en la operación de eliminación
        }
    }

    public Book getBookById(Long idBook) {
        String selectQuery = "SELECT idBook, author, libroBase64, name, university FROM books WHERE idBook = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, idBook);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("idBook");
                String author = resultSet.getString("author");
                String libroBase64 = resultSet.getString("libroBase64");
                String name = resultSet.getString("name");
                String university = resultSet.getString("university");

                return new Book(id, author, libroBase64, name, university);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si no se encuentra el libro con el ID especificado
    }

}
