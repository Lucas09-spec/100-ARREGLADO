package Estado.com.example.ESTADO.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import Estado.com.example.ESTADO.Model.ESTADO;
import Estado.com.example.ESTADO.Repository.ESTADORepository;

@Component
public class EstadoDataLoader implements CommandLineRunner {

    @Autowired
    private ESTADORepository estadoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (estadoRepository.count() == 0) {
            estadoRepository.save(new ESTADO(null, "Activo"));
            estadoRepository.save(new ESTADO(null, "Inactivo"));
            estadoRepository.save(new ESTADO(null, "Pendiente"));
        }
    }
}
