package tasks;

import correlation.MatStatTask;

public class Task2 extends MatStatTask {
    private static final double z1 = 7724;
    private static double lastRand = z1;
    private static boolean generated = false;

    private static final double ALPHA = 0.05;

    public static void main(String[] args) {
        double[] X = X(100);

        for (int i = 0; i < 10; ++i) {
            System.out.print(X[i] + " ");
        }

        System.out.println();
        drawGist(X, 10, 20);

        System.out.println("KS criteria for 100 numbers " + (criteriaDecline(X) ? "declined" : "accepted"));
        System.out.println("________________________________________");
        X = X(10000);
        drawGist(X, 20,20);
        System.out.println("KS criteria for 10000 numbers " + (criteriaDecline(X) ? "declined" : "accepted"));
    }

    public static double[] X(int num) {
        double[] d = new double[num];

        for (int i = 0; i < num; ++i) {
            d[i] = newRand();
        }

        return d;
    }

    public static double newRand() {
        if (!generated) {
            generated = true;
            return z1 / 10000;
        }

        lastRand = Math.floor(Math.pow(lastRand + 17, 2.2) / 100) % 10000;

        return lastRand / 10000;
    }

    public static boolean criteriaDecline(double[] X) {
        double maxDev = 0;
        for (double i = 0; i < 1; i += 0.001) {
            double d = Math.abs(e(X, i) - i);
            if (d > maxDev)
                maxDev = d;
        }

        double KS = Math.sqrt(X.length) * maxDev;
        double KSA = Math.sqrt(-0.5 * Math.log(ALPHA * 0.5));

        System.out.println("max: " + maxDev);
        System.out.println("KS: " + KS);
        System.out.println("KSA: " + KSA);

        return (KS > KSA);
    }

    public static double e(double[] X, double x) {
        int res = 0;
        for (int i = 0; i < X.length; ++i)
            if (X[i] < x)
                res++;
        return 1.0 * res / X.length;
    }
}
