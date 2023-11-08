/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.bu.cqrs;

import com.dg.bu.dao.UsuarioDao;
import com.dg.bu.model.User;
import com.dg.bu.viewmodel.UserViewModel;

/**
 *
 * @author damia
 */
public class UsuarioCqrs {

    private UsuarioDao usuarioDAO;

    public UsuarioCqrs() {
        this.usuarioDAO = new UsuarioDao();
    }

    public User registerUser(User usuario) {
        // Verifica si el email ya existe en la base de datos
        if (usuarioDAO.verificarUsuario(usuario.getEmail()) != null) {

            System.out.println("El email ya existe en la base de datos.");
            return null;
        }

        // Si el email no existe, establece los valores de estatus y rol si están vacíos
        if (usuario.getStatus() == null) {
            usuario.setStatus(1);
        }

        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("CLIENTE");
        }

        // Inserta el usuario en la base de datos
        usuario = usuarioDAO.registrarUsuario(usuario);

        return usuario;
    }

    public User updateUser(User usuario) {
        // Verifica si el usuario existe en la base de datos
        User existingUser = usuarioDAO.obtenerUsuarioPorId(usuario.getIdUser());

        if (existingUser == null) {
            System.out.println("El usuario no existe en la base de datos.");
            return null;
        }

        // Copia los campos actualizados al usuario existente
        existingUser.setIdUser(usuario.getIdUser());
        existingUser.setEmail(usuario.getEmail());
        existingUser.setName(usuario.getName());
        existingUser.setPassword(usuario.getPassword());
        existingUser.setRol(usuario.getRol());
        existingUser.setStatus(usuario.getStatus());

        // Realiza la actualización en la base de datos
        return usuarioDAO.actualizarUsuario(existingUser);
    }

    public boolean deleteUser(Long idUsuario) {
        User existingUser = usuarioDAO.obtenerUsuarioPorId(idUsuario);

        if (existingUser != null) {
            // El usuario existe, procedemos a eliminarlo
            return usuarioDAO.deleteUser(idUsuario);

        }

        return false;

    };

    public Boolean login(UserViewModel userLogin) {

        // Verifica si el usuario existe, si no devuelve null, si existe devuelve un
        User user = usuarioDAO.verificarUsuario(userLogin.getEmail());

        if (user != null && userLogin.getPassword().equals(user.getPassword())) {
            System.out.println("USUARIO ENCONTRADO: " + user.toString());
            return true;
        }

        return false;

    }

    public UserViewModel restablecerContrasena(String email, String newpass) {

        User usactual = usuarioDAO.verificarUsuario(email);

        UserViewModel userViewModel = new UserViewModel(usactual.getEmail(), usactual.getPassword());

        if (usactual != null) {
            usactual.setPassword(newpass);
            usuarioDAO.actualizarUsuario(usactual);
            return userViewModel;
        }

        return null;
    }

}
