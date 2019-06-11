package tasks;

import regression.DataReader;
import regression.Regression;
import regression.Renderer;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Regression r;
        ArrayList<Float[]> data;

        data = DataReader.read("secm/hw4/data", 2, x -> x.get(2) == 0);
        System.out.println("Size: " + data.size());
        r = new Regression(data);
        System.out.println("Boys:");
        System.out.println(r.toString());
//        Renderer.render(r, 0);
        System.out.println("_______________________________\n");

        data = DataReader.read("secm/hw4/data", 2, x -> x.get(2) == 1);
        System.out.println("Size: " + data.size());
        r = new Regression(data);
        System.out.println("Girls:");
        System.out.println(r.toString());
//        Renderer.render(r, 0);

        System.out.println("_______________________________\n");

        data = DataReader.read("secm/hw4/data", 3, null, new int[]{2, 1});
        System.out.println("Size: " + data.size());
        r = new Regression(data);
        System.out.println("Overall:");
        System.out.println(r.toString());
        Renderer.render(r, 0);
//        Renderer.render(r, 1);
        Renderer.render(r, 2);
        Regression rr = new Regression(DataReader.read("secm/hw4/data", 2, null));
        System.out.println("_______________________________\n");
        System.out.println("Restricted: ");
        System.out.println(rr.toString());
        System.out.println("determ: " + Regression.determ(r, rr));
    }
}
