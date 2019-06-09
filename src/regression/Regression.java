package regression;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Arrays;

public class Regression {
    private ArrayList<Float[]> data;
    private int k;
    private float[] _y, b, e;
    private Float ESS, TSS, RSS;

    public Regression(ArrayList<Float[]> data) {
        this.data = data;
        k = data.get(0).length;
    }

    private void regress() {
        float[][] xdata = new float[data.size()][data.get(0).length + 1];
        float[][] ydata = new float[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            ydata[i][0] = data.get(i)[0];
            for (int j = 0; j <= data.get(0).length; j++) {
                if (j == 0)
                    xdata[i][j] = 1;
                else
                    xdata[i][j] = data.get(i)[j-1];
            }
        }
        SimpleMatrix x = new SimpleMatrix(xdata);
        SimpleMatrix y = new SimpleMatrix(ydata);
        SimpleMatrix _b = x.transpose().mult(x);
        _b = _b.invert();
        _b = _b.mult(x.transpose()).mult(y);
        SimpleMatrix yVec = x.mult(_b);

        b = new float[k];
        for (int i = 0; i < k; i++)
            b[i] = (float) _b.get(i, 0);

        _y = new float[data.size()];
        e = new float[data.size()];
        for (int i = 0; i < data.size(); i++) {
            _y[i] = (float) yVec.get(i, 0);
            e[i] = (float) (y.get(i, 0) - _y[i]);
        }
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
        return 1 - RSS() / TSS();
    }

    public float fStatistic(){
        return (ESS()/(k-1))/(RSS()/(data.size() - k));
    }

    public boolean isValidAtQuantile(float q){
        float F = fStatistic();
        return F >= q;
    }

    @Override
    public String toString() {
        return "\tb = " + Arrays.toString(b())+
                "\r\n\t" +"ESS= " + ESS() +
                "\r\n\t" +"RSS= " + RSS() +
                "\r\n\t" +"TSS= " + TSS() +
                "\r\n\t" +"R^2= " + R_kvadrat() +
                "\r\n\t" +"Valid at 5%: " + isValidAtQuantile(3.2f) + " (" + fStatistic() + ")";
    }
}
