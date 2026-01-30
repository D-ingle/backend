package com.example.Dingle.environment.util;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.WKTReader;

public class GeometryConverter {

    private static final int SRID = 5179;

    public static MultiPolygon toMultiPolygon(String wkt) {
        try {
            WKTReader reader = new WKTReader();
            MultiPolygon geometry = (MultiPolygon) reader.read(wkt);
            geometry.setSRID(SRID);
            return geometry;
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorMessage.GEOM_PARSE_FAILED);
        }
    }
}
