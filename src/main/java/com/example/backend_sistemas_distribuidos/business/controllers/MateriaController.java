package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;
import com.example.backend_sistemas_distribuidos.business.managers.MateriaMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.backend_sistemas_distribuidos.business.entities.Materia;

import java.util.List;


@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    @Autowired
    private MateriaMgr materiaMgr;

    /**
     * Endpoint para crear una nueva materia.
     *
     * @param materia Nombre de la materia a crear.
     * @return Mensaje indicando el resultado de la operación.
     */
    @PostMapping("/crear")
    public String crearMateria(@RequestBody Materia materia) {
        try {
            materiaMgr.crearMateria(materia.getNombre());
            return "Materia creada exitosamente: " + materia.getNombre();
        } catch (InvalidInformation e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/todas")
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaMgr.obtenerTodasLasMaterias();
    }
}