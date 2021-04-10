package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatistics {

    private int playerId;
    private int jersey;
    private int teamId;
    private int points;
    private int fieldGoalMade;
    private int fieldGoalAttempts;
    private int freeThrowMade;
    private int freeThrowAttempts;
    private int threePointsMade;
    private int threePointsAttempts;
    private int offensiveRebounds;
    private int defensiveRebounds;
    private int assists;
    private int blocks;
    private int turnovers;
    private int personalFouls;

}
