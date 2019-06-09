package correlation;

import tasks.Task3;

import java.io.*;

public class DataCSVWriter {
    private File file;
    private static final int size = 7;

    private String label = "framerate,frames,bitrate,duration,size\n";

    public DataCSVWriter(File f) {
        file = f;
    }

    public void write(double[][] data) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(label);
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    fw.write(String.format("%.3f" + value(data[i][j]) + ",\t", data[i][j]));
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String value(double v) {
        double statistic = Task3.getStatistic(Math.abs(v));
        if (statistic >= 3.46)
            return "***";
        if (statistic >= 2.66)
            return "**";
        if (statistic >= 2)
            return "*";

        return "";
    }
}
