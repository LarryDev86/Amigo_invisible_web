package com.larryDev.repository;

import com.larryDev.entity.ContenedorAmigoSeleccionado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContenedorAmigoRepository extends JpaRepository<ContenedorAmigoSeleccionado, Integer> {

    @Query(value = """
        SELECT COUNT(*)
        FROM contenedor_de_amigos
        WHERE familiar_seleccionado_id = amigo_tocado_id
        """, nativeQuery = true)
    int contarFamiliaresAutoAsignados();
}
