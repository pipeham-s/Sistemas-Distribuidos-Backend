package com.example.backend_sistemas_distribuidos.business.controllers;


import com.example.backend_sistemas_distribuidos.business.entities.Clase;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.managers.ClaseMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
@CrossOrigin(origins = "http://localhost:3000")
public class ClaseController {

    @Autowired
    ClaseMgr claseMgr;

    @GetMapping("/clasesProfesor/{cedula}")
    public ResponseEntity<List<Clase>> obtenerClasesProfesor(@PathVariable Long cedula){
        try{
            List<Clase> clasesProfesor = claseMgr.obtenerClasesProfesor(cedula);
            return ResponseEntity.ok(clasesProfesor);
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/clasesAlumno/{cedula}")
    public ResponseEntity<List<Clase>> obtenerClasesAlumno(@PathVariable Long cedula){
        try{
            List<Clase> clasesAlumno = claseMgr.obtenerClasesAlumno(cedula);

            return ResponseEntity.ok(clasesAlumno);
        } catch (EntidadNoExiste e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

}}
