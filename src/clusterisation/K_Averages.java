package clusterisation;

import java.util.ArrayList;

public class K_Averages {
    private ArrayList<Float[]> points;

    public K_Averages(ArrayList<Float[]> _points) {
        points = _points;
    }

    public final Cluster[] clusterize(int k) {
        int countIterations = 0;
        Cluster[] cs = new Cluster[k];
        initCentroids(cs, k);
        do {
            countIterations++;
            associatePoints(cs, k);
        }while (changeCentroids(cs, k));
        System.out.println(k + " Clusters, " + countIterations + " iterations");
        return cs;
    }

    private void initCentroids(Cluster[] cs, int k){
            //set centroids at step 0
            for (int i = 0; i < k; i++)
                cs[i] = new Cluster(points.get(i));
    }

    private boolean changeCentroids(Cluster[] cs, int k) {
        boolean changed = false;
        for(int i = 0; i < k; i++){
            Float[] newPosition = countNewCentroid(cs[i]);
            if(cs[i].distance(newPosition) > 0)
                changed = true;
            cs[i].center = newPosition;
        }
        return changed;
    }

    protected Float[] countNewCentroid(Cluster c){
        Float[] newPosition = new Float[]{0f,0f};
        for(Float[] point : c.getPoints()){
            newPosition[0] += point[0];
            newPosition[1] += point[1];
        }
        newPosition[0] /= c.getPoints().size();
        newPosition[1] /= c.getPoints().size();
        return newPosition;
    }

    private void associatePoints(Cluster[] cs, int k) {
        for (Cluster c : cs)
            c.clear();

        for (Float[] point : points) {
            Cluster nearest = cs[0];
            Float distance = nearest.distance(point);

            for (int kNum = 1; kNum < k; kNum++) {
                Float newDistance = cs[kNum].distance(point);
                if (newDistance < distance) {
                    distance = newDistance;
                    nearest = cs[kNum];
                }
            }

            nearest.addPoint(point);
        }
    }

    @Override
    public String toString() {
        return " Averages algorythm";
    }
}
