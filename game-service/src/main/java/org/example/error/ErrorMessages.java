package org.example.error;

import java.util.function.UnaryOperator;

public class ErrorMessages {

    public static final UnaryOperator<String> GAME_NOT_FOUND = gameId -> "No game found with id " + gameId;

}
