package tasks;

import regression.DataReader;
import regression.LogisticRegression;
import regression.ProbitRegression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public class Task5 {
    public static void main(String[] args) {
        ArrayList<Function<Float[], Float>> binaryTraits = new ArrayList<>();
        binaryTraits.add(data->data[5]==1 ? 1f : 0f);
        binaryTraits.add(data->data[5]==2 ? 1f : 0f);
        ArrayList<Float[]> trainig = DataReader.read("secm/hw5/data", 5, null, binaryTraits);
        ArrayList<Float[]> test = DataReader.read("secm/hw5/data", 5, data->data.size() == 5, binaryTraits);

        System.out.println("Logistic Regression");
        LogisticRegression r = new LogisticRegression(trainig, 100, 0.0001);
        System.out.println(r.toString());
        System.out.println("\tAccuracy: " + r.accuracy(0.7f));

        System.out.println("____________________________________________\n");
        System.out.println("Probability based Regression");
        ProbitRegression pr = new ProbitRegression(trainig, 10, 0.0001);
        System.out.println(pr.toString());
        System.out.println("\tAccuracy: " + pr.accuracy(0.75f));
    }
}
