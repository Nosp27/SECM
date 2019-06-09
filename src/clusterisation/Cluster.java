package clusterisation;

import java.util.ArrayList;

public class Cluster {
    Float[] center;
    private ArrayList<Float[]> points;

    public ArrayList<Float[]> getPoints() {
        return points;
    }

    Cluster(Float[] centroid) {
        center = centroid;
        points = new ArrayList<>();
    }

    public void addPoint(Float[] point) {
        if (point == null || point.length != 2)
            return;
        points.add(point);
    }

    void clear() {
        points.clear();
    }

    Float distance(Float[] point) {
        return (point[0] - center[0]) * (point[0] - center[0]) + (point[1] - center[1]) * (point[1] - center[1]);
    }
}
