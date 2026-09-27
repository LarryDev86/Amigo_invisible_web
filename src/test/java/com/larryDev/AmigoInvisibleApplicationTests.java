package com.larryDev;

import com.larryDev.entity.Familiar;
import com.larryDev.service.ContenedorAmigoService;
import com.larryDev.service.FamiliarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class AmigoInvisibleApplicationTests {

    @Autowired
    private FamiliarService familiarService;

    @Autowired
    private ContenedorAmigoService contenedorAmigoService;


    @Test
    void sorteoCompletoSinCoincidencias() {

        // 1. Sacamos todos los familiares de la BBDD
        List<Familiar> familiares =
                familiarService.listarTodosLosFamiliares();

        assertFalse(
                familiares.isEmpty(),
                "La lista de familiares no debería estar vacía"
        );

        System.out.println(
                "Número de familiares: " + familiares.size()
        );


        // 2. Simulamos que TODOS hacen el sorteo
        for (Familiar familiar : familiares) {

            System.out.println(
                    "Realizando sorteo para: "
                            + familiar.getNombre()
                            + " - ID: "
                            + familiar.getId()
            );

            contenedorAmigoService.asignarAmigo(
                    familiares,
                    familiar.getId()
            );
        }


        // 3. Miramos si queda alguien disponible
        List<Familiar> disponibles =
                familiarService.listarTodosLosFamiliaresDisponibles();

        System.out.println(
                "Familiares disponibles después del sorteo: "
                        + disponibles.size()
        );


        // 4. Miramos si ha habido coincidencias
        int coincidencias =
                contenedorAmigoService.consultaCoincidencias();

        System.out.println(
                "Coincidencias encontradas: "
                        + coincidencias
        );


        // 5. Comprobaciones

        assertTrue(
                disponibles.isEmpty(),
                "Después del sorteo no debería quedar ningún familiar disponible"
        );

        assertEquals(
                0,
                coincidencias,
                "No debería existir ninguna coincidencia"
        );


        System.out.println(
                "TEST CORRECTO: sorteo completado sin coincidencias, nadie ha salido repetido/a"
        );
    }
}