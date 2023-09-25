package guru.qa.geoservice.domain;

import guru.qa.geoservice.data.entity.GeoEntity;

public record GeoJson (String countryName, String countryCode, String countryCoordinate){

    public static GeoJson fromEntity(GeoEntity entity) {
        return new GeoJson(entity.getCountryName(), entity.getCountryCode(), entity.getCountryCoordinate());
    }
}
