package clusterisation;

import java.util.ArrayList;

public class K_Medians extends K_Averages {

    public K_Medians(ArrayList<Float[]> _points) {
        super(_points);
    }

    @Override
    protected Float[] countNewCentroid(Cluster c) {
        Float[] newPosition = new Float[2];
        Float[] arr;

        arr = c.getPoints().stream().map(f->f[0]).sorted().toArray(Float[]::new);
        newPosition[0] = arrMedian(arr);
        arr = c.getPoints().stream().map(f->f[1]).sorted().toArray(Float[]::new);
        newPosition[1] = arrMedian(arr);

        return newPosition;
    }

    Float arrMedian(Float[] arr){
        return arr[arr.length%2==1?arr.length/2:(arr.length/2+arr.length/2+1)/2];
    }

    @Override
    public String toString() {
        return " Medians algorythm";
    }
}
