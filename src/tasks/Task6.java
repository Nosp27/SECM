package tasks;

import clusterisation.DisplayFactory;
import clusterisation.K_Averages;
import clusterisation.PointsReader;

import java.io.IOException;

public class Task6 {
    public static void main(String[] args) {
        try {
            PointsReader reader = new PointsReader();
            K_Averages clusterizer = new K_Averages(reader.readPoints("secm/hw6/coins_data.txt"));
            for (int k = 2; k <= 4; k++) {
                DisplayFactory.createNewFrame(clusterizer.clusterize(k), reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
