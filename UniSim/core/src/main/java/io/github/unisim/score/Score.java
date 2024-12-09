package io.github.unisim.score;

/**
 * The score of the game, 0-100.
 */
public class Score {
    private float score;
    private float initialScore;
    private boolean hasFinished;

    /**
     * Creates a score object, with score value between 0 and 100.
     *
     * @param initialScore The initial number to set the score value to.
     */
    public Score(int initialScore) {
        if (initialScore > 100) { initialScore = 100; }
        if (initialScore < 0) {initialScore = 0; }
        this.initialScore = initialScore;
        score = initialScore;
        hasFinished = false;
    }

    public float getScore() {
        return score;
    }

    /**
     * @return The score value as a string.
     */
    public String getScoreString() {
        return String.valueOf(Math.round(score)) + "%";
    }

    /**
     * Changes the score value by the amount specified, and keeps it within the range 0-100.
     *
     * @param inc The amount to change the score value by.
     */
    public void incrementScore(float inc) {
        float newScore = score;
        newScore += inc;
        if (newScore > 100f) { newScore = 100f; }
        if (newScore < 0f) { newScore = 0f; }
        score = newScore;
    }

    public void reset() {
        score = initialScore;
        hasFinished = false;
    }
}
