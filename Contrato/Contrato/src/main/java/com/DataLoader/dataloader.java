package com.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.model.Contrato;
import com.Repository.ContratoRepository;

import java.sql.Date;

@Component
public class dataloader implements CommandLineRunner {

    @Autowired
    private ContratoRepository contratoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (contratoRepository.count() == 0) {
            contratoRepository.save(new Contrato(
                null,
                Date.valueOf("2025-06-01"),  // fecha_contrato
                10L,                        // id_usuario
                5L,                         // id_direcc
                150000,                     // total
                3L,                         // id_servicio
                Date.valueOf("2025-07-01"), // fecha_inicio
                Date.valueOf("2026-07-01")  // fecha_final
            ));
            contratoRepository.save(new Contrato(
                null,
                Date.valueOf("2025-07-10"),
                12L,
                7L,
                200000,
                4L,
                Date.valueOf("2025-08-01"),
                Date.valueOf("2026-08-01")
            ));
            contratoRepository.save(new Contrato(
                null,
                Date.valueOf("2025-08-05"),
                15L,
                9L,
                175000,
                5L,
                Date.valueOf("2025-09-01"),
                Date.valueOf("2026-09-01")
            ));
        }
    }
}
