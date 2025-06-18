package com.api_vendedor.controller;

import com.api_vendedor.dto.VendedorDTO;
import com.api_vendedor.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorservice;

    @PostMapping
    public ResponseEntity<VendedorDTO> crear(@RequestBody VendedorDTO dto) {
        VendedorDTO creado = vendedorservice.guardar(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> listar() {
        return ResponseEntity.ok(vendedorservice.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> obtener(@PathVariable Integer id) {
        return vendedorservice.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendedorDTO> actualizar(@PathVariable Integer id, @RequestBody VendedorDTO dto) {
        return vendedorservice.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return vendedorservice.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/hateoas/{id}")
    public VendedorDTO obtenerHATEOAS(@PathVariable Integer id) {
           VendedorDTO dto = vendedorservice.obtenerPorId(id);
        
        dto.add(linkTo(methodOn(VendedorController.class).obtenerHATEOAS(id)).withSelfRel());
        dto.add(linkTo(methodOn(VendedorController.class).obtenerTodosHATEOAS()).withRel("todos"));
        dto.add(linkTo(methodOn(VendedorController.class).eliminar(id)).withRel("eliminar"));

        return dto;
    }

    //METODO HATEOAS para listar todos los productos utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<VendedorDTO> obtenerTodosHATEOAS() {
        List<VendedorDTO> lista = vendedorservice.listar();

        for (VendedorDTO dto : lista) {
            dto.add(linkTo(methodOn(VendedorController.class).obtenerHATEOAS(dto.getId())).withSelfRel());
        }

        return lista;
    }
}
