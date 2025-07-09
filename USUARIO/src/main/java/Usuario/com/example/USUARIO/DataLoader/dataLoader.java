package Usuario.com.example.USUARIO.DataLoader;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import Usuario.com.example.USUARIO.Model.Usuario;
import Usuario.com.example.USUARIO.Repository.UsuarioRepository;

@Component
public class dataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

        // Solo insertar si la tabla está vacía
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario(null, "user1@example.com", "password123", "Lucas", "Araneda", "987654321", 1L));
            usuarioRepository.save(new Usuario(null, "user2@example.com", "password456", "Benjamín", "Soto", "912345678", 2L));
            usuarioRepository.save(new Usuario(null, "user3@example.com", "password789", "Claudio", "Pérez", "998877665", 3L));
        }
    }
}