package regression;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractRegression {
    protected float[] b;
    protected float[][] xdata, ydata;
    protected float[] _y, e;
    protected ArrayList<Float[]> data;
    protected Float ESS, TSS, RSS;
    protected int k;

    protected final void regress() {
        _y = new float[data.size()];
        e = new float[data.size()];
        b = new float[k];
        xdata = new float[data.size()][k];
        ydata = new float[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            ydata[i][0] = data.get(i)[0];
            for (int j = 0; j < k; j++) {
                if (j == 0)
                    xdata[i][j] = 1;
                else
                    xdata[i][j] = data.get(i)[j];
            }
        }

        regress0();
    }

    protected abstract void regress0();

    public final float prediction(int observation) {
        return prediction(toPrimitive(data.get(observation)));
    }

    public final float prediction(Float[] input) {
        return prediction(toPrimitive(input));
    }

    public final float prediction(float[] input) {
        if (b == null)
            regress();

        SimpleMatrix in = new SimpleMatrix(k, 1, false, input).rows(1, k);
        SimpleMatrix B = new SimpleMatrix(b.length, 1, false, b);
        B = B.rows(1, k);
        float prediction = (float) activation(in.transpose().mult(B).get(0) + b[0]);
//        System.out.println(prediction);
        return prediction;
    }

    public float accuracy(float acceptLevel) {
        float hits = 0;

        for (Float[] _d : data) {
            float prediction = prediction(toPrimitive(_d));
            if ((prediction > acceptLevel) == (_d[0] == 1))
                hits += 1;
        }
        return hits / data.size();
    }

    public float[] spec_sens(float acceptLevel) {
        float[] totals = new float[2];
        float[] ret = new float[2];

        for (Float[] _d : data) {
            float prediction = prediction(toPrimitive(_d));
            if (_d[0] == 1)
                totals[0]++;
            else totals[1]++;
            if ((prediction > acceptLevel) == (_d[0] == 1))
                if (_d[0] == 1)
                    ret[0] += 1;
                else ret[1] += 1;
        }
        ret[0] /= totals[0];
        ret[1] /= totals[1];
        return ret;
    }

    private float[] toPrimitive(Float[] origin) {
        float[] primitive = new float[origin.length];
        for (int i = 0; i < primitive.length; i++)
            primitive[i] = origin[i];
        return primitive;
    }

    public float[][] xdata() {
        if (xdata == null)
            regress();
        return xdata;
    }

    public float[][] ydata() {
        if (ydata == null)
            regress();
        return ydata;
    }

    protected double activation(double raw) {
        return raw;
    }

    public float[] b() {
        if (b == null)
            regress();
        return b;
    }

    public float[] e() {
        if (e == null)
            regress();
        return e;
    }

    public float RSS() {
        if (RSS != null)
            return RSS;
        RSS = 0f;
        for (int i = 0; i < data.size(); i++)
            RSS += e()[i] * e()[i];
        return RSS;
    }

    public float ESS() {
        if (ESS != null)
            return ESS;
        ESS = 0f;
        float avgY = 0;
        for (int i = 0; i < data.size(); i++)
            avgY += data.get(i)[0];
        avgY /= data.size();
        for (int i = 0; i < data.size(); i++)
            ESS += (_y[i] - avgY) * (_y[i] - avgY);
        return ESS;
    }

    public float TSS() {
        if (TSS != null)
            return TSS;
        TSS = 0f;
        float avgY = 0;
        for (int i = 0; i < data.size(); i++)
            avgY += data.get(i)[0];
        avgY /= data.size();
        for (int i = 0; i < data.size(); i++)
            TSS += (data.get(i)[0] - avgY) * (data.get(i)[0] - avgY);
        return TSS;
    }

    public float R_kvadrat() {
        return ESS() / RSS();
    }

    public float fStatistic() {
        return (ESS() / (k - 1)) / (RSS() / (data.size() - k));
    }

    public boolean isValidAtQuantile(float q) {
        float F = fStatistic();
        return F >= q;
    }

    @Override
    public String toString() {
        return "\tb = " + Arrays.toString(b()) +
                "\r\n\t" + "ESS= " + ESS() +
                "\r\n\t" + "RSS= " + RSS() +
                "\r\n\t" + "TSS= " + TSS() +
                "\r\n\t" + "R^2= " + R_kvadrat() +
                "\r\n\t" + "Valid at 5%: " + isValidAtQuantile(3.2f) + " (" + fStatistic() + ")";
    }

    public abstract String getType();

}
