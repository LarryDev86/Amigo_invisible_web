package com.larryDev.controller;

import com.larryDev.entity.ContenedorAmigoSeleccionado;
import com.larryDev.entity.Familiar;
import com.larryDev.service.ClaseEmailService;
import com.larryDev.service.ContenedorAmigoService;
import com.larryDev.service.FamiliarService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
                                   @RequestParam("email") String email, Model modelo) throws MessagingException {

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
            return "vista";
        }
        modelo.addAttribute("mensaje","El email introducido no es valido!");
        return "error";
    }
    //Metodo para actualizar la BD
    @GetMapping("/querys")
    public String borrarBaseDeDatosAmigos(){
        contenedorAmigoService.borrarTodaLaListaDeAmigos();
        familiarService.cambiarDisponibleTodosFamiliares();
        System.out.println("Se borro con exito.. y se han puesto todos como disponibles");
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String vistaAdmin() {
        return "vistaAdmin";
    }
}
