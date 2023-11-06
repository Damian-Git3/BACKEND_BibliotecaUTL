/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.bu.appservice;

import com.dg.bu.cqrs.UsuarioCqrs;
import com.dg.bu.dao.UsuarioDao;
import com.dg.bu.model.User;

/**
 *
 * @author damia
 */
public class UsuariosAppService {

    private UsuarioCqrs usuarioCqrs;
    private UsuarioDAo userDao;

    public User register(User user) {
        usuarioCqrs = new UsuarioCqrs();
        
        String contenido = "Joto el que confirme";
        
        EmailService emailService = new EmailService();
        emailService.enviarCorreo(user.getEmail(), contenido);
        // Inserta el usuario utilizando UsuarioCQRS
        usuarioCqrs.registerUser(user);
        return user;
    }

    public User update(User user) {
        usuarioCqrs = new UsuarioCqrs();

        // Inserta el usuario utilizando UsuarioCQRS

        return usuarioCqrs.updateUser(user);
    }
    
    
    public void recuperarCuenta(String email,String pass){
        
        usuarioCqrs = new UsuarioCqrs();
        if (usuarioCqrs.restablecerContrasena(email, pass) != null) {
            EmailService emailService = new EmailService();
            
            emailService.enviarCorreo(email, "");
        }
    };

    public UserViewModel findUser(String email){
        UserViewModel userVM = new UserViewModel();
        User user = userDao.verificarUsuario(email);
        if(user != null){
           
            userVM.setEmail(user.getEmail());
            userVM.setPassword(user.getPassword());
        }

        return userVM;
    }
    
    
}
