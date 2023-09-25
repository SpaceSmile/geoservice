package guru.qa.geoservice.conroller;

import guru.qa.geoservice.data.entity.GeoEntity;
import guru.qa.geoservice.domain.GeoJson;
import guru.qa.geoservice.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class GeoController {

    private final GeoService geoService;

    @Autowired
    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/geo")
    public List<GeoJson> getAllGeo(){
        return geoService.getAllGeo();
    }

    @PostMapping("/geo")
    public GeoJson addGeo(@RequestBody GeoEntity geoEntity) {
        GeoEntity savedEntity = geoService.saveGeo(geoEntity);
        return GeoJson.fromEntity(savedEntity);
    }

    @PatchMapping("/geo/{id}")
    public GeoJson updateCountryName(@PathVariable UUID id, @RequestParam String countryName) {
        GeoEntity entity = geoService.getGeoById(id);
        entity.setCountryName(countryName);
        GeoEntity updatedEntity = geoService.saveGeo(entity);
        return GeoJson.fromEntity(updatedEntity);
    }
}

