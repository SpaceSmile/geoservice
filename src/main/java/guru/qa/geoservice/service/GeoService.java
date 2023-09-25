package guru.qa.geoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import guru.qa.geoservice.data.GeoRepository;
import guru.qa.geoservice.data.entity.GeoEntity;
import guru.qa.geoservice.domain.GeoJson;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GeoService {

    private final GeoRepository geoRepository;

    @Autowired
    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    public List<GeoJson> getAllGeo() {
        return IteratorUtils.toList(geoRepository.findAll().iterator())
                .stream()
                .map(GeoJson::fromEntity)
                .toList();
    }

    public GeoEntity saveGeo(GeoEntity entity) {
        return geoRepository.save(entity);
    }

    public GeoEntity getGeoById(UUID id) {
        return geoRepository.findById(id).orElse(null);
    }

    public List<GeoEntity> readGeoJsonFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<GeoEntity> geoEntities = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            ArrayNode featuresNode = (ArrayNode) rootNode.get("features");

            for (JsonNode featureNode : featuresNode) {
                String countryName = featureNode.get("N").asText();
                String countryCode = featureNode.get("I").asText();

                ArrayNode coordinatesNode = (ArrayNode) featureNode.get("C");
                for (JsonNode polygonNode : coordinatesNode) {
                    GeoEntity entity = new GeoEntity();
                    entity.setCountryName(countryName);
                    entity.setCountryCode(countryCode);

                    // Преобразовать в JSON и обратно в строку
                    ObjectMapper jsonMapper = new ObjectMapper();
                    JsonNode jsonNode = jsonMapper.readTree(polygonNode.toString());
                    entity.setCountryCoordinate(jsonNode.toString());

                    geoEntities.add(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return geoEntities;
    }

    public Iterable<GeoEntity> saveAllGeo(List<GeoEntity> geoEntities) {
        return geoRepository.saveAll(geoEntities);
    }
}
