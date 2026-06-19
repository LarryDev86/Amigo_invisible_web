package com.larryDev.service;

import com.larryDev.entity.ContenedorAmigoSeleccionado;
import com.larryDev.entity.Familiar;
import com.larryDev.repository.ContenedorAmigoRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ContenedorAmigoService {

    private ContenedorAmigoRepository contenedorAmigoRepository;
    @Autowired
    private FamiliarService familiarService;
    @Autowired
    private ClaseEmailService claseEmailService;
    public ContenedorAmigoService(ContenedorAmigoRepository contenedorAmigoRepository) {
        this.contenedorAmigoRepository = contenedorAmigoRepository;
    }

    //Metodo para que le toque uno de manera aleatoria, de la clase familiares.
    public synchronized int asignarAmigo(List<Familiar> listaFam , int id){
        Random ran = new Random();
        int idElegido = 0;
        List<ContenedorAmigoSeleccionado> listaContenedor = listarTodoElContenedor();

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
                procesarGuardado(id,idElegido);
                return idElegido;
            }
        }
    }
    private void procesarGuardado(int id, int idAmigoElegido){
        guardarEnContenedor(id,idAmigoElegido);
        //Si se a podido asignar un amigo al familiar, lo declaramos no disponible en la BD.
        familiarService.cambiarDisponibilidadAlFamiliar(id);
    }
    public List<ContenedorAmigoSeleccionado> listarTodoElContenedor(){
        return contenedorAmigoRepository.findAll();
    }

    public void guardarEnContenedor(int familiarId, int amigoTocadoId){
        contenedorAmigoRepository.save(new ContenedorAmigoSeleccionado(familiarId,amigoTocadoId));
    }
    public void borrarTodaLaListaDeAmigos(){
        contenedorAmigoRepository.deleteAll();
    }
    private ContenedorAmigoSeleccionado buscarAmigoPorId(int id){

        for (ContenedorAmigoSeleccionado c : contenedorAmigoRepository.findAll()) {
            if(c.getId() == id){
                return c;
            }
        }
       return null;
    }

    public int consultaCoincidencias(){
        return contenedorAmigoRepository.contarFamiliaresAutoAsignados();
    }

}
