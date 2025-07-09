package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Contrato;
import com.Repository.ContratoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Operation(summary = "Obtiene una lista con todos los contratos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contratos obtenida con éxito",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "204", description = "No hay contratos para mostrar")
    })
    public List<Contrato> getContratos() {
        return contratoRepository.findAll();
    }

    @Operation(summary = "Obtiene un contrato por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato encontrado",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "400", description = "ID inválido"),
        @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    public Contrato getContratoPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido. Debe ser mayor que cero.");
        }
        return contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato con ID " + id + " no encontrado"));
    }

    @Operation(summary = "Guarda un nuevo contrato")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contrato creado con éxito",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Contrato saveContrato(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException("El contrato no puede ser nulo.");
        }
        if (contrato.getFecha_inicio() == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria y no puede estar vacía.");
        }
        if (contrato.getFecha_final() == null) {
            throw new IllegalArgumentException("La fecha final es obligatoria y no puede estar vacía.");
        }
        if (contrato.getFecha_contrato() == null) {
            throw new IllegalArgumentException("La fecha del contrato es obligatoria y no puede estar vacía.");
        }
        if (contrato.getTotal() == null) {
            throw new IllegalArgumentException("El total es obligatorio y no puede estar vacío.");
        }
        if (contrato.getId_usuario() == null) {
            throw new IllegalArgumentException("El ID de usuario es obligatorio y no puede estar vacío.");
        }
        if (contrato.getId_servicio() == null) {
            throw new IllegalArgumentException("El ID de servicio es obligatorio y no puede estar vacío.");
        }
        if (contrato.getId_direcc() == null) {
            throw new IllegalArgumentException("El ID de dirección es obligatorio y no puede estar vacío.");
        }

        return contratoRepository.save(contrato);
    }

    @Operation(summary = "Actualiza un contrato existente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato actualizado con éxito",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes"),
        @ApiResponse(responseCode = "404", description = "Contrato no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Contrato actualizarContrato(Long id, Contrato contratoActualizado) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido. Debe ser mayor que cero.");
        }
        if (contratoActualizado == null) {
            throw new IllegalArgumentException("Datos del contrato no pueden ser nulos.");
        }
        if (contratoActualizado.getFecha_inicio() == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria y no puede estar vacía.");
        }
        if (contratoActualizado.getFecha_final() == null) {
            throw new IllegalArgumentException("La fecha final es obligatoria y no puede estar vacía.");
        }
        if (contratoActualizado.getFecha_contrato() == null) {
            throw new IllegalArgumentException("La fecha del contrato es obligatoria y no puede estar vacía.");
        }
        if (contratoActualizado.getTotal() == null) {
            throw new IllegalArgumentException("El total es obligatorio y no puede estar vacío.");
        }
        if (contratoActualizado.getId_usuario() == null) {
            throw new IllegalArgumentException("El ID de usuario es obligatorio y no puede estar vacío.");
        }
        if (contratoActualizado.getId_servicio() == null) {
            throw new IllegalArgumentException("El ID de servicio es obligatorio y no puede estar vacío.");
        }
        if (contratoActualizado.getId_direcc() == null) {
            throw new IllegalArgumentException("El ID de dirección es obligatorio y no puede estar vacío.");
        }

        Optional<Contrato> contratoExistente = contratoRepository.findById(id);

        if (contratoExistente.isPresent()) {
            Contrato contrato = contratoExistente.get();

            contrato.setFecha_contrato(contratoActualizado.getFecha_contrato());
            contrato.setFecha_inicio(contratoActualizado.getFecha_inicio());
            contrato.setFecha_final(contratoActualizado.getFecha_final());
            contrato.setTotal(contratoActualizado.getTotal());
            contrato.setId_usuario(contratoActualizado.getId_usuario());
            contrato.setId_direcc(contratoActualizado.getId_direcc());
            contrato.setId_servicio(contratoActualizado.getId_servicio());

            return contratoRepository.save(contrato);
        } else {
            throw new RuntimeException("Contrato con ID " + id + " no encontrado para actualizar");
        }
    }
    @Operation(summary = "Elimina un contrato por su ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Contrato eliminado con éxito"),
    @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
})
public void eliminarContrato(Long id) {
    if (id == null || id <= 0) {
        throw new IllegalArgumentException("ID inválido. Debe ser mayor que cero.");
    }

    if (!contratoRepository.existsById(id)) {
        throw new RuntimeException("Contrato con ID " + id + " no encontrado");
    }

    contratoRepository.deleteById(id);
}
}
