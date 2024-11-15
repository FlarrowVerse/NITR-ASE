package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.Area;
import com.ase.rrts.service.AreaService;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public List<Area> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> getAreaById(@PathVariable Integer id) {
        return areaService.getAreaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        return ResponseEntity.ok(areaService.createArea(area));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> updateArea(@PathVariable Integer id, @RequestBody Area area) {
        return ResponseEntity.ok(areaService.updateArea(id, area));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Integer id) {
        areaService.deleteArea(id);
        return ResponseEntity.noContent().build();
    }
}

