package Servicio.com.example.Servicio.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import Servicio.com.example.Servicio.Model.Servicio;
import Servicio.com.example.Servicio.Repository.ServicioRepository;

@Component
public class DATALOADER implements CommandLineRunner {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (servicioRepository.count() == 0) {
            servicioRepository.save(new Servicio(null, "Instalación de paneles solares", 150000L, true));
            servicioRepository.save(new Servicio(null, "Mantenimiento de sistemas eléctricos", 90000L, true));
            servicioRepository.save(new Servicio(null, "Reparación de aire acondicionado", 70000L, false));
        }
    }
}
