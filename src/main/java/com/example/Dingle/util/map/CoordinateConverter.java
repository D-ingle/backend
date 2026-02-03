package com.example.Dingle.util.map;

import lombok.NoArgsConstructor;
import org.locationtech.proj4j.*;

@NoArgsConstructor
public class CoordinateConverter {

    private static final CRSFactory CRS_FACTORY = new CRSFactory();
    private static final CoordinateTransformFactory COORDINATE_TRANSFORM_FACTORY = new CoordinateTransformFactory();
    private static final CoordinateTransform COORDINATE_TRANSFORM5174 = COORDINATE_TRANSFORM_FACTORY.createTransform(
            CRS_FACTORY.createFromName("EPSG:5174"),
            CRS_FACTORY.createFromName("EPSG:4326")
    );

    private static final CoordinateTransform COORDINATE_TRANSFORM_3857 = COORDINATE_TRANSFORM_FACTORY.createTransform(
            CRS_FACTORY.createFromName("EPSG:3857"),
            CRS_FACTORY.createFromName("EPSG:4326")
    );


    public static double[] convert5174(double x, double y) {
        return transform(COORDINATE_TRANSFORM5174, x, y);
    }

    public static double[] convert3857(double x, double y) {
        return transform(COORDINATE_TRANSFORM_3857, x, y);
    }

    public static double[] transform(CoordinateTransform transform, double x, double y) {
        try {
            ProjCoordinate src = new ProjCoordinate(x, y);
            ProjCoordinate dst = new ProjCoordinate();

            transform.transform(src, dst);

            return new double[]{dst.x, dst.y};
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("좌표 변환 실패", e);
        }
    }
}
