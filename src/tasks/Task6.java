package tasks;

import clusterisation.DisplayFactory;
import clusterisation.K_Averages;
import clusterisation.K_Medians;
import clusterisation.PointsReader;

import java.io.IOException;
import java.util.ArrayList;

public class Task6 {
    public static void main(String[] args) {
        try {
            PointsReader reader = new PointsReader();
            ArrayList<Float[]> points = reader.readPoints("secm/hw6/data_7");
            K_Averages clusterizer;
            clusterizer = new K_Averages(points);
            makeClusterization(clusterizer);
            clusterizer = new K_Medians(points);
            makeClusterization(clusterizer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeClusterization(K_Averages c){
        for (int k = 2; k <= 4; k++) {
            DisplayFactory.createNewFrame(c.clusterize(k), k + c.toString());
        }
    }
}
