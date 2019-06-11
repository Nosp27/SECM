package tasks;

import regression.DataReader;
import regression.LinearRegression;
import regression.Renderer;
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
//        Renderer.render(r, 0);
        System.out.println("_______________________________\n");

        data = DataReader.read("secm/hw4/data", 2, x -> x.get(2) == 1);
        System.out.println("Size: " + data.size());
        r = new LinearRegression(data);
        System.out.println("Girls:");
        System.out.println(r.toString());
//        Renderer.render(r, 0);

        System.out.println("_______________________________\n");

        data = DataReader.read("secm/hw4/data", 3, null, new int[]{2, 1});
        System.out.println("Size: " + data.size());
        r = new LinearRegression(data);
        System.out.println("Overall:");
        System.out.println(r.toString());
        Renderer.render(r, 0);
//        Renderer.render(r, 1);
        Renderer.render(r, 2);
        LinearRegression rr = new LinearRegression(DataReader.read("secm/hw4/data", 2, null));
        System.out.println("_______________________________\n");
        System.out.println("Restricted: ");
        System.out.println(rr.toString());
        System.out.println("determ: " + LinearRegression.determ(r, rr));
    }
}
