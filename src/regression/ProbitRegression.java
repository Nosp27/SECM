package regression;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ProbitRegression extends LogisticRegression {

    SimpleMatrix x, y, theta, h;

    private int epochNum;
    double[][] plotData;

    public ProbitRegression(ArrayList<Float[]> data, int epochs, double learningRate) {
        super(data, epochs, learningRate);
        plotData = new double[k + 1][epochs];
    }

    @Override
    protected void regress0() {
        x = new SimpleMatrix(xdata);
        y = new SimpleMatrix(ydata);

        theta = new SimpleMatrix(k, 1);
        h = theta;
//        theta = theta.plus(1);
        for (epochNum = 0; epochNum < epochs; epochNum++) {
            h = theta.transpose().mult(x.transpose()).transpose();
            SimpleMatrix thetaGradient = gradient(x, y, h);
            theta = theta.plus(thetaGradient.scale(learningRate));

            b = new float[k];
            for (int i = 0; i < theta.numRows(); i++) {
                b[i] = (float) theta.get(i, 0);
            }

            for (int i = 0; i < data.size(); i++) {
                _y[i] = (float) activation(h.get(i, 0));
                e[i] = (float) (y.get(i, 0) - _y[i]);
            }

            RSS = null;
            ESS = null;
            TSS = null;
            plotData[k][epochNum] = R_kvadrat();
//            System.out.println(RSS());
        }
    }

    @Override
    protected double activation(double raw) {
        //standart normal distribution
        try {
            NormalDistributionImpl nd = new NormalDistributionImpl();
            double ret = nd.cumulativeProbability(raw);
            return ret;
        } catch (MathException e) {
            return 0;
        }
    }

    private double gauss(double in) {
        double coeff = 1d / Math.sqrt(2 * Math.PI);
        double pow = -in * in / 2;
        return coeff * Math.exp(pow);
    }

    @Override
    protected SimpleMatrix gradient(SimpleMatrix x, SimpleMatrix y, SimpleMatrix h) {
        return gradientMatrix();
    }

    private SimpleMatrix gradientMatrix() {
        SimpleMatrix gradient = new SimpleMatrix(k, 1);
        Supplier<SimpleMatrix> lyambda = () -> {
            SimpleMatrix ret = new SimpleMatrix(data.size(), 1);
            for (int i = 0; i < data.size(); i++) {
                double qi = 2 * y.get(i, 0) - 1;
                double hi = h.get(i);
                ret.set(i, gauss(qi * hi) * qi / activation(qi * hi));
            }
            return ret;
        };
        //count lysmbdas
        SimpleMatrix lyamblaVector = lyambda.get();
        double[] diag = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            double _lyambla = lyamblaVector.get(i);
            diag[i] = _lyambla * (h.get(i, 0) + _lyambla);
        }
        SimpleMatrix lt = lyambda.get();
        SimpleMatrix W = SimpleMatrix.diag(diag);
        SimpleMatrix xT = x.transpose();
        SimpleMatrix mc = (xT.mult(W).mult(x).invert().mult(xT).mult(lt));
        gradient = mc;

        for (int i = 0; i < k; i++)
            plotData[i][epochNum] = mc.get(i);

        return gradient;
    }

    private SimpleMatrix gradientCyclic(SimpleMatrix x, SimpleMatrix y, SimpleMatrix h) {
        double sum = 0;
        SimpleMatrix gradient = new SimpleMatrix(k, 1);
        for (int beta = 0; beta < k; beta++) {
            //second derivative

            for (int i = 0; i < data.size(); i++) {
                double hi = h.get(i, 0);
                double yi = y.get(i, 0);
                double xi = x.get(i, beta);
                double g = gauss(hi);
                double a = activation(hi);
//                sum += (g / (a * (1f - a))) * (yi - activation(hi)) * xi;
                sum += (yi * (g / a) + (1 - yi) * g / (1 - a)) * xi;
            }
            gradient.set(beta, 0, sum);
        }
        return gradient;
    }

    @Override
    public String getType() {
        return "Probability based";
    }
}
