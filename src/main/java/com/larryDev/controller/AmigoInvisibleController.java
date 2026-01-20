package com.larryDev.controller;

import com.larryDev.entity.ContenedorAmigoSeleccionado;
import com.larryDev.entity.Familiar;
import com.larryDev.service.ClaseEmailService;
import com.larryDev.service.ContenedorAmigoService;
import com.larryDev.service.FamiliarService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FamiliarService familiaController;

    @Autowired
    ContenedorAmigoService contenedorAmigoService;

    @Autowired
    ClaseEmailService claseEmailService;

    @GetMapping("/")
    public String getFamiliares(Model modelo){
        modelo.addAttribute("familiares",familiaController.listarTodosLosFamiliaresDisponibles());
        return "formulario";
    }
    @GetMapping("/save")
    public String eliminarFamiliar(@RequestParam("id") String id,
                                   @RequestParam("email") String email, Model modelo) throws MessagingException {

        if(email.contains("@") && email.contains("gmail") && email.contains(".com") || email.contains("gmail") && email.contains(".es") ||
                email.contains("hotmail") && email.contains(".com") ||  email.contains("hotmail") && email.contains(".es") ||
                email.contains("icloud") && email.contains(".com") ){
            int idAmigoElegido = obtenerFamiliar(familiaController.listarTodosLosFamiliares(),
                    Integer.parseInt(id) );
            contenedorAmigoService.guardarEnContenedor(Integer.parseInt(id),idAmigoElegido);
            System.out.println("Comletado con exito!!");
            //Si se a podido asignar un amigo al familiar, lo declaramos no disponible en la BD.
            familiaController.cambiarDisponibilidadAlFamiliar(Integer.parseInt(id));
            modelo.addAttribute("nombreFamiliar",familiaController.
                    buscarFamiliarPorId(Integer.parseInt(id)).getNombre());
            modelo.addAttribute("nombreAmigo",familiaController.
                    buscarFamiliarPorId(idAmigoElegido).getNombre());
            //------------------------------------------------------------------------------------------
            //Invocamos la clase del email.
            claseEmailService.enviarEmail(familiaController.
                            buscarFamiliarPorId(Integer.parseInt(id)).getNombre(),email,
                    familiaController.
                            buscarFamiliarPorId(idAmigoElegido).getNombre());

            return "vista";
        }
        return "error";
    }

    //Metodo para que le toque uno de manera aleatoria, de la clase familiares.
    private Integer obtenerFamiliar(List<Familiar> listaFam , int id){
        Random ran = new Random();
        int idElegido = 0;
        List<ContenedorAmigoSeleccionado> listaContenedor = contenedorAmigoService.listarTodoElContenedor();

        while(true){
            idElegido = ran.nextInt(listaFam.size())+1;
            //Familiar famElegido = listaFam.get(ran.nextInt(listaFam.size())+1);
            if(idElegido == id)continue;
            boolean yaSalio = false;
            //Comprobamos el numero random, con los id que hay en la tabla de amigos que ya le han tocado a alguien.
            for (ContenedorAmigoSeleccionado c : listaContenedor) {
                if(idElegido == c.getAmigoTocadoId()){
                    yaSalio = true;
                    break;
                }
            }
            //Si no ha salido devuelve el id que no ha salido.
            if(!yaSalio){
                return idElegido;
            }
        }
    }
}
