package guru.qa.geoservice.data;

import guru.qa.geoservice.data.entity.GeoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GeoRepository extends CrudRepository<GeoEntity, UUID> {
}
