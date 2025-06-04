package com.api_vendedor.dto;

import lombok.Data;

@Data
public class VendedorDTO {
    private Integer idVendedor;
    private Integer idUsuario;
    private String nombreCompleto;
    private String rut;
    private String areaVentas;
}
