package com.dg.bu.dao;

import com.dg.bu.db.ConexionMySQL;
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
    private ConexionMySQL conexionMySQL = new ConexionMySQL();
    PreparedStatement preparedStatement;

    public Book insertBook(Book book) {
        String insertQuery = "INSERT INTO book (author, libro_base64, name, university) VALUES (?, ?, ?, ?)";
        connection = null;
        preparedStatement = null;
        ResultSet generatedKeys = null;
    
        try {
            connection = conexionMySQL.open();
            preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
    
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getLibroBase64());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setString(4, book.getUniversity());
            System.out.println("CONSULTA:  " + preparedStatement.toString());
            int rowsInserted = preparedStatement.executeUpdate();
    
            if (rowsInserted > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    book.setIdBook(id); // Establecer el ID generado en el objeto Book
                    System.out.println("El libro ha sido insertado con éxito. ID generado: " + id);
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        return null; // Devuelve null si la inserción no fue exitosa o no se generó un ID
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String selectQuery = "SELECT * FROM book";
        connection = null;
        preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            connection = conexionMySQL.open();
            preparedStatement = connection.prepareStatement(selectQuery);
            System.out.println("CONSULTA:  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                Long idBook = resultSet.getLong("id_book");
                String author = resultSet.getString("author");
                String libroBase64 = resultSet.getString("libro_base64");
                String name = resultSet.getString("name");
                String university = resultSet.getString("university");
    
                Book book = new Book(idBook, author, libroBase64, name, university);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        return books;
    }

    public Book updateBook(Book book) {
        String updateQuery = "UPDATE book SET author = ?, libro_base64 = ?, name = ?, university = ? WHERE id_book = ?";
        connection = null;
        preparedStatement = null;
    
        try {
            connection = conexionMySQL.open();
            preparedStatement = connection.prepareStatement(updateQuery);
    
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getLibroBase64());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setString(4, book.getUniversity());
            preparedStatement.setLong(5, book.getIdBook());
            System.out.println("CONSULTA:  " + preparedStatement.toString());
            int rowsUpdated = preparedStatement.executeUpdate();
    
            if (rowsUpdated > 0) {
                System.out.println("El libro con ID " + book.getIdBook() + " ha sido actualizado con éxito.");
    
                // Después de la actualización, recupera el libro actualizado de la base de datos
                Book updatedBook = getBookById(book.getIdBook().intValue());
                return updatedBook;
            } else {
                System.out.println("No se encontró ningún libro con ID " + book.getIdBook());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        return null; // Devuelve null si la actualización no fue exitosa o no se encontró el libro
    }

    public boolean deleteBook(Long idBook) {
        String deleteQuery = "DELETE FROM book WHERE id_book = ?";
        connection = null;
        preparedStatement = null;
    
        try {
            connection = conexionMySQL.open();
            preparedStatement = connection.prepareStatement(deleteQuery);
            System.out.println("CONSULTA:  " + preparedStatement.toString());
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
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Book getBookById(int idBook) {
        String selectQuery = "SELECT * FROM book WHERE id_book = ?";
        connection = null;
        preparedStatement = null;
        ResultSet resultSet = null;
    
        try {
            connection = conexionMySQL.open();
            preparedStatement = connection.prepareStatement(selectQuery);
    
            preparedStatement.setLong(1, idBook);
            System.out.println("CONSULTA:  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                Long id = resultSet.getLong("id_book");
                String author = resultSet.getString("author");
                String libroBase64 = resultSet.getString("libro_base64");
                String name = resultSet.getString("name");
                String university = resultSet.getString("university");
    
                return new Book(id, author, libroBase64, name, university);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        return null; // Devuelve null si no se encuentra el libro con el ID especificado
    }



}
