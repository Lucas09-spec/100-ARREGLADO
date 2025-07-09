package com.Direccion.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.Direccion.Model.Region;
import com.Direccion.Model.Comuna;
import com.Direccion.Model.Direccion;

import com.Direccion.Repository.RegRepository;
import com.Direccion.Repository.ComRepository;
import com.Direccion.Repository.DireccionRepository;

@Component
public class dataloader implements CommandLineRunner {

    @Autowired
    private RegRepository regionRepository;

    @Autowired
    private ComRepository comunaRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    public void run(String... args) throws Exception {

        if (regionRepository.count() == 0) {
            regionRepository.save(new Region(1L, "Región Metropolitana"));
            regionRepository.save(new Region(2L, "Región de Valparaíso"));
        }

        if (comunaRepository.count() == 0) {
            comunaRepository.save(new Comuna(1L, 1L, "Santiago"));
            comunaRepository.save(new Comuna(2L, 1L, "Providencia"));
            comunaRepository.save(new Comuna(3L, 2L, "Viña del Mar"));
        }

        if (direccionRepository.count() == 0) {
            direccionRepository.save(new Direccion(1L, 1L, 1L, "Calle Falsa 123"));
            direccionRepository.save(new Direccion(2L, 1L, 2L, "Avenida Siempre Viva 742"));
            direccionRepository.save(new Direccion(3L, 2L, 3L, "Boulevard Valparaíso 100"));
        }
    }
}
