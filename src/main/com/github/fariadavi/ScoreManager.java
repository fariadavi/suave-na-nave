package main.com.github.fariadavi;

import main.com.github.fariadavi.utils.FileHelper;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreManager {

    public static final String SCOREBOARD_FILEPATH = "highscores.dat";
    public static final int NUM_HIGHSCORES = 10;

    public HighScore[] readScoreBoardFile() {
        HighScore[] highScores = new HighScore[NUM_HIGHSCORES];
        String[] scoresFile = FileHelper.readFile(SCOREBOARD_FILEPATH);
        for (int i = 0; i < scoresFile.length; i++)
            highScores[i] = scoresFile[i] != null
                    ? new HighScore(scoresFile[i])
                    : null;
        return highScores;
    }

    public int getLowestScoreFromFile() {
        HighScore[] highScores = readScoreBoardFile();
        if (highScores == null ||
                highScores.length < NUM_HIGHSCORES ||
                Arrays.stream(highScores).anyMatch(Objects::isNull))
            return 0;

        return Arrays.stream(highScores)
                .mapToInt(HighScore::getScore)
                .min().orElse(0);
    }

    public void addScoreToFile(HighScore highScore) {
        HighScore[] newScore = {highScore};
        HighScore[] highScores = readScoreBoardFile();

        String newScoreList = Stream.concat(
                        Arrays.stream(highScores),
                        Arrays.stream(newScore))
                .filter(Objects::nonNull)
                .sorted((h1, h2) ->
                        Integer.compare(h2.getScore(), h1.getScore()))
                .limit(NUM_HIGHSCORES)
                .map(HighScore::toString)
                .collect(Collectors.joining("\n"));

        FileHelper.writeFile(SCOREBOARD_FILEPATH, newScoreList);
    }
}
