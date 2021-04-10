package org.example.service;

import org.example.error.GameNotFoundException;
import org.example.model.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service in charge of CRUD operations on {@link Game}.
 */
public interface IGameService {

    /**
     * Retrieve all games.
     *
     * @return {@link Flux} emitting all games.
     */
    Flux<Game> getGames();

    /**
     * Retrieves a game by its id.
     *
     * @param gameId the game id.
     * @return {@link Mono} emitting the game with the given {@literal id}.
     * @throws GameNotFoundException in case no game the given {@literal id} exists.
     */
    Mono<Game> getGameById(String gameId) throws GameNotFoundException;

    /**
     * Removes the game with the given id.
     *
     * @param gameId the game id.
     * @return {@link Mono} signaling when the operation has completed.
     * @throws GameNotFoundException in case no game the given {@literal id} exists.
     */
    Mono<Void> removeGameById(String gameId) throws GameNotFoundException;

    /**
     * Creates or overrides a given game.
     *
     * @param game must not be {@literal null}.
     * @return {@link Mono} emitting the created game.
     */
    Mono<Game> createGame(Game game);

}
