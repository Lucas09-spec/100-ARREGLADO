package com.Proyecto.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.Proyecto.Model.Proyecto;
import com.Proyecto.Repository.ProyectoRepository;

import java.time.LocalDate;

@Component
public class ProyectoDataLoader implements CommandLineRunner {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (proyectoRepository.count() == 0) {
            proyectoRepository.save(new Proyecto(
                null,
                10L,         
                5L,          
                LocalDate.of(2025, 6, 22), 
                "Proyecto urgente",
                2L           
            ));

            proyectoRepository.save(new Proyecto(
                null,
                11L,
                6L,
                LocalDate.of(2025, 7, 10),
                "Revisión técnica",
                3L
            ));

            proyectoRepository.save(new Proyecto(
                null,
                12L,
                7L,
                LocalDate.of(2025, 8, 5),
                "Instalación finalizada",
                1L
            ));
        }
    }
}
