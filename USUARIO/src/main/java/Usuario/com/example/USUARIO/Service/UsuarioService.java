package Usuario.com.example.USUARIO.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Usuario.com.example.USUARIO.Model.Usuario;
import Usuario.com.example.USUARIO.Repository.UsuarioRepository;
import Usuario.com.example.USUARIO.WebUsuario.UserUsuario;
import Usuario.com.example.USUARIO.WebUsuario.ServicioClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired 
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserUsuario userUsuario;

    @Autowired
    private ServicioClient servicioClient;

    public Usuario guardarusuario(Usuario nuevo){
        return usuarioRepository.save(nuevo);
    }

    public List<Usuario> obteneruUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public boolean validarPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }


  

    public Map<String, Object> getServicioWebClient(Long id) {
        return servicioClient.getServicioById(id);
    }
}