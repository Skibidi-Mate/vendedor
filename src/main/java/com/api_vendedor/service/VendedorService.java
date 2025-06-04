package com.api_vendedor.service;

import com.api_vendedor.dto.VendedorDTO;
import com.api_vendedor.models.Vendedor;
import com.api_vendedor.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository repository;

    public VendedorDTO guardar(VendedorDTO dto) {
        Vendedor vendedor = toEntity(dto);
        Vendedor saved = repository.save(vendedor);
        return toDTO(saved);
    }

    public List<VendedorDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<VendedorDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<VendedorDTO> actualizar(Integer id, VendedorDTO dto) {
        return repository.findById(id).map(vendedor -> {
            vendedor.setIdUsuario(dto.getIdUsuario());
            vendedor.setNombreCompleto(dto.getNombreCompleto());
            vendedor.setRut(dto.getRut());
            vendedor.setAreaVentas(dto.getAreaVentas());
            return toDTO(repository.save(vendedor));
        });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos auxiliares
    private VendedorDTO toDTO(Vendedor vendedor) {
        VendedorDTO dto = new VendedorDTO();
        dto.setIdVendedor(vendedor.getIdVendedor());
        dto.setIdUsuario(vendedor.getIdUsuario());
        dto.setNombreCompleto(vendedor.getNombreCompleto());
        dto.setRut(vendedor.getRut());
        dto.setAreaVentas(vendedor.getAreaVentas());
        return dto;
    }

    private Vendedor toEntity(VendedorDTO dto) {
        Vendedor vendedor = new Vendedor();
        vendedor.setIdVendedor(dto.getIdVendedor());
        vendedor.setIdUsuario(dto.getIdUsuario());
        vendedor.setNombreCompleto(dto.getNombreCompleto());
        vendedor.setRut(dto.getRut());
        vendedor.setAreaVentas(dto.getAreaVentas());
        return vendedor;
    }
}
