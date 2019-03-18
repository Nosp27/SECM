public class Task2 extends MatStatTask{
    private static final double z1 = 7724;
    private static double lastRand = z1;
    private static boolean generated = false;

    public static void main(String[] args) {
        double[] X = X(100);

        for(int i = 0; i < 10; ++i){
            System.out.print(X[i] + " ");
        }

        System.out.println();
        drawGist(X, 10, 20);
    }

    public static double[] X(int num){
        double[] d = new double[num];

        for(int i = 0; i < num; ++i){
            d[i] = newRand();
        }

        return d;
    }

    public static double newRand(){
        if(!generated){
            generated = true;
            return z1 / 10000;
        }

        lastRand = Math.floor(Math.pow(lastRand + 17, 2.2) / 100) % 10000;

        return lastRand / 10000;
    }
}
