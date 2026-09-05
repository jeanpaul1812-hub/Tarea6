package com.trabajo6.rest;

import com.trabajo6.jpa.CarEntity;
import com.trabajo6.jpa.CarRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autos")
public class CarRestController {
    private final CarRepository repository;

    public CarRestController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CarEntity> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarEntity> obtener(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CarEntity crear(@RequestBody CarEntity car) {
        car.setId(null);
        return repository.save(car);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarEntity> actualizar(@PathVariable Integer id, @RequestBody CarEntity car) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        car.setId(id);
        return ResponseEntity.ok(repository.save(car));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
