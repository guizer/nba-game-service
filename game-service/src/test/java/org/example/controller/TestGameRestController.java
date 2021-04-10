package org.example.controller;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;
import java.util.List;
import org.example.error.ApiErrorResponse;
import org.example.error.ErrorMessages;
import org.example.error.Errors;
import org.example.error.GameNotFoundException;
import org.example.model.Game;
import org.example.model.Team;
import org.example.repository.MongoGameRepository;
import org.example.service.IGameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(GameRestController.class)
public class TestGameRestController {

    private static final String GAME_ID = "1";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MongoGameRepository mongoGameRepository;

    @MockBean
    private IGameService gameService;

    @Test
    public void testGetGamesReturnAllGames() {
        Iterable<Game> expectedGames = List.of(Game.builder()
            .gameId(GAME_ID)
            .seasonYear(2020)
            .league("NBA")
            .startTimeUTC(LocalDateTime.now())
            .endTimeUTC(LocalDateTime.now())
            .homeTeam(new Team(1, "Los Angeles Lakers"))
            .visitorTeam(new Team(2, "Brooklyn Nets"))
            .build());
        Mockito.when(gameService.getGames()).thenReturn(Flux.fromIterable(expectedGames));

        String uri = GameRestController.REQUEST_MAPPING + GameRestController.GAMES_ENDPOINT;
        webTestClient.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Game.class)
            .value(games -> games, equalTo(expectedGames));
        Mockito.verify(gameService, Mockito.times(1)).getGames();
    }

    @Test
    public void testGetGameByIdReturnGameWhenGameExist() {
        Game expectedGame = Game.builder()
            .gameId(GAME_ID)
            .seasonYear(2020)
            .league("NBA")
            .startTimeUTC(LocalDateTime.now())
            .endTimeUTC(LocalDateTime.now())
            .homeTeam(new Team(1, "Los Angeles Lakers"))
            .visitorTeam(new Team(2, "Brooklyn Nets"))
            .build();

        Mockito.when(gameService.getGameById(GAME_ID))
            .thenReturn(Mono.just(expectedGame));

        String uri = GameRestController.REQUEST_MAPPING + GameRestController.GAME_ENDPOINT;
        webTestClient.get()
            .uri(uri, GAME_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Game.class)
            .isEqualTo(expectedGame);
        Mockito.verify(gameService, Mockito.times(1)).getGameById(GAME_ID);
    }

    @Test
    public void testGetGameByIdReturnNotFoundWhenGameNotExist() {
        Mockito.when(gameService.getGameById(GAME_ID))
            .thenReturn(Mono.error(new GameNotFoundException(GAME_ID)));

        String uri = GameRestController.REQUEST_MAPPING + GameRestController.GAME_ENDPOINT;
        webTestClient.get()
            .uri(uri, GAME_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(ApiErrorResponse.class)
            .isEqualTo(new ApiErrorResponse(Errors.GAME_NOT_FOUND,
                ErrorMessages.GAME_NOT_FOUND.apply(GAME_ID)));
        Mockito.verify(gameService, Mockito.times(1)).getGameById(GAME_ID);
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
        Mockito.when(gameService.createGame(gameToCreate)).thenReturn(Mono.just(gameToCreate));

        String uri = GameRestController.REQUEST_MAPPING + GameRestController.GAMES_ENDPOINT;
        webTestClient.post()
            .uri(uri)
            .bodyValue(gameToCreate)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Game.class)
            .isEqualTo(gameToCreate);
        Mockito.verify(gameService, Mockito.times(1)).createGame(gameToCreate);
    }

    @Test
    public void testDeleteGameWorksWhenGameExist() {
        Mockito.when(gameService.removeGameById(GAME_ID)).thenReturn(Mono.empty());

        String uri = GameRestController.REQUEST_MAPPING + GameRestController.GAME_ENDPOINT;
        webTestClient.delete()
            .uri(uri, GAME_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNoContent();
        Mockito.verify(gameService, Mockito.times(1)).removeGameById(GAME_ID);
    }

    @Test
    public void testDeleteGameReturnNotFoundWhenGameNotExist() {
        Mockito.when(gameService.removeGameById(GAME_ID))
            .thenReturn(Mono.error(new GameNotFoundException(GAME_ID)));

        String uri = GameRestController.REQUEST_MAPPING + GameRestController.GAME_ENDPOINT;
        webTestClient.delete()
            .uri(uri, GAME_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(ApiErrorResponse.class)
            .isEqualTo(new ApiErrorResponse(Errors.GAME_NOT_FOUND,
                ErrorMessages.GAME_NOT_FOUND.apply(GAME_ID)));
        Mockito.verify(gameService, Mockito.times(1)).removeGameById(GAME_ID);
    }

}
