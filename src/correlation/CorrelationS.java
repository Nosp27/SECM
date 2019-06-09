package correlation;

import java.util.Arrays;

public class CorrelationS implements CorrelationCounter {
    private double[] x;
    private double[] y;


    @Override
    public double countCorrelation(double[] _x, double[] _y) {
        this.x = _x;
        this.y = _y;

        int n = _x.length;

        return rk();
    }

    private double rk() {
        CorrelationP pirson = new CorrelationP();

        double[] _x = x.clone();
        double[] _y = y.clone();

        Arrays.sort(_x);
        Arrays.sort(_y);

        double[] rankX = new double[_x.length];
        double[] rankY = new double[_y.length];

        for (int i = 0; i < _x.length; i++) {
            rankX[i] = Arrays.binarySearch(_x, x[i]);
            rankY[i] = Arrays.binarySearch(_y, y[i]);
        }

        return pirson.countCorrelation(rankX, rankY);
    }
}
