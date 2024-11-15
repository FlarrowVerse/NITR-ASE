package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.exceptions.ResourceNotFoundException;
import com.ase.rrts.model.Resource;
import com.ase.rrts.model.ResourceDTO;
import com.ase.rrts.model.ResourceEstimateDTO;
import com.ase.rrts.repository.ResourceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Optional<Resource> getResourceById(UUID id) {
        return resourceRepository.findById(id);
    }

    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource updateResource(UUID id, Resource resource) {
        if (resourceRepository.existsById(id)) {
            resource.setId(id);
            return resourceRepository.save(resource);
        }
        return null;
    }

    public void updateResource(UUID id, ResourceDTO resourceDTO) {
        if (resourceRepository.existsById(id)) {
            Resource resource = resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such resource"));
            
            if (resourceDTO.getAvailable() != null) {
                resource.setAvailable(resourceDTO.getAvailable());
            }

            if (resourceDTO.getCount() != null) {
                resource.setCount(resourceDTO.getCount());
            }

            if (resourceDTO.getCost() != null) {
                resource.setCost(resourceDTO.getCost());
            }

            resourceRepository.save(resource);
        }
    }

    public void deleteResource(UUID id) {
        resourceRepository.deleteById(id);
    }

    protected boolean checkResourceAvailability(List<ResourceEstimateDTO> resources) {
        for (ResourceEstimateDTO resource : resources) {
            Optional<Resource> resourceEntity = resourceRepository.findById(resource.getResourceId());
            if (resourceEntity.isPresent()) {
                
                if (resourceEntity.get().getAvailable() < resource.getQuantity()) {
                    return false;  // Not enough resources available
                }
            } else {
                return false;  // Resource not found
            }
        }
        return true;  // All resources are available
    }

    protected void updateResourceAvailability(List<ResourceEstimateDTO> resources) {
        for (ResourceEstimateDTO resource : resources) {
            Optional<Resource> resourceEntity = resourceRepository.findById(resource.getResourceId());
            if (resourceEntity.isPresent()) {
                Resource resourceRecord = resourceEntity.get();
                resourceRecord.setAvailable(resourceRecord.getAvailable() - resource.getQuantity()); // set the available resource
                resourceRepository.save(resourceRecord);  // Deduct resources
            }
        }
    }
}
