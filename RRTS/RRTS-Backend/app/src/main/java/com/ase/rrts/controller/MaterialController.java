package com.ase.rrts.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.Material;
import com.ase.rrts.model.MaterialDTO;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.service.MaterialService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @GetMapping
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable UUID id) {
        return materialService.getMaterialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        return ResponseEntity.ok(materialService.createMaterial(material));
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable UUID id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> updateResource(@PathVariable UUID id, @RequestBody MaterialDTO materialDTO) {
        try {
            materialService.updateMaterial(id, materialDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Resource updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failed", "Resource was not updated!"));
        }
    }
}
