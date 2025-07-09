package Usuario.com.example.USUARIO.controller;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import Usuario.com.example.USUARIO.Controller.UsuarioController;
import Usuario.com.example.USUARIO.FeignClients.RoleFeignClient;
import Usuario.com.example.USUARIO.Model.Usuario;
import Usuario.com.example.USUARIO.Service.UsuarioService;

public class UsuarioControllerStandaloneTest {

    private MockMvc mockMvc;
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;  // Mock de la dependencia

    @Mock
    private RoleFeignClient roleFeignClient;  // También se mockea el FeignClient (aunque no se use en el test)

    @BeforeEach
    public void setup() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);

        // Instancia el controlador que queremos testear
        usuarioController = new UsuarioController();

        // Inyecta manualmente los mocks en el controlador usando ReflectionTestUtils
        ReflectionTestUtils.setField(usuarioController, "usuarioService", usuarioService);
        ReflectionTestUtils.setField(usuarioController, "roleFeignClient", roleFeignClient);

        // Configura MockMvc en modo standalone con el controlador
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    // Test: GET /api/v2/usuarios cuando la lista está vacía -> se espera retorno 204 No Content
    @Test
    public void testObtenerUsuarios_ReturnsNoContent() throws Exception {
        Mockito.when(usuarioService.obteneruUsuarios())
               .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/usuarios"))
               .andExpect(status().isNoContent());
    }

    // Test: GET /api/v2/usuarios/{usuarioId}/acciones para un usuario existente  
    // Con rol 1 asumimos que se interpreta como "Administrador" y se retorna la acción correspondiente
  @Test
public void testObtenerAccionesPorUsuarioId_ConRolAdministrador() throws Exception {
    Usuario user = new Usuario();
    user.setId(1L);
    user.setNombre("Usuario Uno");
    user.setCorreo("uno@example.com");
    user.setRol(1L);

    Mockito.when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(user);
    Mockito.when(roleFeignClient.obtenerNombreRol(1L)).thenReturn("Administrador");

    mockMvc.perform(get("/api/v2/usuarios/1/acciones"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombreUsuario", is("Usuario Uno")))
           .andExpect(jsonPath("$.rolNombre", is("Administrador")))
           .andExpect(jsonPath("$.accion", is("Gestiona usuarios, roles y realiza respaldos del sistema.")));
}

@Test
public void testObtenerAccionesPorUsuarioId_ConRolUsuario() throws Exception {
    Usuario user = new Usuario();
    user.setId(2L);
    user.setNombre("Usuario Dos");
    user.setCorreo("dos@example.com");
    user.setRol(2L);

    Mockito.when(usuarioService.obtenerUsuarioPorId(2L)).thenReturn(user);
    Mockito.when(roleFeignClient.obtenerNombreRol(2L)).thenReturn("Usuario");

    mockMvc.perform(get("/api/v2/usuarios/2/acciones"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombreUsuario", is("Usuario Dos")))
           .andExpect(jsonPath("$.rolNombre", is("Usuario")))
           .andExpect(jsonPath("$.accion", is("Acceso básico al sistema.")));
}

@Test
public void testObtenerAccionesPorUsuarioId_ConRolDesconocido() throws Exception {
    Usuario user = new Usuario();
    user.setId(3L);
    user.setNombre("Usuario Tres");
    user.setCorreo("tres@example.com");
    user.setRol(3L);

    Mockito.when(usuarioService.obtenerUsuarioPorId(3L)).thenReturn(user);
    Mockito.when(roleFeignClient.obtenerNombreRol(3L)).thenReturn(null);

    mockMvc.perform(get("/api/v2/usuarios/3/acciones"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombreUsuario", is("Usuario Tres")))
           .andExpect(jsonPath("$.rolNombre", is("Rol desconocido")))
           .andExpect(jsonPath("$.accion", is("Acciones.")));  // <-- aquí debe coincidir
}



    // Test: GET /api/v2/usuarios/{usuarioId}/acciones cuando no se encuentra el usuario -> se espera 404
    @Test
    public void testObtenerAccionesPorUsuarioId_NotFound() throws Exception {
        Mockito.when(usuarioService.obtenerUsuarioPorId(1L))
               .thenReturn(null);

        mockMvc.perform(get("/api/v2/usuarios/1/acciones"))
               .andExpect(status().isNotFound());
    }
}
