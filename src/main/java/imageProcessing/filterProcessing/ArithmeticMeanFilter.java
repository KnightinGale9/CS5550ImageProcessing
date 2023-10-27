package imageProcessing.filterProcessing;

import java.util.Arrays;

public class ArithmeticMeanFilter implements Filter {
    private int maskSize;
    public ArithmeticMeanFilter(int m){
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
        int sum=0;
        for(int i=0;i<m.length;i++)
        {
            for(int j =0;j<m[0].length;j++)
            {
                sum+=m[i][j];
            }
        }
        return sum/(maskSize*maskSize);
    }
}
