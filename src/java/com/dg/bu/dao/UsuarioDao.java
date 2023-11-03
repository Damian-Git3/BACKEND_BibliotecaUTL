/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.bu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dg.bu.model.User;
import com.dg.bu.db.ConexionMySQL;

/**
 *
 * @author damia
 */
public class UsuarioDao {

    public User registrarUsuario(User usuario) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        ConexionMySQL conexionMySQL = new ConexionMySQL();

        try {

            connection = conexionMySQL.open();
            String sql = "INSERT INTO user (email, name, password, rol, status) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getName());
            preparedStatement.setString(3, usuario.getPassword());
            preparedStatement.setString(4, usuario.getRol());
            preparedStatement.setInt(5, usuario.getStatus());
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

    public User verificarUsuario(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ConexionMySQL conexionMySQL = new ConexionMySQL();

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
    
    public boolean actualizarUsuario(User usuario) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ConexionMySQL conexionMySQL = new ConexionMySQL();

        try {
            connection = conexionMySQL.open();
            String sql = "UPDATE user SET name = ?, password = ?, rol = ? WHERE idUser = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getName());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.setString(3, usuario.getRol());
            preparedStatement.setLong(4, usuario.getIdUser());

            int rowsAffected = preparedStatement.executeUpdate();

            // Verifica si la actualización fue exitosa
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Puedes lanzar una excepción personalizada aquí si lo prefieres.
            return false;
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


}
