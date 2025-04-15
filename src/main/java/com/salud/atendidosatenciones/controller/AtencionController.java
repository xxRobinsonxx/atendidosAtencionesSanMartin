package com.salud.atendidosatenciones.controller;

import com.salud.atendidosatenciones.model.Atencion;
import com.salud.atendidosatenciones.service.AtencionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionController {

    private final AtencionService service;

    public AtencionController(AtencionService service) {
        this.service = service;
    }

    // 1) Registrar
    @PostMapping
    public ResponseEntity<Atencion> registrarAtencion(@Valid @RequestBody Atencion atencion) {
        Atencion nueva = service.registrarAtencion(atencion);
        return ResponseEntity.ok(nueva);
    }

    // 2) Listar
    @GetMapping
    public ResponseEntity<List<Atencion>> listarAtenciones() {
        return ResponseEntity.ok(service.listarAtenciones());
    }

    // 3) Importar Excel
    @PostMapping("/importar")
    public ResponseEntity<List<Atencion>> importarExcel(@RequestParam("archivo") MultipartFile archivo) {
        List<Atencion> atencionesImportadas = service.importarExcel(archivo);
        return ResponseEntity.ok(atencionesImportadas);
    }
}
