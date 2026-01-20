package com.larryDev.entity;

import jakarta.persistence.*;

@Entity
@Table(name="contenedor_de_amigos")
public class ContenedorAmigoSeleccionado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="familiar_seleccionado_id" , nullable = false)
    private Integer familiarSeleccionadoId;
    @Column(name="amigo_tocado_id" , nullable = false)
    private Integer amigoTocadoId;
    public ContenedorAmigoSeleccionado() {
    }
    public ContenedorAmigoSeleccionado(Integer familiarSeleccionadoId,Integer amigoTocadoId) {
        this.familiarSeleccionadoId = familiarSeleccionadoId;
        this.amigoTocadoId = amigoTocadoId;
    }

    public Integer getAmigoTocadoId() {
        return amigoTocadoId;
    }

    public void setAmigoTocadoId(Integer amigoTocadoId) {
        this.amigoTocadoId = amigoTocadoId;
    }

    public int getId() {
        return id;
    }

    public Integer getFamiliarSeleccionadoId() {
        return familiarSeleccionadoId;
    }

    public void setFamiliarSeleccionadoId(Integer familiarSeleccionadoId) {
        this.familiarSeleccionadoId = familiarSeleccionadoId;
    }

    @Override
    public String toString() {
        return "ContenedorAmigoSeleccionado{" +
                "id=" + id +
                ", familiarSeleccionadoId=" + familiarSeleccionadoId +
                '}';
    }
}
