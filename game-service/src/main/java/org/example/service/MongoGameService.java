package org.example.service;

import org.example.entity.GameEntity;
import org.example.error.GameNotFoundException;
import org.example.model.Game;
import org.example.repository.MongoGameRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MongoGameService implements IGameService {

    private final MongoGameRepository mongoGameRepository;

    public MongoGameService(MongoGameRepository mongoGameRepository) {
        this.mongoGameRepository = mongoGameRepository;
    }

    @Override
    public Flux<Game> getGames() {
        return mongoGameRepository.findAll()
            .map(GameEntity::convertToDto);
    }

    @Override
    public Mono<Game> getGameById(String gameId) {
        return mongoGameRepository.findById(gameId)
            .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
            .map(GameEntity::convertToDto);
    }

    @Override
    public Mono<Void> removeGameById(String gameId) {
        return mongoGameRepository.findById(gameId)
            .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
            .flatMap(mongoGameRepository::delete);
    }

    @Override
    public Mono<Game> createGame(Game game) {
        return mongoGameRepository.save(GameEntity.fromDto(game))
            .map(GameEntity::convertToDto);
    }

}

