package imageProcessing.filterProcessing;

import java.util.Arrays;

public class MinFilter implements Filter {
    private int[] mask;
    private int maskSize;
    public MinFilter(int m){
        mask = new int[m*m];
        maskSize=m;
    }

    @Override
    public int[][] getMask() {
        return new int[0][0];
    }
    @Override
    public int getMaskSize() {
        return maskSize;
    }
    @Override
    public int getPixel(int i, int j) {
        return 0;
    }
    @Override
    public int calculate(int[][] m){
        for(int i=0;i<m.length;i++)
        {
            for(int j =0;j<m[0].length;j++)
            {
                mask[i*m.length+j]=m[i][j];
            }
        }
        Arrays.sort(mask);
        return mask[0];
    }
}
