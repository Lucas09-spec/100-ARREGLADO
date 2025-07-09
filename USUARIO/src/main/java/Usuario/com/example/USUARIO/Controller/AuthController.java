package Usuario.com.example.USUARIO.Controller;
import Usuario.com.example.USUARIO.Security.LoginRequest;
import Usuario.com.example.USUARIO.Security.MessageResponse;
import Usuario.com.example.USUARIO.Model.Usuario;

import Usuario.com.example.USUARIO.Service.UsuarioService;
import Usuario.com.example.USUARIO.WebUsuario.ServicioClient;
import Usuario.com.example.USUARIO.WebUsuario.UserUsuario;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
  @Autowired
    private ServicioClient servicioClient;
   
    @Autowired
    private UserUsuario userUsuario;

    @Autowired 
    private UsuarioService usuarioService;

 @PostMapping("/register")
public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
    try {
        if (!userUsuario.rolExistePorId(usuario.getRol())) {
            return ResponseEntity.badRequest().body("Error: El ID de rol no existe.");
        }
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(201).body(creado);
    } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(400).body("Error: El correo ya existe o hay datos inválidos.");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error al crear usuario: " + e.getMessage());
    }
}

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);


            return ResponseEntity.ok(new MessageResponse("Te has logeado correctamente"));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Correo o contraseña incorrectos"));
        }
    }
  @PostMapping("/{usuarioId}/comprar/{servicioId}")
    public ResponseEntity<?> comprarServicio(@PathVariable Long usuarioId, @PathVariable Long servicioId) {
        try {
            // Obtener usuario directo del servicio
            Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
            if (usuario == null) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            // Obtener servicio por WebClient
            Map<String, Object> servicio = servicioClient.getServicioById(servicioId);
            if (servicio == null || servicio.isEmpty()) {
                return ResponseEntity.status(404).body("Servicio no encontrado");
            }

            // Armar respuesta con info completa
            Map<String, Object> compraInfo = new HashMap<>();
            compraInfo.put("usuario", usuario);
            compraInfo.put("servicio", servicio);
            compraInfo.put("fechaCompra", LocalDate.now().toString());
            compraInfo.put("mensaje", "Compra realizada con éxito");

            return ResponseEntity.ok(compraInfo);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la compra: " + e.getMessage());
        }
    }}
