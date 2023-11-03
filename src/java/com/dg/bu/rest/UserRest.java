/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.bu.rest;


import com.dg.bu.dao.UsuarioDao;
import com.dg.bu.model.User;
import com.dg.bu.appservice.UsuariosAppService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.dg.bu.viewmodel.UserViewModel;

/**
 *
 * @author damia
 */
@Path("/user")
public class UserRest {
    
    
    @GET
    @Path("/saludar/{nombre}")
    @Produces(MediaType.TEXT_PLAIN)
    public String saludar(@PathParam("nombre") String nombre) {
        return "¡Hola, " + nombre + "!";
    }
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User register(User usuario) {        
        UsuariosAppService usuarioAppService = new UsuariosAppService();
        
        return usuarioAppService.registrar(usuario);
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean login(UserViewModel usuarioVM) {  
        UsuarioDao usuarioDAO = new UsuarioDao();
        User user = usuarioDAO.verificarUsuario(usuarioVM.getEmail());

        if (user != null && usuarioVM.getPassword().equals(user.getPassword())) {
            System.out.println("USUARIO ENCONTRADO: " + user.toString());
            return true;
        }
        
        return false;
    }
    
    @POST
    @Path("/resetpass")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserViewModel ResetPassword(String email) {  
        UserViewModel userViewModel = new UserViewModel();
        UsuarioDao usuarioDAO = new UsuarioDao();
        User user = usuarioDAO.verificarUsuario(email);

        if (user != null) {
            System.out.println("USUARIO ENCONTRADO: " + user.toString());
            userViewModel.setEmail(email);
            userViewModel.setPassword(user.getPassword());
            return userViewModel;
        }
        
        return null;
    }
   
}
