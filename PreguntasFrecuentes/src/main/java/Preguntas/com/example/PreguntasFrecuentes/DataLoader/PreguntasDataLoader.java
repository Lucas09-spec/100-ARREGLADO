package Preguntas.com.example.PreguntasFrecuentes.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import Preguntas.com.example.PreguntasFrecuentes.Model.Preguntas;
import Preguntas.com.example.PreguntasFrecuentes.Repository.PreguntaRepository;

@Component
public class PreguntasDataLoader implements CommandLineRunner {

    @Autowired
    private PreguntaRepository preguntasRepository;

    @Override
    public void run(String... args) throws Exception {
        if (preguntasRepository.count() == 0) {
            preguntasRepository.save(new Preguntas(null, "¿Cómo puedo crear una cuenta?", "Puedes crear una cuenta haciendo clic en registrar."));
            preguntasRepository.save(new Preguntas(null, "¿Cuál es el horario de atención?", "Atendemos de lunes a viernes de 9 a 18 hrs."));
            preguntasRepository.save(new Preguntas(null, "¿Cómo recupero mi contraseña?", "Usa el enlace 'Olvidé mi contraseña' en la pantalla de login."));
        }
    }
}
