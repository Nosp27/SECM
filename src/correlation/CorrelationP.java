package correlation;

public class CorrelationP implements CorrelationCounter{
    private double[] x, y;
    private double ex, ey;

    @Override
    public double countCorrelation(double[] _x, double[] _y) {
        this.x = _x;
        this.y = _y;

        if(x.length != y.length)
            throw new IllegalArgumentException();

        ex = avg(x);
        ey = avg(y);

        return dispsummul() / Math.sqrt(disp(x, ex) * disp(y, ey));
    }

    private double dispsummul() {
        double res = 0;
        for (int i = 0; i < x.length; i++) {
            res += (x[i] - ex) * (y[i] - ey);
        }
        return res;
    }

    private double disp(double[] x, double avg) {
        double res = 0;
        for (int i = 0; i < x.length; i++) {
            res += Math.pow(x[i] - avg, 2);
        }
        return res;
    }

    private double avg(double[] data) {
        double ex = 0;
        for (int i = 0; i < data.length; i++) {
            ex += data[i];
        }
        return ex / data.length;
    }
}
