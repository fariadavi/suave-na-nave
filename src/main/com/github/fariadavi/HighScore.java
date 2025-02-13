package main.com.github.fariadavi;

public class HighScore {

    private final String playerName;
    private final int score;

    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public HighScore(String highScoreString) {
        String[] highScoreSplit = highScoreString.split("\\|");

        this.playerName = highScoreSplit[0];
        this.score = Integer.parseInt(highScoreSplit[1]);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return playerName + "|" + score;
    }
}
