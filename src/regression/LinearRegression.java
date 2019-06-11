package regression;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Arrays;

public class LinearRegression extends AbstractRegression {

    public LinearRegression(ArrayList<Float[]> data) {
        this.data = data;
        k = data.get(0).length;
    }

    protected void regress0() {
        SimpleMatrix x = new SimpleMatrix(xdata);
        SimpleMatrix y = new SimpleMatrix(ydata);
        SimpleMatrix _b = x.transpose().mult(x);
        _b = _b.invert();
        _b = _b.mult(x.transpose()).mult(y);
        SimpleMatrix yVec = x.mult(_b);

        for (int i = 0; i < k; i++)
            b[i] = (float) _b.get(i, 0);

        for (int i = 0; i < data.size(); i++) {
            _y[i] = (float) yVec.get(i, 0);
            e[i] = (float) (y.get(i, 0) - _y[i]);
        }
    }


    public static float determ(LinearRegression r1, LinearRegression r2) {
        float rk1 = -1+r1.R_kvadrat();
        float rk2 = -1+r2.R_kvadrat();

        return ((rk1 - rk2) * (r1.data.size() - r1.k)) / ((1 - rk1) * 2);
    }

    @Override
    public String getType(){return "Linear"; }
}
