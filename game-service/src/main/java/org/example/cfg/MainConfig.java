package org.example.cfg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.repository.MongoGameRepository;
import org.example.service.MongoGameService;
import org.example.service.IGameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    public IGameService gameService(MongoGameRepository mongoGameRepository) {
        return new MongoGameService(mongoGameRepository);
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

}
