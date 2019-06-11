package regression;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class ProbitRegression extends LogisticRegression {

    public ProbitRegression(ArrayList<Float[]> data, int epochs, double learningRate) {
        super(data, epochs, learningRate);
    }

    @Override
    protected void regress0() {
        SimpleMatrix x = new SimpleMatrix(xdata);
        SimpleMatrix y = new SimpleMatrix(ydata);

        SimpleMatrix theta = new SimpleMatrix(k, 1);
        SimpleMatrix h = theta;
//        theta = theta.plus(1);
        for (int epochNum = 0; epochNum < epochs; epochNum++) {
            h = theta.transpose().mult(x.transpose()).transpose();
            SimpleMatrix thetaGradient = gradient(x, y, h);
            theta = theta.minus(thetaGradient.scale(learningRate));

            b = new float[k];
            for (int i = 0; i < theta.numRows(); i++) {
                b[i] = (float) theta.get(i, 0);
            }

            for (int i = 0; i < data.size(); i++) {
                _y[i] = (float) activation(h.get(i, 0));
                e[i] = (float) (y.get(i, 0) - _y[i]);
            }

            RSS = null;
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

    private double lyambda(int i, SimpleMatrix h, SimpleMatrix y){
        double qi = 2 * y.get(i, 0) - 1;
        double hi = h.get(i, 0);
        return gauss(qi*hi)*qi/activation(qi*hi);
    }

    private double sd(SimpleMatrix x, SimpleMatrix y, SimpleMatrix h){
        double sd = 0;
        for(int i = 0; i < data.size(); i++){
            double hi = h.get(i, 0);
            double lyambda = lyambda(i, h, y);
            SimpleMatrix _x = x.rows(i,i+1);
            sd+=lyambda*(hi+lyambda)*_x.transpose().mult(_x).get(0);
        }
        return sd;
    }

    @Override
    protected SimpleMatrix gradient(SimpleMatrix x, SimpleMatrix y, SimpleMatrix h) {
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
                sum += (yi*(g/a) + (1-yi)*g/(1-a))*xi;
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
