package Usuario.com.example.USUARIO.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Usuario.com.example.USUARIO.Model.Usuario;
import Usuario.com.example.USUARIO.Service.UsuarioService;
import Usuario.com.example.USUARIO.FeignClients.RoleFeignClient;

  
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; 

    @Autowired
    private RoleFeignClient roleFeignClient;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerusuarios() {
        List<Usuario> lista = usuarioService.obteneruUsuarios();
        if (lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

@GetMapping("/{id}/acciones")
public ResponseEntity<Map<String, String>> obtenerAccionesPorUsuarioId(@PathVariable("id") Long id) {
    Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
    if (usuario == null) {
        return ResponseEntity.notFound().build();
    }

    String nombreRol = roleFeignClient.obtenerNombreRol(usuario.getRol());

    String accion;
    if (nombreRol == null) {
        nombreRol = "Rol desconocido";
        accion = "Acciones.";
    } else if ("Administrador".equalsIgnoreCase(nombreRol)) {
        accion = "Gestiona usuarios, roles y realiza respaldos del sistema.";
    } else if ("Usuario".equalsIgnoreCase(nombreRol)) {
        accion = "Acceso básico al sistema.";
    } else {
        accion = "Acciones no definidas para este rol.";
    }

    Map<String, String> response = new HashMap<>();
    response.put("nombreUsuario", usuario.getNombre());
    response.put("rolNombre", nombreRol);
    response.put("accion", accion);

    return ResponseEntity.ok(response);
}
}