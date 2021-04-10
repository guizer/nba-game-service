package org.example.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.example.model.Arena;
import org.example.model.Game;
import org.example.model.Team;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "games")
public class GameEntity {

    @Id
    private String gameId;
    private int seasonYear;
    private String league;
    private LocalDateTime startTimeUTC;
    private LocalDateTime endTimeUTC;
    private int attendance;
    private Arena arena;
    private Team homeTeam;
    private Team visitorTeam;
    private List<String> officials;

    public Game convertToDto() {
        return Game.builder()
            .gameId(this.gameId)
            .seasonYear(this.seasonYear)
            .league(this.league)
            .startTimeUTC(this.startTimeUTC)
            .endTimeUTC(this.endTimeUTC)
            .attendance(this.attendance)
            .arena(this.arena)
            .homeTeam(this.homeTeam)
            .visitorTeam(this.visitorTeam)
            .officials(this.officials)
            .build();
    }

    public static GameEntity fromDto(Game game) {
        return GameEntity.builder()
            .gameId(game.getGameId())
            .seasonYear(game.getSeasonYear())
            .league(game.getLeague())
            .startTimeUTC(game.getStartTimeUTC())
            .endTimeUTC(game.getEndTimeUTC())
            .attendance(game.getAttendance())
            .arena(game.getArena())
            .homeTeam(game.getHomeTeam())
            .visitorTeam(game.getVisitorTeam())
            .officials(game.getOfficials())
            .build();
    }

}
