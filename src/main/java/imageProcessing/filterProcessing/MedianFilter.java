package imageProcessing.filterProcessing;

import java.util.Arrays;

public class MedianFilter implements Filter {
    private int[] mask;
    public MedianFilter(int m){
        mask = new int[m*m];
    }

    @Override
    public int[][] getMask() {
        return new int[0][0];
    }
    @Override
    public int getPixel(int i, int j) {
        return 0;
    }
    @Override
    public int calculate(int[][] m){
        for(int i=0;i<m.length;i++)
        {
            for(int j =0;j<m.length;j++)
            {
                mask[i*m.length+j]=m[i][j];
            }
        }
        Arrays.sort(mask);
        return mask[mask.length/2];
    }
}
