package regression;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public abstract class AbstractRegression {
    protected float[] b;
    protected float[][] xdata, ydata;
    protected ArrayList<Float[]> data;
    protected int k;

    protected final void regress() {
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

    public float[] b() {
        if (b == null)
            regress();
        return b;
    }

    public final float prediction(float[] input) {
        if (b == null)
            regress();

        SimpleMatrix in = new SimpleMatrix(k, 1, false, input).rows(1, k);
        SimpleMatrix B = new SimpleMatrix(b.length, 1, false, b);
        B = B.rows(1, k);
        float prediction = (float)activation(in.transpose().mult(B).get(0) + b[0]);
        System.out.println(prediction);
        return prediction;
    }

    protected double activation(double raw){
        return raw;
    }
}
