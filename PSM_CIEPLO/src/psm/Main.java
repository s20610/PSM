package psm;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    static double[][] a;
    static double[][] macierz;
    static double[] b;

    static int n = 42;

    public static void main(String[] args) throws IOException {
        a = new double[n][n];

        for (int j=0; j<n; j++) {
            a[j][0]=150;
            a[j][n-1]=50;
            a[0][j]=100;
            a[n-1][j]=200;
        }

        int size=n-2;
        int m = size*size;
        macierz = new double[m][m];
        b = new double[m];

        int row_nr = 0;

        for (int j=1; j<=size; j++) {
            for (int i=1; i<=size; i++) {
                b[row_nr]=0;
                if (Brzeg(j-1,i))
                    b[row_nr]-=a[j-1][i];
                else
                    macierz[row_nr][size*(j-2)+(i-1)]=1;
                if (Brzeg(j+1,i))
                    b[row_nr]-=a[j+1][i];
                else
                    macierz[row_nr][size*(j)+(i-1)]=1;
                if (Brzeg(j,i-1))
                    b[row_nr]-=a[j][i-1];
                else
                    macierz[row_nr][size*(j-1)+(i-2)]=1;
                if (Brzeg(j,i+1))
                    b[row_nr]-=a[j][i+1];
                else
                    macierz[row_nr][size*(j-1)+(i)]=1;
                macierz[row_nr][size*(j-1)+(i-1)]=-4;
                row_nr++;
            }
        }
        FileWriter myWriter = new FileWriter("macierz.csv");
        for (int j=0; j<m; j++) {
            for (int i=0; i<m; i++) {
                String s = String.format("%2.0f"+",", macierz[j][i]);
                myWriter.write(s);
            }
            myWriter.write(""+b[j]);
            myWriter.write("\n");
        }
    }
    static boolean Brzeg(int j, int i) {
        return (j==0 || j==n-1 || i==0 || i==n-1);
    }
}