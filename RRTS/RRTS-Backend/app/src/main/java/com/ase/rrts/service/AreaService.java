package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.model.Area;
import com.ase.rrts.repository.AreaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    public Optional<Area> getAreaById(Integer id) {
        return areaRepository.findById(id);
    }

    public Area createArea(Area area) {
        return areaRepository.save(area);
    }

    public Area updateArea(Integer id, Area area) {
        if (areaRepository.existsById(id)) {
            area.setId(id);
            return areaRepository.save(area);
        }
        return null;
    }

    public void deleteArea(Integer id) {
        areaRepository.deleteById(id);
    }
}
