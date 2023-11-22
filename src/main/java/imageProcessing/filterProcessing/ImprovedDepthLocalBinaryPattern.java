package imageProcessing.filterProcessing;

import java.util.Arrays;

public class ImprovedDepthLocalBinaryPattern implements Filter{
    private int maskSize;
    private String type;
    public ImprovedDepthLocalBinaryPattern(int m,String type){
        maskSize = m;
        this.type=type;
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
        int t=0;
        int sum=0;
        int max=Integer.MIN_VALUE;

        int k=0;
        for(int i=0;i<mask.length;i++)
        {
            for(int j=0;j<mask.length;j++)
            {
                if(!(i==mask.length/2 &&j==mask.length/2)) {
                    if (mask[i][j] > max) {
                        max = mask[i][j];
                    }
                    k = (mask[i][j] - mask[mask.length / 2][mask.length / 2]);

                    t += ((k>=0)?1:0);
                }
            }
        }
        if(t>0 &&t<(mask.length*mask.length-1)) {
            for (int i = 0; i < mask.length; i++) {
                for (int j = 0; j < mask.length; j++) {
                    if (!(i == mask.length / 2 && j == mask.length / 2)) {
                        sum += (calculateX(mask[i][j] - mask[mask.length / 2][mask.length / 2], mask[mask.length / 2][mask.length / 2], max));
                    }
                }
            }
            if(sum-t==0) {
                System.out.println(Arrays.toString(mask[0]) + " sum:" + sum);
                System.out.println(Arrays.toString(mask[1]) + " t:" + t);
                System.out.println(Arrays.toString(mask[2]));
            }
            if (type.equals("<")) {
                if (sum - t < 0) {
                    if (sum - t == 0) {
                        return 255;
                    }
                    return Math.abs(sum - t);
                } else {
                    return 0;
                }
            }
            if (type.equals("=")) {
                if (sum - t == 0) {
                    if (sum - t == 0) {
                        return 255;
                    }
                    return Math.abs(sum - t);
                } else {
                    return 0;
                }
            }
            if (type.equals("<=")) {
                if (sum - t <= 0) {
                    if (sum - t == 0) {
                        return 255;
                    }
                    return Math.abs(sum - t);
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }
    private int calculateX(int pixel,int center,int max)
    {
        if(pixel>=(max-center))
        {
            return 1;
        }
        else{
            return 0;
        }
    }
}
