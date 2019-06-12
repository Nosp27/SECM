package regression;

import clusterisation.DisplayFactory;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.Plus;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Renderer {
    public static void render(AbstractRegression r, int index) {
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
        for (int i = 0; i < arr[arr.length - 1]; i++) {
            rys.add(b[0] + b[index + 1] * i);
        }

        chart.addSeries("Regression data", plotDataX, plotDataY);
        chart.addSeries("Regression line", rys);
        frame.setContentPane(new XChartPanel<>(chart));
        frame.pack();
        frame.setVisible(true);
    }

    public static void renderAccuracy(AbstractRegression r) {
        JFrame frame = new JFrame(r.getType() + " Regression accuracy");
        XYChart chart = new XYChart(500, 700);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setSeriesMarkers(new Marker[]{new Plus(), new DisplayFactory.my_marker(5)});
        chart.getStyler().setSeriesColors(new Color[]{Color.BLUE, Color.BLACK});
        int n = r.xdata().length;
        float[] plotDataY = new float[n];
        for (int i = 0; i < n; i++) {
            plotDataY[i] = 1 - Math.abs(r.prediction(i) - r.data.get(i)[0]);
        }

        Arrays.sort(plotDataY);
        chart.addSeries(r.getType() + "Regression data", plotDataY);
        frame.setContentPane(new XChartPanel<>(chart));
        frame.pack();
        frame.setVisible(true);
    }

    public static void renderSS(AbstractRegression r) {
        JFrame frame = new JFrame(r.getType() + " Regression S/S");
        XYChart chart = new XYChart(500, 700);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setSeriesMarkers(new Marker[]{new Plus(), new DisplayFactory.my_marker(5)});
        chart.getStyler().setSeriesColors(new Color[]{Color.BLUE, Color.BLACK});

        int steps = 12;
        float[] xs = new float[steps];
        float[][] plot = new float[2][steps];
        for (int i = 0; i < steps; i++) {
            xs[i] = i*(1f/steps);
            float[] data = r.spec_sens(i * (1f/steps));
            plot[0][i] = data[0];
            plot[1][i] = data[1];
        }

        chart.addSeries(r.getType() + " Sensitivity", xs, plot[0]);
        chart.addSeries(r.getType() + " Specificity", xs, plot[1]);
        frame.setContentPane(new XChartPanel<>(chart));
        frame.pack();
        frame.setVisible(true);
    }

    public static void renderGradient(ProbitRegression r) {
        JFrame frame = new JFrame(r.getType() + " Regression S/S");
        XYChart chart = new XYChart(500, 700);

        for (int i = 0; i < r.plotData.length; i++) {
            chart.addSeries("k = " + i, r.plotData[i]);
        }
        frame.setContentPane(new XChartPanel<>(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
