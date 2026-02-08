package com.example.Dingle.property.util;

import org.springframework.stereotype.Component;

@Component
public class SafetyScoreCalculator {

    public int crimeScore(int insideMaxRisk, boolean hasWithin300m) {
        if (insideMaxRisk > 0) {
            if (insideMaxRisk >= 9) return 30;
            if (insideMaxRisk >= 6) return 50;
            return 70;
        }

        if (hasWithin300m) return 85;
        return 100;
    }

    public int policeScore(int distanceMeter) {
        if (distanceMeter <= 300) return 100;
        if (distanceMeter <= 600) return 90;
        if (distanceMeter <= 1000) return 80;
        return 70;
    }

    public int infraScore(int cctv50m, int light50m) {
        if (cctv50m >= 5 && light50m >= 10) return 100;
        if (cctv50m >= 3 && light50m >= 5) return 85;
        if (cctv50m >= 1 && light50m >= 3) return 70;
        return 60;
    }

    public int safetyScore(int crimeScore, int policeScore, int infraScore) {
        double score = crimeScore * 0.50 + policeScore * 0.20 + infraScore * 0.30;
        return (int) Math.round(score);
    }

    public int pathCrimeScore(int crimeCount) {
        if (crimeCount == 0) return 100;
        if (crimeCount >= 1 && crimeCount < 3) return 80;
        if (crimeCount < 5) return 60;
        return 40;
    }

    public int pathInfraScore(int cctv, int light) {
        if (cctv >= 8 && light >= 15) return 100;
        if (cctv >= 5 && light >= 10) return 85;
        if (cctv >= 2 && light >= 5) return 70;
        return 60;
    }

    public int pathSafetyScore(int crimeScore, int infraScore) {
        return (int) Math.round(crimeScore * 0.5 + infraScore * 0.5);
    }
}
