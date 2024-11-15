package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.exceptions.ResourceNotFoundException;
import com.ase.rrts.model.Material;
import com.ase.rrts.model.MaterialDTO;
import com.ase.rrts.model.MaterialEstimateDTO;
import com.ase.rrts.repository.MaterialRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public Optional<Material> getMaterialById(UUID id) {
        return materialRepository.findById(id);
    }

    public Material createMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material updateMaterial(UUID id, Material material) {
        if (materialRepository.existsById(id)) {
            material.setId(id);
            return materialRepository.save(material);
        }
        return null;
    }

    public void updateMaterial(UUID id, MaterialDTO materialDTO) {
        if (materialRepository.existsById(id)) {
            Material material = materialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such raw material"));
            
            if (materialDTO.getInventory() != null) {
                material.setInventory(materialDTO.getInventory());
            }

            if (materialDTO.getCost() != null) {
                material.setCost(materialDTO.getCost());
            }

            materialRepository.save(material);
        }
    }

    public void deleteMaterial(UUID id) {
        materialRepository.deleteById(id);
    }

    protected boolean checkMaterialAvailability(List<MaterialEstimateDTO> materials) {
        for (MaterialEstimateDTO material : materials) {
            Optional<Material> materialEntity = materialRepository.findById(material.getMaterialId());
            if (materialEntity.isPresent()) {
                if (materialEntity.get().getInventory() < material.getQuantity()) {
                    return false;  // Not enough materials available
                }
            } else {
                return false;  // Material not found
            }
        }
        return true;  // All materials are available
    }

    protected void updateMaterialAvailability(List<MaterialEstimateDTO> materials) {
        for (MaterialEstimateDTO material : materials) {
            Optional<Material> materialEntity = materialRepository.findById(material.getMaterialId());
            if (materialEntity.isPresent()) {
                Material materialRecord = materialEntity.get();
                materialRecord.setInventory(materialRecord.getInventory() - material.getQuantity());
                materialRepository.save(materialRecord);  // Deduct materials
            }
        }
    }
}
