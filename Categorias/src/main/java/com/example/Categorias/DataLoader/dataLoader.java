package com.example.Categorias.DataLoader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Categorias.model.Categoria;
import com.example.Categorias.repository.CategoriaRepository;

@Component
public class dataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {

        // Solo insertar si la tabla está vacía
        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(new Categoria(null, "Instalación de paneles solares", "Servicios de instalación de paneles solares"));
            categoriaRepository.save(new Categoria(null, "Mantenimiento de paneles solares", "Servicios de mantenimiento preventivo y correctivo"));
            categoriaRepository.save(new Categoria(null, "Compra de paneles solares", "Venta de paneles solares"));
        }
    }
}