package com.api_vendedor.service;

import com.api_vendedor.dto.VendedorDTO;
import com.api_vendedor.models.Vendedor;
import com.api_vendedor.repository.VendedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendedorServiceTest {

    @Mock
    private VendedorRepository repository;

    @InjectMocks
    private VendedorService service;

    @Test
    void testGuardarVendedor() {
        VendedorDTO dto = new VendedorDTO();
        dto.setIdVendedor(1);
        dto.setIdUsuario(10);
        dto.setNombreCompleto("Pedro Pérez");
        dto.setRut("11.111.111-1");
        dto.setAreaVentas("Norte");

        Vendedor vendedorEntity = new Vendedor();
        vendedorEntity.setIdVendedor(1);
        vendedorEntity.setIdUsuario(10);
        vendedorEntity.setNombreCompleto("Pedro Pérez");
        vendedorEntity.setRut("11.111.111-1");
        vendedorEntity.setAreaVentas("Norte");

        when(repository.save(any(Vendedor.class))).thenReturn(vendedorEntity);

        VendedorDTO result = service.guardar(dto);

        assertNotNull(result);
        assertEquals("Pedro Pérez", result.getNombreCompleto());
        assertEquals("Norte", result.getAreaVentas());
        verify(repository).save(any(Vendedor.class));
    }

    @Test
    void testEliminarVendedorExistente() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(true);

        boolean eliminado = service.eliminar(id);

        assertTrue(eliminado);
        verify(repository).deleteById(id);
    }
}
