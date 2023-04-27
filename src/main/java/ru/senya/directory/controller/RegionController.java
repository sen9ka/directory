package ru.senya.directory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.senya.directory.entity.model.Region;
import ru.senya.directory.service.RegionService;
import java.util.List;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
@Tag(name = "Справочник регионов")
public class RegionController {

    private final RegionService regionService;
    Logger logger = LoggerFactory.getLogger(RegionController.class);

    @GetMapping()
    @Cacheable(value = "allRegions")
    @Operation(summary = "Получить список всех регионов из БД")
    public ResponseEntity<Object> getAllRegions() {
        logger.info("Getting all regions");
        List<Region> regionList = regionService.getAllRegions();
        return new ResponseEntity<>(regionList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "regionById", key = "#id")
    @Operation(summary = "Получить инофрмацию по региону по ID")
    public ResponseEntity<Object> getRegionById(@PathVariable Integer id) {
        logger.info("Getting region by ID: {}", id);
        Region region = regionService.findRegionById(id);
        logger.info("Found region: {}", region);
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "Добавить регион в БД")
    public ResponseEntity<Object> addRegion(@RequestBody Region region) {
        logger.info("Saving new region: {}", region);
        Region savedRegion = regionService.saveRegion(region);
        logger.info("Saved region: {}", region);
        return new ResponseEntity<>(savedRegion, HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Обновить информацию о регионе")
    public ResponseEntity<Object> updateRegion(@RequestBody Region updatedRegion) {
        logger.info("Updating region: {}", updatedRegion);
        Region region = regionService.updateRegion(updatedRegion);
        logger.info("Updated region: {}", region);
        return new ResponseEntity<>(region, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Удалить регион")
    public ResponseEntity<Object> deleteRegion(@PathVariable Integer id) {
        logger.info("Deleting region by ID: {}", id);
        List<Region> regionList = regionService.deleteRegion(id);
        return new ResponseEntity<>(regionList, HttpStatus.OK);
    }


}
