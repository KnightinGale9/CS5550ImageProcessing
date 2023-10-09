package imageProcessing.filterProcessing;

import java.util.Arrays;

public class SharpeningFilter implements Filter {
    private int[][] mask;
    public SharpeningFilter(int m, int adj, int sign){
        mask = new int[m][m];
        if(adj==4)
        {
            Arrays.fill(mask[m/2],sign);
            for(int i=0;i< mask.length;i++)
            {
                mask[i][m/2]=sign;
            }
            mask[m/2][m/2]=(sign*m*2);
            mask[m/2][m/2]= sign==-1?-1*(mask[m/2][m/2]+2):-1*(mask[m/2][m/2]-2);
        }
        else if(adj==8)
        {
            for(int i=0;i<m;i++) {
                Arrays.fill(mask[i], sign);
            }
            mask[m/2][m/2]=-1*sign*(m*m-1);
        }
        System.out.println(Arrays.toString(mask[m/2]));
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
        cal = (m[m.length/2][m.length/2]>0)?cal*-1:cal;
        return (m[m.length/2][m.length/2]+cal>0)?m[m.length/2][m.length/2]+cal:0;
//        return m[m.length/2][m.length/2]+-1*cal;
    }

}
