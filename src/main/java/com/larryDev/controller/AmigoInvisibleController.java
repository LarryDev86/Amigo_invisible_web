package com.larryDev.controller;

import com.larryDev.entity.Familiar;
import com.larryDev.service.ClaseEmailService;
import com.larryDev.service.ContenedorAmigoService;
import com.larryDev.service.FamiliarService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AmigoInvisibleController {

    @Autowired
    private FamiliarService familiarService;

    @Autowired
    private ContenedorAmigoService contenedorAmigoService;

    @Autowired
    private ClaseEmailService claseEmailService;

    @Value("${email.admin}")
    private String emailAdmin;

    @Value("${password.admin}")
    private String passwordAdmin;

    @GetMapping("/")
    public String getFamiliares(Model modelo){
        modelo.addAttribute("familiares",familiarService.listarTodosLosFamiliaresDisponibles());
        return "formulario";
    }
    @GetMapping("/save")
    public String eliminarFamiliar(@RequestParam("id") String id,
                                   @RequestParam("email") String email, RedirectAttributes modelo) throws MessagingException {

        if(email.contains("@") && email.contains("gmail") && email.contains(".com") || email.contains("gmail") && email.contains(".es") ||
                email.contains("hotmail") && email.contains(".com") ||  email.contains("hotmail") && email.contains(".es") ||
                email.contains("icloud") && email.contains(".com") ){
            int idAmigoElegido = contenedorAmigoService.asignarAmigo(familiarService.listarTodosLosFamiliares(),
                    Integer.parseInt(id));
            //Invocamos la clase del email.
            claseEmailService.enviarEmail(familiarService.
                            buscarFamiliarPorId(Integer.parseInt(id)).getNombre(),email,
                    familiarService.
                            buscarFamiliarPorId(idAmigoElegido).getNombre());
            modelo.addFlashAttribute("mensaje",
                    " Se te ha asignado un amigo con exito. Comprueba tu correo electrónico.");
            return "redirect:/";
        }
        modelo.addFlashAttribute("mensajeError","El email introducido no es valido!");
        return "redirect:/";
    }
    //Metodo para actualizar la BD
    @GetMapping("/querys")
    public String borrarBaseDeDatosAmigos(){
        contenedorAmigoService.borrarTodaLaListaDeAmigos();
        familiarService.cambiarDisponibleTodosFamiliares();
        System.out.println("Se borro con exito.. y se han puesto todos como disponibles");
        return "redirect:/logout";
    }
    @GetMapping("/new")
    public String crearnuevo(){
        return "/formRegistro";
    }

    @GetMapping("/create")
    public String nuevoFamiliar(@RequestParam("nombre") String nombre, Model modelo){
        familiarService.nuevoFamiliar(new Familiar(nombre));
        modelo.addAttribute("mensaje","Familiar nuevo con exito!");
        return "/formRegistro";
    }
    @GetMapping("/admin")
    public String vistaAdmin() {
        return "vistaAdmin";
    }
    //Metodo login personalizado.
    @GetMapping("/login")
    public String mostrarLogin(){
        return "formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        SecurityContextLogoutHandler logoutHandler =
                new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }
    @GetMapping("/repetidos")
    public String repetidos(Model modelo){
        contenedorAmigoService.consultaCoincidencias();
        if(familiarService.listarTodosLosFamiliaresDisponibles().isEmpty() &&
                contenedorAmigoService.consultaCoincidencias() == 0){
            modelo.addAttribute("mensaje","¡Perfecto! - No ha habido ninguna coincidencia");
            return"/vistaAdmin";
        }
        return"/vistaAdmin";
    }
}
