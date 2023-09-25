package guru.qa.geoservice;

import guru.qa.geoservice.data.entity.GeoEntity;
import guru.qa.geoservice.service.GeoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class GeoserviceApplication {

    @Autowired
    private GeoService geoService;

    public static void main(String[] args) {
        SpringApplication.run(GeoserviceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<GeoEntity> geoEntities = geoService.readGeoJsonFile("src/main/resources/geo.json");
        geoService.saveAllGeo(geoEntities);
    }
}
