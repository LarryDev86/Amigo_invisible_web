package com.larryDev.repository;

import com.larryDev.entity.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamiliarRepository extends JpaRepository<Familiar,Integer> {
    //Metodo para mostrar todos en orden alfabetico
    List<Familiar> findAllByOrderByNombreAsc();
}
