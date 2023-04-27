package ru.senya.directory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.senya.directory.controller.exceptionHandler.exception.RegionNotFoundException;
import ru.senya.directory.entity.model.Region;
import ru.senya.directory.mapper.RegionMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionMapper regionMapper;

    public List<Region> getAllRegions(){
        return regionMapper.getAllRegions();
    }

    public Region findRegionById(Integer id) {
        Optional<Region> optionalRegion = regionMapper.findRegionById(id);

        return optionalRegion.orElseThrow(() -> new RegionNotFoundException(Constant.RegionNotFoundExceptionMessage + id));
    }

    public Region saveRegion(Region region) {
        regionMapper.saveRegion(region);
        return region;
    }

    public Region updateRegion(Region updatedRegion) {
        Region regionToBeUpdated = findRegionById(updatedRegion.getId()).toBuilder()
                .name(updatedRegion.getName())
                .shortName(updatedRegion.getShortName())
                .build();
        regionMapper.updateRegion(regionToBeUpdated);
        return regionToBeUpdated;
    }

    public List<Region> deleteRegion(Integer id) {
        Region regionToDelete = findRegionById(id);
        regionMapper.deleteRegion(regionToDelete.getId());
        return regionMapper.getAllRegions();
    }

}
