package imageProcessing;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class BitPlane {
    private String binary;
    private String bitPlane="";
    private HashMap<Integer,Integer> values=new HashMap();
    public void bitPlaneCreation(int[][] original, int[][]transform, Set<Integer> bitLevel)
    {
        for(int i=0;i<original.length;i++)
        {
            for(int j=0;j< original[i].length;j++)
            {
                binary = String.format("%8s", Integer.toBinaryString(original[i][j])).replace(' ', '0');
                System.out.print(binary);
                for(int b=binary.length()-1;b>=0;b--)
                {
                    if(bitLevel.contains(b))
                    {
                        bitPlane+=0;
                    }
                    else
                    {
                        bitPlane+=binary.charAt(binary.length()-1-b);
                    }
                }
                transform[i][j] = Integer.parseInt(bitPlane, 2);
                System.out.println(":" + bitPlane);
                bitPlane="";
            }
        }
    }
}
