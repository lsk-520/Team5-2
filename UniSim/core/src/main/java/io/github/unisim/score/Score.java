package io.github.unisim.score;

public class Score {
    private int score;
    private int initialScore;
    private boolean hasFinished;

    public Score(int initialScore) {
        this.initialScore = initialScore;
        score = initialScore;
        hasFinished = false;
    }

    public void reset() {
        score = initialScore;
        hasFinished = false;
    }

    public int getScore() {
        return score;
    }

    public String getScoreString() {
        return String.valueOf(score) + "%";
    }

    public void calculateScore(ScoreModifierType modifier) {
        // building should be close together
        // check that no building is far away

        // event reactions

        // achievements modifier


    }


}
