package imageProcessing.filterProcessing;

public class BoxFilter implements Filter{
    private int[][] mask;
    private int size;
    public BoxFilter(int m){
        mask = new int[m][m];
        size = m*m;
        for(int i=0;i<mask.length;i++)
        {
            for(int j=0;j<mask.length;j++)
            {
                mask[i][j]=1;
            }
        }
    }

    @Override
    public int[][] getMask() {
        return mask;
    }
    @Override
    public int getPixel(int i, int j) {
        return mask[i][j];
    }
    @Override
    public int calculate(int[][] m){
        int cal=0;
        for(int i=0;i<mask.length;i++)
        {
            for(int j =0;j<mask.length;j++)
            {
                cal+=m[i][j]*mask[i][j];
            }
        }
        return cal/size;
    }
}
