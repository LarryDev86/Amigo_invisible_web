package com.larryDev.service;

import com.larryDev.entity.Familiar;
import com.larryDev.repository.FamiliarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class FamiliarService {

    private FamiliarRepository repoFamiliar;

    public FamiliarService(FamiliarRepository repoFamiliar) {

        this.repoFamiliar = repoFamiliar;
    }
    public void cambiarDisponibleTodosFamiliares(){

        for (Familiar f : listarTodosLosFamiliares()) {
            if(f.isDisponible() == false){
                f.setDisponible(true);
                repoFamiliar.save(f);
            }
        }
    }
    public void cambiarDisponibilidadAlFamiliar(Integer id){
        Optional<Familiar> fam = repoFamiliar.findById(id);
            if(fam.isPresent()){
                Familiar familiar = fam.get();
                familiar.setDisponible(false);
                //actualizamos el cambio.
                repoFamiliar.save(familiar);
            }

    }
    public List<Familiar> listarTodosLosFamiliaresDisponibles(){
        List<Familiar> lista = new ArrayList<>();
        for (Familiar f : listarTodosLosFamiliares()) {
            if(f.isDisponible() == true){
                lista.add(f);
            }
        }
        return lista;
    }
    public List<Familiar> listarTodosLosFamiliares(){
        return repoFamiliar.findAllByOrderByNombreAsc();
    }

    public Familiar buscarFamiliarPorId(int id){
        Optional<Familiar> lista = repoFamiliar.findById(id);
        if(lista.isPresent()){
            return lista.get();
        }
        return null;
    }
}
