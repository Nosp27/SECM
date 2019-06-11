package tasks;

import regression.DataReader;
import regression.LinearRegression;

import java.util.ArrayList;

public class Task4 {
    public static void main(String[] args) {
        LinearRegression r;
        ArrayList<Float[]> data;

        data = DataReader.read("secm/hw4/data", 2, x -> x.get(2) == 0);
        System.out.println("Size: " + data.size());
        r = new LinearRegression(data);
        System.out.println("Boys:");
        System.out.println(r.toString());

        System.out.println("_______________________________\n");

        data = DataReader.read("secm/hw4/data", 2, x -> x.get(2) == 1);
        System.out.println("Size: " + data.size());
        r = new LinearRegression(data);
        System.out.println("Girls:");
        System.out.println(r.toString());

        System.out.println("_______________________________\n");

        data = DataReader.read("secm/hw4/data", 3, null, new int[]{2, 1});
        System.out.println("Size: " + data.size());
        r = new LinearRegression(data);
        System.out.println("Overall:");
        System.out.println(r.toString());
    }
}
