package imageProcessing.filterProcessing;

import java.util.Arrays;

public class AlphaTrimmedMeanFIlter implements Filter {
    private int[] mask;
    private int maskSize;
    private int dValue;
    public AlphaTrimmedMeanFIlter(int m,int d){
        mask = new int[m*m];
        dValue=d;
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
            for(int j =0;j<m.length;j++)
            {
                mask[i*m.length+j]=m[i][j];
            }
        }
        Arrays.sort(mask);
        int sum=0;
        for(int i=dValue/2;i<mask.length-dValue;i++)
        {
            sum +=mask[i];
        }
        return sum/(mask.length-dValue);
    }
}
