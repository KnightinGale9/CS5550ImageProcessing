package imageProcessing.filterProcessing;

public class Sobel implements Filter{
    private int[][] sobelHorizontal;
    private int[][] sobelVerticle;
    private int maskSize;
    public Sobel(int n){
        maskSize=n;
        sobelHorizontal= new int[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        sobelVerticle = new int[][]{{1,2,1},{0,0,0},{-1,-2,-1}};
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
        int horVal=0;
        int verVal=0;
        int sum=0;
        for(int i=0;i<mask.length;i++)
        {
            for(int j=0;j<mask[0].length;j++)
            {
                horVal+= mask[i][j]*sobelHorizontal[i][j];
                verVal+= mask[i][j]*sobelVerticle[i][j];
            }
        }
        sum =(int) Math.sqrt((horVal*horVal)+(verVal*verVal));
        if(sum<0)
        {
            sum=0;
        }
        if(sum>255)
        {
            sum=255;
        }
        return sum;
    }
}
