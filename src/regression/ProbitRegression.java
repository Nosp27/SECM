package regression;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import java.util.ArrayList;

public class ProbitRegression extends LogisticRegression {

    public ProbitRegression(ArrayList<Float[]> data, int epochs, double learningRate) {
        super(data, epochs, learningRate);
    }

    @Override
    protected double activation(double raw) {
        //standart normal distribution
        try {
            NormalDistributionImpl nd = new NormalDistributionImpl();
            return nd.cumulativeProbability(raw);
        } catch (MathException e) {
            return 0;
        }
    }
}
