package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.Resource;
import com.ase.rrts.model.ResourceDTO;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.service.ResourceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
@Tag(name = "Resources", description = "Endpoints related to resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @GetMapping
    @Operation(summary = "Get Resources", description = "Retrieves a list of resources")
    public List<Resource> getAllResources() {
        return resourceService.getAllResources();
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @GetMapping("/{id}")
    @Operation(summary = "Get Resource", description = "Fetches a single resource based on UUID")
    public ResponseEntity<Resource> getResourceById(@PathVariable UUID id) {
        return resourceService.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @PostMapping
    @Operation(summary = "Create Resource", description = "Creates a resource and adds to DB")
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        return ResponseEntity.ok(resourceService.createResource(resource));
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Resource", description = "Deletes a resource based on UUID")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('CITY-ADM')")
    @PatchMapping("/update/{id}")
    @Operation(summary = "Update Resource", description = "Updates a resource based on UUID")
    public ResponseEntity<ResponseMessage> updateResource(@PathVariable UUID id, @RequestBody ResourceDTO resourceDTO) {
        try {
            resourceService.updateResource(id, resourceDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Resource updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failed", "Resource was not updated!"));
        }
    }
}
