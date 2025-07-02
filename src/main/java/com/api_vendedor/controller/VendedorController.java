package com.api_vendedor.controller;

import com.api_vendedor.dto.VendedorDTO;
import com.api_vendedor.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @PostMapping
    public ResponseEntity<VendedorDTO> crear(@RequestBody VendedorDTO vendedor) {
        VendedorDTO creado = vendedorService.guardar(vendedor);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> listar() {
        return ResponseEntity.ok(vendedorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> obtener(@PathVariable Integer id) {
        return vendedorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendedorDTO> actualizar(@PathVariable Integer id, @RequestBody VendedorDTO vendedor) {
        return vendedorService.actualizar(id, vendedor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return vendedorService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/hateoas/{id}")
    public VendedorDTO obtenerHATEOAS(@PathVariable Integer id) {
        VendedorDTO vendedor = vendedorService.obtenerPorId(id).orElse(null);
        if (vendedor == null) {
            return null;
        }

        vendedor.add(linkTo(methodOn(VendedorController.class).obtenerHATEOAS(id)).withSelfRel());
        vendedor.add(linkTo(methodOn(VendedorController.class).obtenerTodosHATEOAS()).withRel("todos"));
        vendedor.add(linkTo(methodOn(VendedorController.class).eliminar(id)).withRel("eliminar"));

        vendedor.add(Link.of("http://localhost:8083/api/proxy/vendedores/" + vendedor.getIdVendedor()).withSelfRel());
        vendedor.add(Link.of("http://localhost:8083/api/proxy/vendedores/" + vendedor.getIdVendedor()).withRel("Modificar HATEOAS").withType("PUT"));
        vendedor.add(Link.of("http://localhost:8083/api/proxy/vendedores/" + vendedor.getIdVendedor()).withRel("Eliminar HATEOAS").withType("DELETE"));

        return vendedor;
    }

    @GetMapping("/hateoas")
    public List<VendedorDTO> obtenerTodosHATEOAS() {
        List<VendedorDTO> lista = vendedorService.listar();

        for (VendedorDTO vendedor : lista) {
            vendedor.add(linkTo(methodOn(VendedorController.class).obtenerHATEOAS(vendedor.getIdVendedor())).withSelfRel());
            vendedor.add(Link.of("http://localhost:8083/api/proxy/vendedores").withRel("Get todos HATEOAS"));
            vendedor.add(Link.of("http://localhost:8083/api/proxy/vendedores/" + vendedor.getIdVendedor()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }
}
