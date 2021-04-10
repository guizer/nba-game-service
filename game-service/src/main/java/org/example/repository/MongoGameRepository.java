package org.example.repository;

import org.example.entity.GameEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoGameRepository extends ReactiveMongoRepository<GameEntity, String> {

}
