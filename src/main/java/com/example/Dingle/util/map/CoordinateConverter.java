package com.example.Dingle.util.map;

import lombok.NoArgsConstructor;
import org.locationtech.proj4j.*;

@NoArgsConstructor
public class CoordinateConverter {

    private static final CRSFactory CRS_FACTORY = new CRSFactory();
    private static final CoordinateTransformFactory COORDINATE_TRANSFORM_FACTORY = new CoordinateTransformFactory();
    private static final CoordinateTransform COORDINATE_TRANSFORM = COORDINATE_TRANSFORM_FACTORY.createTransform(
            CRS_FACTORY.createFromName("EPSG:5174"),
            CRS_FACTORY.createFromName("EPSG:4326")
    );


    public static double[] convert(double x, double y) {

        try {
            ProjCoordinate src = new ProjCoordinate(x, y);
            ProjCoordinate dst = new ProjCoordinate();

            COORDINATE_TRANSFORM.transform(src, dst);

            return new double[]{dst.x, dst.y};
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("좌표 변환 실패", e);
        }
    }
}
