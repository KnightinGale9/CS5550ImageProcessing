package imageProcessing.filterProcessing;

public class CountraHarmonicMeanFilter implements Filter {
    private int qValue;
    private int maskSize;
    public CountraHarmonicMeanFilter(int q,int m){
        qValue = q;
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
        double top=0.0;
        double bot=0.0;
        for(int i=0;i<m.length;i++)
        {
            for(int j =0;j<m[0].length;j++)
            {
                top += Math.pow(m[i][j], qValue + 1);
                bot += Math.pow(m[i][j], qValue);

            }
        }

        return (int) (top/bot);
    }
}
