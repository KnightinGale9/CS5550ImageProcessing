package imageProcessing.filterProcessing;

public class HarmonicMeanFilter implements Filter {
    private int maskSize;
    public HarmonicMeanFilter(int m){
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
        double sum=0.0;
        for(int i=0;i<m.length;i++)
        {
            for(int j =0;j<m.length;j++)
            {
                sum += 1 /(double) m[i][j];
            }
        }
        return (int) ((maskSize*maskSize)/sum);
    }
}
