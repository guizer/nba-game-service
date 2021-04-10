package org.example.service;

import java.time.LocalDateTime;
import java.util.Objects;
import org.example.entity.GameEntity;
import org.example.error.GameNotFoundException;
import org.example.model.Game;
import org.example.model.Team;
import org.example.repository.MongoGameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class TestMongoGameService {

    private static final String GAME_ID = "1";

    @Mock
    private MongoGameRepository mongoGameRepository;

    @InjectMocks
    private MongoGameService mongoGameService;

    @Test
    public void testGetGamesReturnAllGames() {
        GameEntity entity = GameEntity.builder()
            .gameId(GAME_ID)
            .seasonYear(2020)
            .league("NBA")
            .startTimeUTC(LocalDateTime.now())
            .endTimeUTC(LocalDateTime.now())
            .homeTeam(new Team(1, "Los Angeles Lakers"))
            .visitorTeam(new Team(2, "Brooklyn Nets"))
            .build();
        Flux<GameEntity> expectedGames = Flux.just(entity);

        Mockito.when(mongoGameRepository.findAll()).thenReturn(expectedGames);

        StepVerifier.create(mongoGameService.getGames())
            .expectComplete();
    }

    @Test
    public void testGetGameByIdReturnGameWhenGameExist() {
        Mono<GameEntity> expectedGame = Mono.just(GameEntity.builder()
            .gameId(GAME_ID)
            .seasonYear(2020)
            .league("NBA")
            .startTimeUTC(LocalDateTime.now())
            .endTimeUTC(LocalDateTime.now())
            .homeTeam(new Team(1, "Los Angeles Lakers"))
            .visitorTeam(new Team(2, "Brooklyn Nets"))
            .build());
        Mockito.when(mongoGameRepository.findById(GAME_ID)).thenReturn(expectedGame);

        StepVerifier.create(mongoGameService.getGameById(GAME_ID))
            .expectNext(Objects.requireNonNull(expectedGame.block()).convertToDto())
            .expectComplete();
    }

    @Test
    public void testGetGameByIdReturnNotFoundWhenGameNotExist() {
        Mockito.when(mongoGameRepository.findById(GAME_ID)).thenReturn(Mono.empty());

        StepVerifier.create(mongoGameService.getGameById(GAME_ID))
            .expectErrorMatches(error -> error.equals(new GameNotFoundException(GAME_ID)));
    }

    @Test
    public void testCreateGame() {
        Game gameToCreate = Game.builder()
            .gameId(GAME_ID)
            .seasonYear(2020)
            .league("NBA")
            .startTimeUTC(LocalDateTime.now())
            .endTimeUTC(LocalDateTime.now())
            .homeTeam(new Team(1, "Los Angeles Lakers"))
            .visitorTeam(new Team(2, "Brooklyn Nets"))
            .build();
        GameEntity gameEntityToCreate = GameEntity.fromDto(gameToCreate);
        Mockito.when(mongoGameRepository.save(gameEntityToCreate))
            .thenReturn(Mono.just(gameEntityToCreate));

        StepVerifier.create(mongoGameService.createGame(gameToCreate))
            .expectNext(gameToCreate)
            .expectComplete();

        Mockito.verify(mongoGameRepository, Mockito.times(1)).save(gameEntityToCreate);
    }

    @Test
    public void testDeleteGameWorksWhenGameExist() {
        GameEntity gameEntityToDelete = GameEntity.builder()
            .gameId(GAME_ID)
            .seasonYear(2020)
            .league("NBA")
            .startTimeUTC(LocalDateTime.now())
            .endTimeUTC(LocalDateTime.now())
            .homeTeam(new Team(1, "Los Angeles Lakers"))
            .visitorTeam(new Team(2, "Brooklyn Nets"))
            .build();
        Mockito.when(mongoGameRepository.findById(GAME_ID)).thenReturn(Mono.just(gameEntityToDelete));
        StepVerifier.create(mongoGameService.removeGameById(GAME_ID))
            .expectComplete();
    }

    @Test
    public void testDeleteGameReturnNotFoundWhenGameNotExist() {
        Mockito.when(mongoGameRepository.findById(GAME_ID)).thenReturn(Mono.empty());

        StepVerifier.create(mongoGameService.removeGameById(GAME_ID))
            .expectErrorMatches(error -> error.equals(new GameNotFoundException(GAME_ID)));
    }

}
