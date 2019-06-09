package regression;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class DataReader {
    public static ArrayList<Float[]> read(String filename, int n, Predicate<ArrayList<Float>> condition, int[]... additionals) {
        try {
            ArrayList<Float[]> res = new ArrayList<>();
            File f = new File(filename);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                Float[] data = new Float[n + additionals.length];
                ArrayList<Float> a = new ArrayList<>();
                Scanner sc = new Scanner(line);
                while(sc.hasNextFloat())
                    a.add(sc.nextFloat());

                for (int i = 0; i < n; i++)
                    data[i] = a.get(i);

                if(additionals.length>0)
                for(int i = 0; i < additionals.length; i++){
                    float v = 1;
                    for(int j = 0; j < additionals[i].length; j++)
                        v *= a.get(additionals[i][j]);
                    data[i + n] = v;
                }

                if (condition == null || condition.test(a))
                    res.add(data);
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
