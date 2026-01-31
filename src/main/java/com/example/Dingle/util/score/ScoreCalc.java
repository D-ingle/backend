package com.example.Dingle.util.score;

import com.example.Dingle.property.dto.PropertyScoreDTO;
import com.example.Dingle.util.dto.PreferredConditionDTO;

import java.util.List;

public class ScoreCalc {

    private ScoreCalc() {}

    public static double calculate (PropertyScoreDTO propertyScoreDTO, List<PreferredConditionDTO> userPrefer) {
        if( userPrefer == null || userPrefer.isEmpty() ) return 0.0;

        int size = userPrefer.size();
        double score = 0.0;

        for ( int i = 0; i < size; i++ ) {
            PreferredConditionDTO pref = userPrefer.get(i);
            int rawScore = propertyScoreDTO.getScoreByConditionId(pref.getConditionId());
            double weight = getWeight(size, i + 1);

            score += weight * rawScore;
        }

        return score;
    }

    private static double getWeight(int size, int priority) {
        if(priority < 1 || priority > size) {
            throw new IllegalArgumentException();
        }

        if( size == 3 ) {
            return switch (priority) {
                case 1 -> 0.5;
                case 2 -> 0.3;
                case 3 -> 0.2;
                default -> 0.0;
            };
        } else if ( size == 2 ) {
            return switch (priority) {
                case 1 -> 0.6;
                case 2 -> 0.4;
                default -> 0.0;
            };
        } else if ( size == 1 ) {
            return 1.0;
        }
        return 0;
    }
}
