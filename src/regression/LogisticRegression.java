package regression;

import com.sun.deploy.util.ArrayUtil;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LogisticRegression extends AbstractRegression {
    double learningRate;
    int epochs;
    private ArrayList<Float> cost_function;

    public LogisticRegression(ArrayList<Float[]> data, int epochs, double learningRate) {
        this.data = data;
        k = data.get(0).length;
        this.epochs = epochs;
        this.learningRate = learningRate;
    }

    @Override
    protected void regress0() {
        SimpleMatrix x = new SimpleMatrix(xdata);
        SimpleMatrix y = new SimpleMatrix(ydata);

        SimpleMatrix theta = new SimpleMatrix(k, 1);
        theta = theta.plus(1);
        cost_function = new ArrayList<>();
        SimpleMatrix h = theta;
        for (int epochNum = 0; epochNum < epochs; epochNum++) {
            h = theta.transpose().mult(x.transpose()).transpose();
            for (int row = 0; row < h.numRows(); row++) {
                h.set(row, 0, activation(h.get(row, 0)));
            }
            float cost = 0;
            for (int row = 0; row < h.numRows(); row++) {
                double yi = y.get(row, 0);
                double hi = h.get(row, 0);
                cost += -yi * Math.log((hi) - (1 - yi) * Math.log(1 - hi));
            }
            cost_function.add(cost);
            SimpleMatrix thetaGradient = gradient(x, y, h);
            theta = theta.minus(thetaGradient.scale(learningRate));
        }

        b = new float[k];
        for (int i = 0; i < theta.numRows(); i++) {
            b[i] = (float) theta.get(i, 0);
        }

        for (int i = 0; i < data.size(); i++) {
            _y[i] = (float) h.get(i, 0);
            e[i] = (float) (y.get(i, 0) - _y[i]);
        }
    }

    protected SimpleMatrix gradient(SimpleMatrix x, SimpleMatrix y, SimpleMatrix h) {
        return h.minus(y).transpose().mult(x).transpose();
    }

    @Override
    protected double activation(double raw) {
        return (1f / (1f + Math.exp(-raw)));
    }

    @Override
    public String getType() {
        return "Logistic";
    }
}
