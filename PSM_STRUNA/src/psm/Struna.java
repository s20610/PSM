package psm;

public class Struna {

    private double[] y;
    private double[] v;
    private double[] a;

    private final double dx = Math.PI / 20;
    private final double dt = 0.02;

    //animacja dla t
    public Struna()
    {
        y = new double[40];
        v = new double[40];
        a = new double[40];

        //Wypelnianie y sinusem
        for(int i = 0; i < 40; i++) {
            y[i] = Math.sin(i * dx);

            if(i > 0 && i < 40-1)
                a[i] = getA(i-1, i, i+1);

            if(i > 0)
                v[i] = getV(i);
        }
    }

    public void computeStep() {

        for(int x = 1; x < 39; x++)
        {
            y[x] = getY(x);
            v[x] = getV(x);
            a[x] = getA(x-1, x, x+1);
        }
    }

    public double[] getData() {
        return y;
    }

    double getA(int x_left, int x, int x_next)
    {
        return (y[x_next] - 2*y[x] + y[x_left]) / (dx*dx);
    }
    double getV(int old_x){ //x
        return v[old_x] + a[old_x]*dt;
    }
    double getY(int old_x) {
        return y[old_x]  + v[old_x]*dt;
    }
}

