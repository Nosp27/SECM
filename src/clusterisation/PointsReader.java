package clusterisation;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PointsReader {
    Float[] boundsX = new Float[]{-Float.MAX_VALUE, Float.MAX_VALUE};
    Float[] boundsY = new Float[]{-Float.MAX_VALUE, Float.MAX_VALUE};
    public ArrayList<Float[]> readPoints(String s) throws IOException {
        ArrayList<Float[]> floats = new ArrayList<>();
        File f = new File(s);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            Scanner sc = new Scanner(line);
            Float[] nextPoint = new Float[] {sc.nextFloat(), sc.nextFloat()};
            boundsX[0] = Math.min(nextPoint[0], boundsX[0]);
            boundsX[1] = Math.max(nextPoint[0], boundsX[1]);

            boundsY[0] = Math.min(nextPoint[1], boundsX[0]);
            boundsY[1] = Math.max(nextPoint[1], boundsY[1]);

            floats.add(nextPoint);
        }
        return floats;
    }
}
