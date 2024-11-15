package com.ase.rrts.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.Material;
import com.ase.rrts.model.MaterialDTO;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/materials")
@Tag(name = "Materials", description = "Endpoints related to materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @GetMapping
    @Operation(summary = "All Materials", description = "Retireves a list of all materials")
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @GetMapping("/{id}")
    @Operation(summary = "Single Material", description = "Retrieves single material details")
    public ResponseEntity<Material> getMaterialById(@PathVariable UUID id) {
        return materialService.getMaterialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @PostMapping
    @Operation(summary = "Create Material", description = "Adds a new material to DB")
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        return ResponseEntity.ok(materialService.createMaterial(material));
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Material", description = "Deletes an material based on UUID")
    public ResponseEntity<Void> deleteMaterial(@PathVariable UUID id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @PatchMapping("/update/{id}")
    @Operation(summary = "Update material", description = "Updates a material based on UUID")
    public ResponseEntity<ResponseMessage> updateResource(@PathVariable UUID id, @RequestBody MaterialDTO materialDTO) {
        try {
            materialService.updateMaterial(id, materialDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Resource updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failed", "Resource was not updated!"));
        }
    }
}
