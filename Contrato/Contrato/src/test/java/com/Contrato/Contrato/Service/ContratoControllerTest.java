package com.Contrato.Contrato.Service;

import com.Controller.ContratoController;
import com.Webproyecto.WebUsuario;
import com.Webproyecto.ServicioClient;
import com.Webproyecto.DireccionClient;
import com.model.Contrato;
import com.service.ContratoService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ContratoController.class)
public class ContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContratoService contratoService;

    @MockBean
    private WebUsuario webUsuario;

    @MockBean
    private ServicioClient servicioClient;

    @MockBean
    private DireccionClient direccionClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Contrato contratoEjemplo() {
        Contrato contrato = new Contrato();
        contrato.setId(1L);
        contrato.setFecha_contrato(Date.valueOf("2025-01-01"));
        contrato.setFecha_inicio(Date.valueOf("2025-01-02"));
        contrato.setFecha_final(Date.valueOf("2025-12-31"));
        contrato.setTotal(100000);
        contrato.setId_usuario(10L);
        contrato.setId_direcc(20L);
        contrato.setId_servicio(30L);
        return contrato;
    }

    // Casos exitosos:

    @Test
    void testObtenerContratos_OK() throws Exception {
        Mockito.when(contratoService.getContratos()).thenReturn(Arrays.asList(contratoEjemplo()));

        mockMvc.perform(get("/api/v1/contrato"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testObtenerContratoPorId_OK() throws Exception {
        Mockito.when(contratoService.getContratoPorId(1L)).thenReturn(contratoEjemplo());

        mockMvc.perform(get("/api/v1/contrato/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGuardarContrato_OK() throws Exception {
        Contrato contrato = contratoEjemplo();

        Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(true);
        Mockito.when(servicioClient.existeServicio(30L)).thenReturn(true);
        Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(true);
        Mockito.when(contratoService.saveContrato(any(Contrato.class))).thenReturn(contrato);

        mockMvc.perform(post("/api/v1/contrato")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActualizarContrato_OK() throws Exception {
        Contrato contrato = contratoEjemplo();

        Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(true);
        Mockito.when(servicioClient.existeServicio(30L)).thenReturn(true);
        Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(true);
        Mockito.when(contratoService.actualizarContrato(eq(1L), any(Contrato.class))).thenReturn(contrato);

        mockMvc.perform(put("/api/v1/contrato/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }


    // Casos de error (validaciones):

    @Test
    void testGuardarContrato_usuarioNoExiste_devuelve400() throws Exception {
        Contrato contrato = contratoEjemplo();

        Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(false);  // Usuario no existe
        Mockito.when(servicioClient.existeServicio(30L)).thenReturn(true);
        Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(true);

        mockMvc.perform(post("/api/v1/contrato")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de usuario no existe en la base de datos."));
    }

    @Test
    void testGuardarContrato_servicioNoExiste_devuelve400() throws Exception {
        Contrato contrato = contratoEjemplo();

        Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(true);
        Mockito.when(servicioClient.existeServicio(30L)).thenReturn(false);  // Servicio no existe
        Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(true);

        mockMvc.perform(post("/api/v1/contrato")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de servicio no existe en la base de datos."));
    }

    @Test
    void testGuardarContrato_direccionNoExiste_devuelve400() throws Exception {
        Contrato contrato = contratoEjemplo();

        Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(true);
        Mockito.when(servicioClient.existeServicio(30L)).thenReturn(true);
        Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(false);  // Dirección no existe

        mockMvc.perform(post("/api/v1/contrato")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de dirección no existe en la base de datos."));
    }


    @Test
    void testActualizarContrato_usuarioNoExiste_devuelve400() throws Exception {
        Contrato contrato = contratoEjemplo();

        Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(false);
        Mockito.when(servicioClient.existeServicio(30L)).thenReturn(true);
        Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(true);

        mockMvc.perform(put("/api/v1/contrato/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de usuario no existe en la base de datos."));
    }

    @Test
    void testActualizarContrato_servicioNoExiste_devuelve400() throws Exception {
        Contrato contrato = contratoEjemplo();

        Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(true);
        Mockito.when(servicioClient.existeServicio(30L)).thenReturn(false);
        Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(true);

        mockMvc.perform(put("/api/v1/contrato/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de servicio no existe en la base de datos."));
    }

  @Test
void testActualizarContrato_direccionNoExiste_devuelve400() throws Exception {
    Contrato contrato = contratoEjemplo();

    Mockito.when(webUsuario.existeUsuario(10L)).thenReturn(true);
    Mockito.when(servicioClient.existeServicio(30L)).thenReturn(true);
    Mockito.when(direccionClient.existeDireccion(20L)).thenReturn(false);

    mockMvc.perform(put("/api/v1/contrato/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contrato)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("El ID de dirección no existe en la base de datos.")); // Aquí cambiaste el "El" por "E"
}

    // Validación de ID inválido en actualización

    @Test
    void testActualizarContrato_idInvalido_devuelve400() throws Exception {
        Contrato contrato = contratoEjemplo();

        mockMvc.perform(put("/api/v1/contrato/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("ID inválido. Debe ser mayor que cero."));
    }

    // Validación contrato nulo en guardar

    @Test
    void testGuardarContrato_nullBody_devuelve400() throws Exception {
        mockMvc.perform(post("/api/v1/contrato")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest());
    }

}
