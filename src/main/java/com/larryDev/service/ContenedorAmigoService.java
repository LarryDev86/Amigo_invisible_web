package com.larryDev.service;

import com.larryDev.entity.ContenedorAmigoSeleccionado;
import com.larryDev.repository.ContenedorAmigoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenedorAmigoService {

    private ContenedorAmigoRepository contenedorAmigoRepository;

    public ContenedorAmigoService(ContenedorAmigoRepository contenedorAmigoRepository) {
        this.contenedorAmigoRepository = contenedorAmigoRepository;
    }

    public List<ContenedorAmigoSeleccionado> listarTodoElContenedor(){
        return contenedorAmigoRepository.findAll();
    }

    public void guardarEnContenedor(int familiarId, int amigoTocadoId){
        contenedorAmigoRepository.save(new ContenedorAmigoSeleccionado(familiarId,amigoTocadoId));
    }

    private ContenedorAmigoSeleccionado buscarAmigoPorId(int id){

        for (ContenedorAmigoSeleccionado c : contenedorAmigoRepository.findAll()) {
            if(c.getId() == id){
                return c;
            }
        }
       return null;
    }

}
