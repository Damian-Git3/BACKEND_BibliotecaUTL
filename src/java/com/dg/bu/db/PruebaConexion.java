package com.dg.bu.db;

import com.dg.bu.cqrs.UsuarioCqrs;
import com.dg.bu.model.User;
import com.dg.bu.viewmodel.UserViewModel;

/**
 *
 * @author Componentes Unidos
 * @date 20/10/2022
 */
public class PruebaConexion {
    
    public static void main(String[] args) {
        ConexionMySQL connMySQl = new ConexionMySQL();
        try {
            connMySQl.open();
            System.out.println("Conexión establecida con MySQl!");
            /*
            User user = new User();
            user.setEmail("fergl");
            user.setName("Damian Gamboa");
            user.setPassword("1234");
            UsuarioCqrs usuarioCqrs = new UsuarioCqrs();
            System.out.println( usuarioCqrs.registrarUsuario(user).toString());
             */
            UserViewModel userLogin = new UserViewModel();
            userLogin.setEmail("ferga");
            userLogin.setPassword("1234");
            
            System.out.println("Usuario Login: " + userLogin.toString());
            
            UsuarioCqrs usuarioCqrs = new UsuarioCqrs();
            
            boolean validacion = usuarioCqrs.login(userLogin);
            System.out.println("Resultado: " + validacion);
            connMySQl.close();
            System.out.println("Conexión Cerrada correctamente con MySQL!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
