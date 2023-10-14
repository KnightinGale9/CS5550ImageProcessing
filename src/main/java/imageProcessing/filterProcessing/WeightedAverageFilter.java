package imageProcessing.filterProcessing;

import java.lang.reflect.Array;
import java.util.Arrays;

//https://www.researchgate.net/figure/Discrete-approximation-of-the-Gaussian-kernels-3x3-5x5-7x7_fig2_325768087
public class WeightedAverageFilter implements Filter{
    private int[][] mask;
    private int size;
    public WeightedAverageFilter(int m,int b){
        mask = new int[m][m];
        size=0;
        for(int i=0;i<m/2+1;i++)
        {
            for(int j=0;j<m/2+1;j++)
            {
                mask[i][j]= (int) Math.pow(b,i+j);
                mask[i][m-1-j]=(int) Math.pow(b,i+j);
                mask[m-1-i][j]= (int) Math.pow(b,i+j);
                mask[m-1-i][m-1-j]=(int) Math.pow(b,i+j);
            }
        }
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<m;j++)
            {
                size += mask[i][j];
            }
        }
        for (int[] mm:mask) {
            System.out.println(Arrays.toString(mm));
        }
    }

    @Override
    public int[][] getMask() {
        return mask;
    }
    @Override
    public int getPixel(int i, int j) {
        return mask[i][j];
    }
    @Override
    public int calculate(int[][] m){
        int cal=0;
        for(int i=0;i<mask.length;i++)
        {
            for(int j =0;j<mask.length;j++)
            {
                cal+=m[i][j]*mask[i][j];
            }
        }

        return cal/size;
    }
}
