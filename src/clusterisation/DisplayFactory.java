package clusterisation;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.XChartDemo;
import org.knowm.xchart.demo.XChartStyleDemo;
import org.knowm.xchart.internal.ChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisplayFactory {
    public static void createNewFrame(Cluster[] cs, PointsReader pr) {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        XYChartBuilder builder = new XYChartBuilder();
        XYChart chart = builder.build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        for (int i = 0; i < cs.length; i++) {
            ArrayList<Float> xs = cs[i].getPoints().stream().map(x -> x[0]).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Float> ys = cs[i].getPoints().stream().map(y -> y[1]).collect(Collectors.toCollection(ArrayList::new));
            chart.addSeries("Cluster " + i, xs, ys);
        }
        frame.setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.RED);
                chart.paint((Graphics2D) g, getWidth(), getHeight());
            }
        });
        frame.setVisible(true);
    }
}
