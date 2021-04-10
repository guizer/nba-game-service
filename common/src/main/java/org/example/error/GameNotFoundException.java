package org.example.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4165218059677506738L;

    private String gameId;

    public GameNotFoundException(String gameId) {
        this.gameId = gameId;
    }

}
