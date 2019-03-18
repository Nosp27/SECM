import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Task1 extends MatStatTask {

    private static final double T = 2.2621571627409915d;

    public static void main(String[] args) {
        double[] D = X(1000);

        drawGist(D, 10, 20);

        D = X(10);
        double avg = avg(D);
        double disp = disp(D, avg);
        System.out.println(Arrays.toString(confidenceIntervalStudent(D, avg, disp)));

        System.out.println("10: " + rate(10));
        System.out.println("15: " + rate(15));
        System.out.println("20: " + rate(20));
        System.out.println("25: " + rate(25));
        System.out.println("30: " + rate(30));
    }

    public static double q(double x){
        return x <= 0.5 ? Math.log(2* x) : - Math.log(2 - 2*x);
    }

    public static double[] X(int num){
        double[] d = new double[num];

        for(int i = 0; i < num; ++i){
            d[i] = q(x());
        }

        return d;
    }



    public static double[] confidenceIntervalStudent(double X[], double avg, double disp){
        return new double[]{avg - T* disp / Math.sqrt(X.length), avg + T* disp / Math.sqrt(X.length)};
    }

    public static double rate(int num){
        double[] D;

        double result = 0;

        for(int i = 0; i < 1000; ++i){
            D = X(num);
            double avg = avg(D);
            double[] interval = confidenceIntervalStudent(D, avg, disp(D, avg));
            if(0 > interval[0] && 0 < interval[1])
                result++;
        }

        return result / 1000;
    }
}