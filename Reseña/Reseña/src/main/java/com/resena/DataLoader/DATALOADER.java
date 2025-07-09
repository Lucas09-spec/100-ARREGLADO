package com.resena.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.resena.model.Resena;
import com.resena.repository.ResenaRepository;

import java.sql.Date;

@Component
public class DATALOADER implements CommandLineRunner {

    private final ResenaRepository resenaRepository;

    public DATALOADER(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        resenaRepository.deleteAll();

        resenaRepository.save(new Resena(null, 100L, 200L, "Muy buen servicio", Date.valueOf("2025-06-22"), 5));
        resenaRepository.save(new Resena(null, 101L, 201L, "Regular, esperaba más", Date.valueOf("2025-06-23"), 3));
        resenaRepository.save(new Resena(null, 102L, 202L, "Excelente atención", Date.valueOf("2025-06-24"), 4));
    }
}
