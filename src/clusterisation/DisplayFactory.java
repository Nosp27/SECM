package clusterisation;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DisplayFactory {
    public static void createNewFrame(Cluster[] cs, String title) {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(400, 400);
        XYChartBuilder builder = new XYChartBuilder();
        XYChart chart = builder.build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setSeriesMarkers(new Marker[]{new Circle(), new my_marker(15)});
        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.RED, Color.BLUE, Color.BLUE, Color.GREEN, Color.GREEN, Color.MAGENTA, Color.MAGENTA});
        chart.getStyler().setMarkerSize(5);
        for (int i = 0; i < cs.length; i++) {
            ArrayList<Float> xs = cs[i].getPoints().stream().map(x -> x[0]).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Float> ys = cs[i].getPoints().stream().map(y -> y[1]).collect(Collectors.toCollection(ArrayList::new));
            chart.addSeries("Cluster " + i, xs, ys);
            chart.addSeries("Centroid " + i, new double[]{cs[i].center[0]}, new double[]{cs[i].center[1]});
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

    public static class my_marker extends Diamond{
        private int sz;
        public my_marker(int size){
            sz = size;
        }
        @Override
        public void paint(Graphics2D g, double xOffset, double yOffset, int markerSize) {
            super.paint(g, xOffset, yOffset, sz);
        }
    }
}
