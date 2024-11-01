package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.Materia;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;
import com.example.backend_sistemas_distribuidos.persistance.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaMgr {

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Crea una nueva materia.
     *
     * @param nombreMateria Nombre de la materia a crear.
     * @throws InvalidInformation Si el nombre de la materia es nulo o vacío.
     */
    public void crearMateria(String nombreMateria) throws InvalidInformation {
        // Validar parámetros de entrada
        if (nombreMateria == null || nombreMateria.trim().isEmpty()) {
            throw new InvalidInformation("El nombre de la materia no puede ser nulo o vacío.");
        }

        // Crear la nueva materia
        Materia materia = new Materia();
        materia.setNombre(nombreMateria.trim());

        // Guardar la materia
        try {
            materiaRepository.save(materia);
            System.out.println("Materia creada: " + materia.getNombre());
        } catch (Exception e) {
            System.out.println("Error al guardar la materia: " + e.getMessage());
        }
    }
    public List<Materia> obtenerTodasLasMaterias() {
        return (List<Materia>) materiaRepository.findAll();
    }
}
