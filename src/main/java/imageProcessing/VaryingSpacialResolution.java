package imageProcessing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VaryingSpacialResolution {
    private int[][] temp;
    public void subsampling(int num,int[][] originalArray, int[][] transformArray)
    {
        for(int row =0,i=0;row< transformArray.length;row++,i+=(int)Math.pow(2,num))
        {
            for (int col = 0, j = 0; col < transformArray[0].length; col++, j += (int)Math.pow(2,num)) {
                transformArray[row][col] = originalArray[i][j];
            }
        }
    }
    public void subsampling(int width, int height,int[][] original, int[][] transformArray) {
        for (int row = 0, i = 0; row < width; row++, i += 2) {
            for (int col = 0, j = 0; col < height; col++, j += 2) {
                transformArray[row][col] = original[i][j];
            }
        }
    }
    public void setupTemp(int[][] original)
    {
        temp=new int[original.length][original[0].length];
        for(int i=0;i< original.length;i++){
            temp[i]=original[i].clone();
        }
    }
    public void upsampling(int[][] transform)
    {
        for(int row=0,i=0;row<temp.length;row++,i+=2)
        {
            for(int col=0,j=0;col<temp[row].length;col++,j+=2)
            {
                transform[i][j]=temp[row][col];
            }
        }
    }
    public void upsampling(int[][] original, int[][] transform)
    {
        for(int row=0,i=0;row<original.length;row++,i+=2)
        {
            for(int col=0,j=0;col<original[row].length;col++,j+=2)
            {
                transform[i][j]=original[row][col];
            }
        }
    }
    public void nearestNeightbor(int[][]transform)
    {
        for(int i=0;i<transform.length;i+=2)
        {
            for(int j=1;j<transform[i].length;j+=2)
            {
                transform[i][j]=transform[i][j-1];
            }
        }
        for(int i=1;i<transform.length;i+=2)
        {
            for(int j=0;j<transform[i].length;j++)
            {
                transform[i][j]=transform[i-1][j];
            }
        }
    }
    public void linearInterpolation(int[][] transform)
    {
        temp = Arrays.copyOf(transform,transform.length+1);
        for(int k=0;k<transform.length;k++)
        {
            temp[k] = Arrays.copyOf(transform[k],transform.length+1);
        }
        temp[temp.length-1] = new int[transform.length+1];

        for(int k=0;k<temp.length;k+=2)
        {
            temp[k][temp[k].length-1]=temp[k][temp[k].length-3];
        }
        for(int k=0;k<temp.length;k+=2)
        {
            temp[temp.length-1][k]=temp[temp.length-3][k];
        }
        System.out.println("clear");
        for(int i=0;i<temp.length-1;i+=2)
        {
            for(int j=1;j<temp[i].length-1;j+=2)
            {
                transform[i][j]=(temp[i][j-1]+temp[i][j+1])/2;
                temp[i][j]=transform[i][j];
            }
        }
        for(int j=1;j<temp[temp.length-1].length-1;j+=2)
        {
            temp[temp.length-1][j]=(temp[temp.length-1][j-1]+temp[temp.length-1][j+1])/2;
        }
        for(int i=1;i<temp.length-1;i+=2)
        {
            for(int j=0;j<temp[i].length-1;j++)
            {
                transform[i][j]=(temp[i-1][j]+temp[i+1][j])/2;
            }
        }
    }
    public void bilinearInterpolation(int[][] transform)
    {
        temp = new int[transform.length+2][transform.length+2];
        for(int i =1;i< temp.length-1;i+=2)
        {
            System.arraycopy(transform[i-1],0,temp[i],1,transform[i-1].length);
        }

        for(int k=1;k<temp.length-1;k+=2)
        {
            temp[k][0]=temp[k][1];
            temp[k][temp[k].length-1]=temp[k][temp[k].length-3];
        }
        for(int k=1;k<temp.length-1;k+=2)
        {
            temp[0][k] = temp[1][k];
            temp[temp.length-1][k]=temp[temp.length-3][k];
        }
        temp[0][0]=temp[0][1];
        temp[0][temp.length-1]=temp[1][temp.length-1];
        temp[temp.length-1][0]=temp[temp.length-1][1];
        temp[temp.length-1][temp.length-1]=temp[temp.length-3][temp.length-1];
        for(int j=2;j<temp[temp.length-1].length-1;j+=2)
        {
            temp[0][j]=(temp[0][j-1]+temp[0][j+1])/2;
            temp[temp.length-1][j]=(temp[temp.length-1][j-1]+temp[temp.length-1][j+1])/2;
            temp[j][0]=(temp[j-1][0]+temp[j+1][0])/2;
            temp[j][temp.length-1]=(temp[j-1][temp.length-1]+temp[j+1][temp.length-1])/2;
        }
        System.out.println("clear");
//        createTransformImage("test");
        //cross
        for(int i=2;i<temp.length-1;i+=2)
        {
            for(int j=2;j<temp[i].length-1;j+=2)
            {
                temp[i][j]=(temp[i-1][j-1]+temp[i-1][j+1]+temp[i-1][j+1]+temp[i+1][j+1])/4;
//                temp[i][j]=transform[i][j];
            }
        }
//        createTransformImage("cross");
        //plus
//
        for(int i=1;i<temp.length-1;i+=2)
        {
            for(int j=2;j<temp[i].length-1;j+=2)
            {
                temp[i][j]=(temp[i][j-1]+temp[i][j+1]+temp[i-1][j]+temp[i+1][j])/4;
            }
        }
        for(int i=2;i<temp.length-1;i+=2)
        {
            for(int j=1;j<temp[i].length-1;j+=2)
            {
                temp[i][j]=(temp[i][j-1]+temp[i][j+1]+temp[i-1][j]+temp[i+1][j])/4;
            }
        }
//        createTransformImage("plus");
        for(int i=0;i<transform.length;i++)
        {
            for(int j=0;j<transform.length;j++)
            {
                transform[i][j]=temp[i+1][j+1];
            }
        }
//        System.out.println(Arrays.compare(temp[1],1,temp[1].length-1,transform[0],0,transform.length));

    }
}
