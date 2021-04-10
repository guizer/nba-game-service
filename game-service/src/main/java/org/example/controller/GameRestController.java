package org.example.controller;

import org.example.error.ApiErrorResponse;
import org.example.error.ErrorMessages;
import org.example.error.Errors;
import org.example.error.GameNotFoundException;
import org.example.model.Game;
import org.example.service.IGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(GameRestController.REQUEST_MAPPING)
public class GameRestController {

    public static final String REQUEST_MAPPING = "/v1/game-management";
    public static final String GAMES_ENDPOINT = "/games";
    public static final String GAME_ENDPOINT = "/games/{gameId}";

    private final IGameService gameService;

    public GameRestController(IGameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(GAMES_ENDPOINT)
    public Flux<Game> getGames() {
        return gameService.getGames();
    }

    @PostMapping(GAMES_ENDPOINT)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Game> createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    @GetMapping(GAME_ENDPOINT)
    public Mono<Game> getGameById(@PathVariable("gameId") String gameId) {
        return gameService.getGameById(gameId);
    }

    @DeleteMapping(GAME_ENDPOINT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removeGameById(@PathVariable("gameId") String gameId) {
        return gameService.removeGameById(gameId);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(GameNotFoundException exception) {
        ApiErrorResponse response = new ApiErrorResponse(Errors.GAME_NOT_FOUND,
            ErrorMessages.GAME_NOT_FOUND.apply(exception.getGameId()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
