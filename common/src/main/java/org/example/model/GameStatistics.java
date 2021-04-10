package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStatistics {

    private TeamStatistics homeTeamStatistics;
    private TeamStatistics visitorTeamStatistics;
    private PlayerStatistics playerStatistics;

}
