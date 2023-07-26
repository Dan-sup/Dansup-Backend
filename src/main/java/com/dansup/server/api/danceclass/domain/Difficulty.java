package com.dansup.server.api.danceclass.domain;

public enum Difficulty {

    BA("베이직"), BE("초급"), BM("초중급"), M("중급"), A("고급");

    private String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
