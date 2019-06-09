package correlation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class YoutubeCSVReader {
    private File file;
    private static final int size = 7;
    private ArrayList<Double[]> data;

    public YoutubeCSVReader(File f) {
        file = f;
    }

    private void parse() {
        if (data != null)
            return;

        data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            br.readLine();
            while ((s = br.readLine()) != null && !s.isEmpty()) {
                Scanner sc = new Scanner(s);
                sc.useLocale(Locale.US);
                sc.useDelimiter("[,\n]");
                int _i = sc.nextInt();
                String n = sc.next();

                Double[] d = new Double[]{
                        sc.nextDouble(),
                        (double) sc.nextInt(),
                        (double) sc.nextInt(),
                        sc.nextDouble(),
                        (double) sc.nextInt()
                };
                data.add(d);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double[] get(int k){
        parse();
        double[] res = new double[data.size()];
        for(int i = 0; i < data.size(); i++){
            res[i] = data.get(i)[k];
        }
        return res;
    }
}
