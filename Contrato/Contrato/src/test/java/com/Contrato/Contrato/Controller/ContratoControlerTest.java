package com.Contrato.Contrato.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Controller.ContratoController;
import com.model.Contrato;
import com.service.ContratoService;
import com.Webproyecto.WebUsuario;
import com.Webproyecto.ServicioClient;
import com.Webproyecto.DireccionClient;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ContratoController.class)
public class ContratoControlerTest {

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerContratos_debeRetornarLista200() throws Exception {
        Contrato contrato = new Contrato();
        contrato.setId(1L);
        contrato.setFecha_contrato(Date.valueOf("2024-01-01"));
        contrato.setFecha_inicio(Date.valueOf("2024-01-05"));
        contrato.setFecha_final(Date.valueOf("2024-12-31"));
        contrato.setId_usuario(1L);
        contrato.setId_direcc(1L);
        contrato.setId_servicio(1L);
        contrato.setTotal(100000);

        when(contratoService.getContratos()).thenReturn(Arrays.asList(contrato));

        mockMvc.perform(get("/api/v1/contrato"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void obtenerContratoPorId_debeRetornar200() throws Exception {
        Contrato contrato = new Contrato();
        contrato.setId(1L);

        when(contratoService.getContratoPorId(1L)).thenReturn(contrato);

        mockMvc.perform(get("/api/v1/contrato/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerContratoPorId_idInvalido_debeRetornar400() throws Exception {
        mockMvc.perform(get("/api/v1/contrato/0"))
            .andExpect(status().isBadRequest());
        mockMvc.perform(get("/api/v1/contrato/-5"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void guardarContrato_debeRetornar201() throws Exception {
        Contrato contrato = new Contrato();
        contrato.setFecha_contrato(Date.valueOf("2024-01-01"));
        contrato.setFecha_inicio(Date.valueOf("2024-01-05"));
        contrato.setFecha_final(Date.valueOf("2024-12-31"));
        contrato.setId_usuario(1L);
        contrato.setId_direcc(1L);
        contrato.setId_servicio(1L);
        contrato.setTotal(100000);

        when(webUsuario.existeUsuario(1L)).thenReturn(true);
        when(servicioClient.existeServicio(1L)).thenReturn(true);
        when(direccionClient.existeDireccion(1L)).thenReturn(true);
        when(contratoService.saveContrato(any(Contrato.class))).thenReturn(contrato);

        mockMvc.perform(post("/api/v1/contrato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id_usuario").value(1));
    }

    @Test
    void guardarContrato_faltanCampos_debeRetornar400() throws Exception {
        Contrato contrato = new Contrato();

        mockMvc.perform(post("/api/v1/contrato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("La fecha de inicio es obligatoria y no puede estar vacía."));
    }

    @Test
    void guardarContrato_usuarioNoExiste_debeRetornar400() throws Exception {
        Contrato contrato = new Contrato();
        contrato.setFecha_contrato(Date.valueOf("2024-01-01"));
        contrato.setFecha_inicio(Date.valueOf("2024-01-05"));
        contrato.setFecha_final(Date.valueOf("2024-12-31"));
        contrato.setId_usuario(99L);
        contrato.setId_direcc(1L);
        contrato.setId_servicio(1L);
        contrato.setTotal(100000);

        when(webUsuario.existeUsuario(99L)).thenReturn(false);

        mockMvc.perform(post("/api/v1/contrato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de usuario no existe en la base de datos."));
    }

    @Test
    void guardarContrato_servicioNoExiste_debeRetornar400() throws Exception {
        Contrato contrato = new Contrato();
        contrato.setFecha_contrato(Date.valueOf("2024-01-01"));
        contrato.setFecha_inicio(Date.valueOf("2024-01-05"));
        contrato.setFecha_final(Date.valueOf("2024-12-31"));
        contrato.setId_usuario(1L);
        contrato.setId_direcc(1L);
        contrato.setId_servicio(99L);
        contrato.setTotal(100000);

        when(webUsuario.existeUsuario(1L)).thenReturn(true);
        when(servicioClient.existeServicio(99L)).thenReturn(false);

        mockMvc.perform(post("/api/v1/contrato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de servicio no existe en la base de datos."));
    }

    @Test
    void guardarContrato_direccionNoExiste_debeRetornar400() throws Exception {
        Contrato contrato = new Contrato();
        contrato.setFecha_contrato(Date.valueOf("2024-01-01"));
        contrato.setFecha_inicio(Date.valueOf("2024-01-05"));
        contrato.setFecha_final(Date.valueOf("2024-12-31"));
        contrato.setId_usuario(1L);
        contrato.setId_direcc(99L);
        contrato.setId_servicio(1L);
        contrato.setTotal(100000);

        when(webUsuario.existeUsuario(1L)).thenReturn(true);
        when(servicioClient.existeServicio(1L)).thenReturn(true);
        when(direccionClient.existeDireccion(99L)).thenReturn(false);

        mockMvc.perform(post("/api/v1/contrato")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contrato)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de dirección no existe en la base de datos."));
    }

    @Test
    void actualizarContrato_debeRetornar200() throws Exception {
        Contrato actualizado = new Contrato();
        actualizado.setFecha_contrato(Date.valueOf("2024-01-01"));
        actualizado.setFecha_inicio(Date.valueOf("2024-01-05"));
        actualizado.setFecha_final(Date.valueOf("2024-12-31"));
        actualizado.setId_usuario(1L);
        actualizado.setId_direcc(1L);
        actualizado.setId_servicio(1L);
        actualizado.setTotal(100000);

        when(webUsuario.existeUsuario(1L)).thenReturn(true);
        when(servicioClient.existeServicio(1L)).thenReturn(true);
        when(direccionClient.existeDireccion(1L)).thenReturn(true);
        when(contratoService.actualizarContrato(eq(1L), any(Contrato.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/contrato/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total").value(100000));
    }

    @Test
    void actualizarContrato_idInvalido_debeRetornar400() throws Exception {
        Contrato actualizado = new Contrato();
        actualizado.setFecha_contrato(Date.valueOf("2024-01-01"));
        actualizado.setFecha_inicio(Date.valueOf("2024-01-05"));
        actualizado.setFecha_final(Date.valueOf("2024-12-31"));
        actualizado.setId_usuario(1L);
        actualizado.setId_direcc(1L);
        actualizado.setId_servicio(1L);
        actualizado.setTotal(100000);

        mockMvc.perform(put("/api/v1/contrato/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("ID inválido. Debe ser mayor que cero."));
    }

    @Test
    void actualizarContrato_faltanCampos_debeRetornar400() throws Exception {
        Contrato actualizado = new Contrato();

        mockMvc.perform(put("/api/v1/contrato/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("La fecha de inicio es obligatoria y no puede estar vacía."));
    }

    @Test
    void actualizarContrato_usuarioNoExiste_debeRetornar400() throws Exception {
        Contrato actualizado = new Contrato();
        actualizado.setFecha_contrato(Date.valueOf("2024-01-01"));
        actualizado.setFecha_inicio(Date.valueOf("2024-01-05"));
        actualizado.setFecha_final(Date.valueOf("2024-12-31"));
        actualizado.setId_usuario(99L);
        actualizado.setId_direcc(1L);
        actualizado.setId_servicio(1L);
        actualizado.setTotal(100000);

        when(webUsuario.existeUsuario(99L)).thenReturn(false);

        mockMvc.perform(put("/api/v1/contrato/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de usuario no existe en la base de datos."));
    }

    @Test
    void actualizarContrato_servicioNoExiste_debeRetornar400() throws Exception {
        Contrato actualizado = new Contrato();
        actualizado.setFecha_contrato(Date.valueOf("2024-01-01"));
        actualizado.setFecha_inicio(Date.valueOf("2024-01-05"));
        actualizado.setFecha_final(Date.valueOf("2024-12-31"));
        actualizado.setId_usuario(1L);
        actualizado.setId_direcc(1L);
        actualizado.setId_servicio(99L);
        actualizado.setTotal(100000);

        when(webUsuario.existeUsuario(1L)).thenReturn(true);
        when(servicioClient.existeServicio(99L)).thenReturn(false);

        mockMvc.perform(put("/api/v1/contrato/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El ID de servicio no existe en la base de datos."));
    }

    @Test
    void actualizarContrato_direccionNoExiste_debeRetornar400() throws Exception {
        Contrato actualizado = new Contrato();
        actualizado.setFecha_contrato(Date.valueOf("2024-01-01"));
        actualizado.setFecha_inicio(Date.valueOf("2024-01-05"));
        actualizado.setFecha_final(Date.valueOf("2024-12-31"));
        actualizado.setId_usuario(1L);
        actualizado.setId_direcc(99L);
        actualizado.setId_servicio(1L);
        actualizado.setTotal(100000);

        when(webUsuario.existeUsuario(1L)).thenReturn(true);
        when(servicioClient.existeServicio(1L)).thenReturn(true);
        when(direccionClient.existeDireccion(99L)).thenReturn(false);

        mockMvc.perform(put("/api/v1/contrato/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isBadRequest())
.andExpect(content().string("El ID de dirección no existe en la base de datos."));


    }
}