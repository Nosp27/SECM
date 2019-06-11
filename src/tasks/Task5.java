package tasks;

import regression.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public class Task5 {
    public static void main(String[] args) {
        ArrayList<Function<Float[], Float>> binaryTraits = new ArrayList<>();
        binaryTraits.add(data->data[data.length-1]==1 ? 1f : 0f);
        binaryTraits.add(data->data[data.length-1]==2 ? 1f : 0f);
        ArrayList<Float[]> trainig = DataReader.read("secm/hw5/data", 5, data->data.size() == 6, binaryTraits);
        ArrayList<Float[]> test = DataReader.read("secm/hw5/data", 5, data->data.get(0) == 2, binaryTraits);

        System.out.println("Linear Regression");
        LinearRegression linearRegression = new LinearRegression(trainig);
        System.out.println(linearRegression.toString());
        System.out.println("\tAccuracy: " + linearRegression.accuracy(0.67f));
        System.out.println("____________________________________________\n");
        System.out.println("Logistic Regression");
        LogisticRegression logisticRegression = new LogisticRegression(trainig, 100, 0.001);
        System.out.println(logisticRegression.toString());
        System.out.println("\tAccuracy: " + logisticRegression.accuracy(0.63f));

        System.out.println("____________________________________________\n");
        System.out.println("Probability based Regression");
        ProbitRegression probitRegression = new ProbitRegression(trainig, 10, .0001);
        System.out.println(probitRegression.toString());
        System.out.println("\tAccuracy: " + probitRegression.accuracy(.45f));

        Renderer.renderAccuracy(linearRegression);
        Renderer.renderAccuracy(logisticRegression);
        Renderer.renderAccuracy(probitRegression);
        Renderer.renderSS(logisticRegression);

        System.out.println("____________________________________________\n");
        System.out.println("Linear Regression");
        for(int i = 0; i < test.size(); i++)
            System.out.println(linearRegression.prediction(test.get(i)));

        System.out.println("____________________________________________\n");
        System.out.println("Logit Regression");
        for(int i = 0; i < test.size(); i++)
            System.out.println(logisticRegression.prediction(test.get(i)));

        System.out.println("____________________________________________\n");
        System.out.println("Probit Regression");
        for(int i = 0; i < test.size(); i++)
            System.out.println(probitRegression.prediction(test.get(i)));
    }
}
