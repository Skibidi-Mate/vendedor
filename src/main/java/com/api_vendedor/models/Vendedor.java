package com.api_vendedor.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vendedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVendedor;

    private Integer idUsuario;

    private String nombreCompleto;

    private String rut;

    private String areaVentas;

}
