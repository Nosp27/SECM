package tasks;

import correlation.*;

import java.io.File;

public class Task3 {
    private static final int size = 7;
    private static int n = 100;

    public static void main(String[] args) {
        YoutubeCSVReader reader = new YoutubeCSVReader(new File("secm/yt/youtube_7.csv"));

        DataCSVWriter writer = new DataCSVWriter(new File("answerP.csv"));
        writer.write(countCorrelation(new CorrelationP(), reader));

        writer = new DataCSVWriter(new File("answerS.csv"));
        writer.write(countCorrelation(new CorrelationS(), reader));
    }

    private static double[][] countCorrelation(CorrelationCounter cc, YoutubeCSVReader reader) {
        double[][] data = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                data[i][j] = cc.countCorrelation(reader.get(i), reader.get(j));
            }
        }
        return data;
    }

    public static double getStatistic(double cc) {
        return (cc * Math.sqrt(n - 2)) / (Math.sqrt(1 - cc * cc));
    }
}
