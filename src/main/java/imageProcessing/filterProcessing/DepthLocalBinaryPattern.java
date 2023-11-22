package imageProcessing.filterProcessing;

import java.util.Arrays;

public class DepthLocalBinaryPattern implements Filter {
    private int maskSize;
    public DepthLocalBinaryPattern(int m){
        maskSize = m;
    }
    @Override
    public int[][] getMask() {
        return new int[0][];
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
    public int calculate(int[][] mask) {
        int sum=0;
        int max=Integer.MIN_VALUE;
        double avg=0;
        for(int i=0;i<mask.length;i++)
        {
            for(int j=0;j<mask.length;j++)
            {
//                if(!(i==mask.length/2 &&j==mask.length/2)) {
                    if (mask[i][j] > max) {
                        max = mask[i][j];
                    }
                    avg += mask[i][j];
//                }
            }
        }
        avg/=((mask.length*mask[0].length));
        if(Math.abs(avg-mask[mask.length/2][mask.length/2])>=1.5){
            for(int i=0;i<mask.length;i++)
            {
                for(int j=0;j<mask.length;j++)
                {
                    if(!(i==mask.length/2 &&j==mask.length/2)) {
//                        sum += (calculateK(mask[i][j] - mask[mask.length / 2][mask.length / 2], avg, max) * (mask[i][j] - mask[mask.length / 2][mask.length / 2]));
                        sum+= (calculateK(mask[i][j]-mask[mask.length / 2][mask.length / 2],avg,max));
                    }
                }
            }
            if(sum>=1)
            {
                    System.out.println(Arrays.toString(mask[0]));
                    System.out.println(Arrays.toString(mask[1]));
                    System.out.println(Arrays.toString(mask[2]));
                    System.out.println();

                return 255;
//                return (sum>255)?255:sum;
            }
            else{
                return 0;
            }
        }
        else{
            return 0;
        }
    }
    private int calculateK(int pixel,double avg,int max)
    {
        if(pixel>(max-avg))
        {
            return 1;
        }
        else{
            return 0;
        }
    }
}
