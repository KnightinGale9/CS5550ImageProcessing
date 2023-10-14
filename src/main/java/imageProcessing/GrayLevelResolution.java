package imageProcessing;

import java.util.HashSet;

public class GrayLevelResolution {
    public void changeBitLevel(int oldLevel, int newLevel,int[][] originalImage, int[][] transformArray)
    {
        HashSet<Integer> gray = new HashSet<>();
        if(oldLevel==newLevel)
        {
            for(int i=0;i< transformArray.length;i++){
                transformArray[i]=originalImage[i].clone();
            }
        }
            for(int i=0;i<transformArray.length;i++)
            {
                for(int j=0;j<transformArray[i].length;j++)
                {
                    transformArray[i][j] = originalImage[i][j]/(int)(Math.pow(2,oldLevel-newLevel));
                    gray.add(transformArray[i][j]);
                }
            }

        System.out.println(gray);
    }
}
