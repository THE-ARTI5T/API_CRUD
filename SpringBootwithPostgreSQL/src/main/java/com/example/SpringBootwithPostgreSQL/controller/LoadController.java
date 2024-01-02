package com.example.SpringBootwithPostgreSQL.controller;

import com.example.SpringBootwithPostgreSQL.entities.Load;
import com.example.SpringBootwithPostgreSQL.repo.LoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/load")
public class LoadController {

    @Autowired
    private LoadRepository loadRepository;

    @PostMapping
    public ResponseEntity<String> addLoad(@RequestBody Load load) {
        loadRepository.save(load);
        return ResponseEntity.ok("Load details added successfully");
    }

    @GetMapping
    public List<Load> getLoadsByShipperId(@RequestParam String shipperId) {
        return loadRepository.findByShipperId(shipperId);
    }

    @GetMapping("/{loadId}")
    public ResponseEntity<Load> getLoadById(@PathVariable Long loadId) {
        Optional<Load> load = loadRepository.findById(loadId);
        return load.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{loadId}")
    public ResponseEntity<Load> updateLoad(@PathVariable Long loadId, @RequestBody Load updatedLoad) {
        return loadRepository.findById(loadId)
                .map(existingLoad -> {
                    existingLoad.setLoadingPoint(updatedLoad.getLoadingPoint());
                    existingLoad.setUnloadingPoint(updatedLoad.getUnloadingPoint());
                    existingLoad.setProductType(updatedLoad.getProductType());
                    existingLoad.setTruckType(updatedLoad.getTruckType());
                    existingLoad.setNoOfTrucks(updatedLoad.getNoOfTrucks());
                    existingLoad.setWeight(updatedLoad.getWeight());
                    existingLoad.setComment(updatedLoad.getComment());
                    existingLoad.setDate(updatedLoad.getDate());

                    loadRepository.save(existingLoad);
                    return ResponseEntity.ok(existingLoad);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{loadId}")
    public ResponseEntity<Void> deleteLoad(@PathVariable Long loadId) {
        if (!loadRepository.existsById(loadId)) {
            return ResponseEntity.notFound().build();
        }

        loadRepository.deleteById(loadId);
        return ResponseEntity.noContent().build();
    }
}
