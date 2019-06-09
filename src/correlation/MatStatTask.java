package correlation;

import java.util.concurrent.ThreadLocalRandom;

public class MatStatTask {
    protected static double x() {
        return ThreadLocalRandom.current().nextDouble();
    }

    protected static double avg(double[] X) {
        double sum = 0;
        for (int i = 0; i < X.length; ++i) {
            sum += X[i];
        }
        return sum / X.length;
    }

    protected static double disp(double[] X, double avg) {
        double d = 0;
        for (int i = 0; i < X.length; ++i) {
            d += Math.pow(X[i] - avg, 2);
        }
        return Math.sqrt(d / (X.length - 1));
    }

    public static void drawGist(double X[], int cols, int height) {
        double max = -100000;
        double min = 1000000;

        for (int i = 0; i < X.length; ++i) {
            if (X[i] > max)
                max = X[i];

            if (X[i] < min)
                min = X[i];
        }

        System.out.println("min " + min);
        System.out.println("max " + max);

        double span = (max - min) / cols;

        double[] columns = new double[cols + 1];

        double colMax = 0;
        for (int i = 0; i < X.length; ++i) {
            int index = (int) ((X[i] - min) / span);
            columns[index]++;
            if(colMax < columns[index])
                colMax = columns[index];
        }
        double c = height / colMax;

        for (int i = height; i > 0; --i, System.out.println())
            for (int j = 0; j < cols; ++j) {
                if (columns[j] * c > i)
                    System.out.print("| ");
                else System.out.print("  ");
            }
    }
}
