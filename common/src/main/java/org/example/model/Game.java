package org.example.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private String gameId;
    private int seasonYear;
    private String league;
    private LocalDateTime startTimeUTC;
    private LocalDateTime endTimeUTC;
    private Team homeTeam;
    private Team visitorTeam;
    private int attendance;
    private Arena arena;
    private List<String> officials;
    private GameStatistics statistics;

}
