/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.bu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dg.bu.model.User;
import com.dg.bu.db.ConexionMySQL;

/**
 * @author damia
 */
public class UsuarioDao {

    private Connection connection;
    private ConexionMySQL conexionMySQL = new ConexionMySQL();

    public User registrarUsuario(User usuario) {
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {

            connection = conexionMySQL.open();
            String sql = "INSERT INTO user (email, name, password, rol, status) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getName());
            preparedStatement.setString(3, usuario.getPassword());
            preparedStatement.setString(4, usuario.getRol());
            preparedStatement.setInt(5, usuario.getStatus());
            System.out.println("CONSULTA:  " + preparedStatement.toString());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La inserción del usuario falló, ningún ID generado.");
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                usuario.setIdUser(generatedKeys.getLong(1));
            } else {
                throw new SQLException("La inserción del usuario falló, ningún ID generado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Puedes lanzar una excepción personalizada aquí si lo prefieres.
            return null;
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                conexionMySQL.close(); // Cierra la conexión cuando ya no se necesita.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM user";

        try (

                Connection connection = conexionMySQL.open();
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long idUser = resultSet.getObject("id_user") != null ? resultSet.getLong("id_user") : null;
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String rol = resultSet.getString("rol");
                Integer status = resultSet.getObject("status") != null ? resultSet.getInt("status") : null;

                User user = new User(idUser, email, name, password, rol, status);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User actualizarUsuario(User usuario) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conexionMySQL.open();
            String sql = "UPDATE user SET name = ?, password = ?, rol = ?, email = ? WHERE id_user = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getName());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.setString(3, usuario.getRol());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setLong(5, usuario.getIdUser());

            System.out.println("CONSULTA:  " + preparedStatement.toString());

            int rowsAffected = preparedStatement.executeUpdate();

            // Verifica si la actualización fue exitosa
            if (rowsAffected > 0) {
                return usuario; // Devuelve el usuario actualizado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Puedes lanzar una excepción personalizada aquí si lo prefieres.
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                conexionMySQL.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null; // Devuelve null si la actualización no fue exitosa
    }

    public boolean deleteUser(Long idUser) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String deleteQuery = "DELETE FROM user WHERE id_user = ?";

        try {
            connection = conexionMySQL.open();
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(1, idUser);
            System.out.println("CONSULTA:  " + preparedStatement.toString());
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("El usuario con ID " + idUser + " ha sido eliminado con éxito.");
                return true; // La eliminación fue exitosa
            } else {
                System.out.println("No se encontró ningún usuario con ID " + idUser);
                return false; // No se encontró ningún usuario con ese ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Ocurrió un error en la operación de eliminación
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                conexionMySQL.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User verificarUsuario(String email) {
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = conexionMySQL.open();
            String sql = "SELECT * FROM user WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            System.out.println("CONSULTA:  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // El usuario existe, crea un objeto Usuario y carga los datos
                User usuario = new User();
                usuario.setIdUser(resultSet.getLong("id_user"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setName(resultSet.getString("name"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setRol(resultSet.getString("rol"));
                usuario.setStatus(resultSet.getInt("status"));

                return usuario;
            }

            // El usuario no existe, devuelve null
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            // Puedes lanzar una excepción personalizada aquí si lo prefieres.
            return null;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                conexionMySQL.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User obtenerUsuarioPorId(Long idUsuario) {
        Connection connection = null;
        User user = null;

        String selectQuery = "SELECT * FROM user WHERE id_user = ?";

        try {
            connection = conexionMySQL.open();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, idUsuario);
            System.out.println("CONSULTA:  " + preparedStatement.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id_user");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String rol = resultSet.getString("rol");
                Integer status = resultSet.getInt("status");

                user = new User(id, email, name, password, rol, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

}
