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

    public User registrar(User user) {
        UsuarioCqrs usuarioCqrs = new UsuarioCqrs();
        
        String contenido = "Joto el que confirme";
        
        EmailService emailService = new EmailService();
        emailService.enviarCorreo(user.getEmail(), contenido);
        // Inserta el usuario utilizando UsuarioCQRS
        usuarioCqrs.registrarUsuario(user);
        return user;
    }
    
    
    public void recuperarCuenta(String email,String pass){
        
        UsuarioCqrs usuarioCqrs = new UsuarioCqrs();
        if (usuarioCqrs.restablecerContrasena(email, pass) != null) {
            EmailService emailService = new EmailService();
            
            emailService.enviarCorreo(email, "");
        }
    };
    
    
}
