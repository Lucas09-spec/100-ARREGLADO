package com.example.Categorias.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Categorias.model.Categoria;
import com.example.Categorias.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Operation(summary = "Obtiene todas las categorías")
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    @Operation(summary = "Obtiene una categoría por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public Categoria getCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría No Encontrada con ID: " + id));
    }

    @Operation(summary = "Guarda una categoría (nueva o existente)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada o actualizada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Categoria saveCategoria(Categoria nuevo) {
        if (nuevo.getNombre() == null || nuevo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
        return categoriaRepository.save(nuevo);
    }

    @Operation(summary = "Elimina una categoría por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public void delete(Long id) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(id);
        if (categoriaExistente.isEmpty()) {
            throw new RuntimeException("No se puede eliminar: Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}