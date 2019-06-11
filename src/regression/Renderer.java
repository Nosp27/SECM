package regression;

import clusterisation.DisplayFactory;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.Plus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Renderer {
    public static void render(LinearRegression r, int index) {
        JFrame frame = new JFrame("Regression index " + index);
        XYChart chart = new XYChart(500, 700);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setSeriesMarkers(new Marker[]{new Plus(), new DisplayFactory.my_marker(5)});
        chart.getStyler().setSeriesColors(new Color[]{Color.BLUE, Color.BLACK});
        float[][] xs = r.xdata();
        float[][] ys = r.ydata();
        int n = xs.length;
        float[] plotDataX = new float[n];
        float[] plotDataY = new float[n];
        for (int i = 0; i < n; i++) {
            plotDataX[i] = xs[i][index + 1];
            plotDataY[i] = ys[i][0];
        }

        ArrayList<Float> rys = new ArrayList<>();
        float[] b = r.b();
        float[] arr = Arrays.copyOf(plotDataX, plotDataX.length);
        Arrays.sort(arr);
        for (int i = 0; i < arr[arr.length-1]; i++) {
            rys.add(b[0] + b[1] * i);
        }

        chart.addSeries("Regression data", plotDataX, plotDataY);
        chart.addSeries("Regression line", rys);
        frame.setContentPane(new XChartPanel<>(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
