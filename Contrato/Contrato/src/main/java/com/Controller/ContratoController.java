package com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Contrato;
import com.service.ContratoService;
import com.Webproyecto.WebUsuario;
import com.Webproyecto.ServicioClient;
import com.Webproyecto.DireccionClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/contrato")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private WebUsuario webUsuario;

    @Autowired
    private ServicioClient servicioClient;

    @Autowired
    private DireccionClient direccionClient;
@Operation(summary = "Obtiene una lista con todos los contratos")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de contratos obtenida con éxito",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contrato.class)))),
    @ApiResponse(responseCode = "204", description = "No hay contratos para mostrar")
})

    @GetMapping
    public ResponseEntity<List<Contrato>> obtenerContratos() {
        List<Contrato> lista = contratoService.getContratos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
   @Operation(summary = "Obtiene un contrato por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato encontrado",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "400", description = "ID inválido"),
        @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerContratoPorId(@PathVariable Long id) {
        try {
            Contrato contrato = contratoService.getContratoPorId(id);
            return ResponseEntity.ok(contrato);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Obtiene un contrato por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato encontrado",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "400", description = "ID inválido"),
        @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
 public ResponseEntity<Contrato> obtenerContratoporId(@PathVariable Long id) {
    if (id == null || id <= 0) {
        return ResponseEntity.badRequest().build();
    }

    Contrato contrato = contratoService.getContratoPorId(id);
    if (contrato == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(contrato);
}
    @Operation(summary = "Guarda un nuevo contrato")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contrato creado con éxito",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<?> guardarContrato(
        @RequestBody(description = "Objeto contrato con los datos para crear un nuevo contrato", required = true,
            content = @Content(schema = @Schema(implementation = Contrato.class)))
        @org.springframework.web.bind.annotation.RequestBody Contrato nuevo) {

        if (nuevo == null) {
            return ResponseEntity.badRequest().body("El contrato no puede ser nulo.");
        }
        if (nuevo.getFecha_inicio() == null) {
            return ResponseEntity.badRequest().body("La fecha de inicio es obligatoria y no puede estar vacía.");
        }
        if (nuevo.getFecha_final() == null) {
            return ResponseEntity.badRequest().body("La fecha final es obligatoria y no puede estar vacía.");
        }
        if (nuevo.getFecha_contrato() == null) {
            return ResponseEntity.badRequest().body("La fecha del contrato es obligatoria y no puede estar vacía.");
        }
        if (nuevo.getTotal() == null) {
            return ResponseEntity.badRequest().body("El total es obligatorio y no puede estar vacío.");
        }
        if (nuevo.getId_direcc() == null) {
            return ResponseEntity.badRequest().body("El ID de dirección es obligatorio y no puede estar vacío.");
        }
        if (nuevo.getId_usuario() == null) {
            return ResponseEntity.badRequest().body("El ID de usuario es obligatorio y no puede estar vacío.");
        }
        if (nuevo.getId_servicio() == null) {
            return ResponseEntity.badRequest().body("El ID de servicio es obligatorio y no puede estar vacío.");
        }
        if (!webUsuario.existeUsuario(nuevo.getId_usuario())) {
            return ResponseEntity.badRequest().body("El ID de usuario no existe en la base de datos.");
        }
        if (!servicioClient.existeServicio(nuevo.getId_servicio())) {
            return ResponseEntity.badRequest().body("El ID de servicio no existe en la base de datos.");
        }
        if (!direccionClient.existeDireccion(nuevo.getId_direcc())) {
            return ResponseEntity.badRequest().body("El ID de dirección no existe en la base de datos.");
        }

        try {
            Contrato contratoGuardado = contratoService.saveContrato(nuevo);
            return ResponseEntity.status(201).body(contratoGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor.");
        }
    }   @Operation(summary = "Elimina un contrato por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarContrato(@PathVariable Long id) {
        try {
            contratoService.eliminarContrato(id);
            return ResponseEntity.ok("Contrato eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Contrato no encontrado");
        }
    }

    @Operation(summary = "Actualiza un contrato existente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrato actualizado con éxito",
            content = @Content(schema = @Schema(implementation = Contrato.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "404", description = "Contrato no encontrado",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "text/plain"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarContrato(
        @PathVariable Long id,
        @RequestBody(description = "Objeto contrato con los datos actualizados para el contrato", required = true,
            content = @Content(schema = @Schema(implementation = Contrato.class)))
        @org.springframework.web.bind.annotation.RequestBody Contrato actualizado) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID inválido. Debe ser mayor que cero.");
        }
        if (actualizado == null) {
            return ResponseEntity.badRequest().body("Datos del contrato no pueden ser nulos.");
        }
        if (actualizado.getFecha_inicio() == null) {
            return ResponseEntity.badRequest().body("La fecha de inicio es obligatoria y no puede estar vacía.");
        }
        if (actualizado.getFecha_final() == null) {
            return ResponseEntity.badRequest().body("La fecha final es obligatoria y no puede estar vacía.");
        }
        if (actualizado.getFecha_contrato() == null) {
            return ResponseEntity.badRequest().body("La fecha del contrato es obligatoria y no puede estar vacía.");
        }
        if (actualizado.getTotal() == null) {
            return ResponseEntity.badRequest().body("El total es obligatorio y no puede estar vacío.");
        }
        if (actualizado.getId_direcc() == null) {
            return ResponseEntity.badRequest().body("El ID de dirección es obligatorio y no puede estar vacío.");
        }
        if (actualizado.getId_usuario() == null) {
            return ResponseEntity.badRequest().body("El ID de usuario es obligatorio y no puede estar vacío.");
        }
        if (actualizado.getId_servicio() == null) {
            return ResponseEntity.badRequest().body("El ID de servicio es obligatorio y no puede estar vacío.");
        }
        if (!webUsuario.existeUsuario(actualizado.getId_usuario())) {
            return ResponseEntity.badRequest().body("El ID de usuario no existe en la base de datos.");
        }
        if (!servicioClient.existeServicio(actualizado.getId_servicio())) {
            return ResponseEntity.badRequest().body("El ID de servicio no existe en la base de datos.");
        }
        if (!direccionClient.existeDireccion(actualizado.getId_direcc())) {
            return ResponseEntity.badRequest().body("El ID de dirección no existe en la base de datos.");
        }

        try {
            Contrato contratoActualizado = contratoService.actualizarContrato(id, actualizado);
            return ResponseEntity.ok(contratoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor.");
        }
    }
}
