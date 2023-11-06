package com.dg.bu.rest;

import com.dg.bu.cqrs.UsuarioCqrs;
import com.dg.bu.dao.UsuarioDao;
import com.dg.bu.model.User;
import com.dg.bu.appservice.UsuariosAppService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import com.dg.bu.viewmodel.UserViewModel;
import jakarta.ws.rs.core.Response;

import java.util.List;

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
        
        return usuarioAppService.register(usuario);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User update(User usuario) {
        UsuariosAppService usuarioAppService = new UsuariosAppService();

        return usuarioAppService.update(usuario);
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
    public UserViewModel resetPassword(String email) {  
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

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        UsuarioDao usuarioDAO = new UsuarioDao();
        List<User> usuarios = usuarioDAO.getAllUsers();

        return Response.ok(usuarios).build();
    }

    @DELETE
    @Path("/delete/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("idUsuario") Long idUsuario) {
        UsuarioCqrs userCQRS = new UsuarioCqrs();

        boolean eliminado = userCQRS.deleteUser(idUsuario);

        if (eliminado) {
            return Response.ok("Usuario eliminado con éxito").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No se encontró el usuario o no se pudo eliminar").build();
        }
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserViewModel findUser(@PathParam("email") String email) {
        return tuClase.findUser(email);
    }


}
