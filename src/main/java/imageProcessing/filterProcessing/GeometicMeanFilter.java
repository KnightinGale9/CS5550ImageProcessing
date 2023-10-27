package imageProcessing.filterProcessing;

public class GeometicMeanFilter implements Filter {
    private int maskSize;
    public GeometicMeanFilter(int m){
        maskSize = m;
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
        float sum=0;
        for(int i=0;i<m.length;i++)
        {
            for(int j =0;j<m.length;j++)
            {
//                sum *= m[i][j];

                sum += (float)Math.log(m[i][j]);
            }
        }
        sum /= maskSize*maskSize;
        return (int) Math.exp(sum);
    }
}
