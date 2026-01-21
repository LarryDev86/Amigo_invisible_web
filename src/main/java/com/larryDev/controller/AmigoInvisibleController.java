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
    private FamiliarService familiaController;

    @Autowired
    ContenedorAmigoService contenedorAmigoService;

    @Autowired
    ClaseEmailService claseEmailService;

    @Value("${email.admin}")
    private String emailAdmin;

    @Value("${password.admin}")
    private String passwordAdmin;

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
        modelo.addAttribute("mensaje","El email introducido no es valido!");
        return "error";
    }
    //Metodo para comprobar los datos del administrador.
    @GetMapping("/admin")
    public String comprobarDatosAdmin(@RequestParam("email") String email, @RequestParam("password") String password,
                                      Model modelo){
        System.out.println("El email del admin es: "+email);
        System.out.println("La contraseña del admin es: "+password);
        if(verificarLogin(email, password)){
            return "vistaAdmin";
        }
        modelo.addAttribute("mensaje","El email o la contraseña no son correctos!!");
        return "error";
    }
    //Metodo para actualizar la BD
    @GetMapping("/querys")
    public String borrarBaseDeDatosAmigos(){
        contenedorAmigoService.borrarTodaLaListaDeAmigos();
        familiaController.cambiarDisponibleTodosFamiliares();
        System.out.println("Se borro con exito.. y se han puesto todos como disponibles");
        return "redirect:/";
    }
    //Veridicar email y contraseña del admin.
    private boolean verificarLogin(String email, String password){
        if(email.equals(emailAdmin) && password.equals(passwordAdmin)){
            return true;
        }
        return false;
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
