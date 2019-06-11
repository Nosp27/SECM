package regression;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataReader {

    public static ArrayList<Float[]> read(String filename, int n, Predicate<ArrayList<Float>> condition, int[]... additionals) {
        return read0(filename, n, 0, condition, null, additionals);
    }

    public static ArrayList<Float[]> read(String filename, int n, Predicate<ArrayList<Float>> condition, ArrayList<Function<Float[], Float>> binaryTraits, int[]... additionals) {
        return read0(filename, n, 0, condition, binaryTraits, additionals);
    }

    public static ArrayList<Float[]> read(String filename, int n, int offset, Predicate<ArrayList<Float>> condition, ArrayList<Function<Float[], Float>> binaryTraits, int[]... additionals) {
        return read0(filename, n, offset, condition, binaryTraits, additionals);
    }

    private static ArrayList<Float[]> read0(String filename, int n, int offset, Predicate<ArrayList<Float>> condition, ArrayList<Function<Float[], Float>> binaryTraits, int[]... additionals) {
        try {
            ArrayList<Float[]> res = new ArrayList<>();
            File f = new File(filename);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int currentOffset = 0;
            while ((line = br.readLine()) != null) {
                currentOffset++;
                if (currentOffset <= offset)
                    continue;
                Float[] data = new Float[n + additionals.length + (binaryTraits == null ? 0 : binaryTraits.size())];
                ArrayList<Float> a = new ArrayList<>();
                Scanner sc = new Scanner(line);
                while (sc.hasNextFloat())
                    a.add(sc.nextFloat());

                try {
                    for (int i = 0; i < n; i++)
                        data[i] = a.get(i);

                    if (additionals.length > 0)
                        for (int i = 0; i < additionals.length; i++) {
                            float v = 1;
                            for (int j = 0; j < additionals[i].length; j++)
                                v *= a.get(additionals[i][j]);
                            data[i + n] = v;
                        }

                    if (binaryTraits != null)
                        for (int i = 0; i < binaryTraits.size(); i++) {
                            float v = binaryTraits.get(i).apply(a.toArray(new Float[0]));
                            data[i + n + additionals.length] = v;
                        }

                    if (condition == null || condition.test(a))
                        res.add(data);
                } catch (Exception e) {
                    System.out.println();
                }
            }
            return norm(res);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static ArrayList<Float[]> norm(ArrayList<Float[]> raw) {
        Float[] maximums = new Float[raw.get(0).length];
        for (int i = 0; i < raw.size(); i++) {
            for (int j = 1; j < maximums.length; j++) {
                if (maximums[j] == null || raw.get(i)[j] > maximums[j]) {
                    maximums[j] = raw.get(i)[j];
                }
            }
        }

        for (int i = 0; i < raw.size(); i++) {
            for (int j = 1; j < maximums.length; j++) {
                if (maximums[j] != 0)
                    raw.get(i)[j] /= maximums[j];
            }
        }

        return raw;
    }
}
