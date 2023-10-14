package imageProcessing.filterProcessing;

public class HighBoostFilter {
    private int boost;
    public HighBoostFilter(int a){
        boost=a;
    }
    public void highBoost(int[][]original,int[][] smooth,int[][] transform)
    {
        for(int i=0;i<smooth.length;i++){
            for(int j=0;j<smooth[0].length;j++){
                transform[i][j]=original[i][j]+boost*(original[i][j]-smooth[i][j]);
            }
        }
    }


}
