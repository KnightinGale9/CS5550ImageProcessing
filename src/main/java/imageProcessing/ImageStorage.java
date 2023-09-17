package imageProcessing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;

public class ImageStorage {
    private File originalImage;
    private BufferedImage originalIMG;
    private int[][] originalArray;
    private int[][] temporary;
    private int[][] transformArray;

    public void setImageStorage(File path) {
        try {
            originalImage = path;
            originalIMG = ImageIO.read(originalImage);
            originalArray = new int[originalIMG.getWidth()][originalIMG.getHeight()];
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    public void setImageStorage(BufferedImage img) {
        originalImage = null;
        originalIMG = img;
        originalArray = new int[originalIMG.getWidth()][originalIMG.getHeight()];
    }
    public void setTransformArrayAsOriginalArray()
    {
        originalArray=new int[transformArray.length][transformArray.length];
        for(int i=0;i< transformArray.length;i++){
            originalArray[i]=transformArray[i].clone();
        }
    }
    //ImageIO built method but the gray values are not taken correctly.
    // Therfore using a file from github which retrives more correct gray values
//    public void setOriginalArrayBufferedImage()
//    {
//        for (int i = 0; i < originalArray.length; i++) {
//            for (int j = 0; j < originalArray[i].length; j++) {
//                originalArray[i][j] = originalIMG.getRGB(j, i) & 0xFF;
//            }
//        }
//    }
    public void setOriginalArray(int[][] arr) {
        originalArray=new int[arr.length][arr.length];
        for(int i=0;i< arr.length;i++){
            originalArray[i]=arr[i].clone();
        }
    }
    public void setOriginalArray(){
        try {
            GrayPixelReader pixel = new GrayPixelReader(originalImage);
            originalArray=pixel.convertImageToArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public BufferedImage getOriginalBufferedImage()
    {
        return originalIMG;
    }
    public void setTransformArray(int width, int height)
    {
        transformArray = new int[width][height];
    }
    public int[][] getTransformArray()
    {
        return transformArray;
    }
    public int[][] getOriginalArray()
    {
        return originalArray;
    }
    public int getOriginalArrayValue(int i,int j)
    {
        return originalArray[i][j];
    }
    public void createTransformImage(String filename)
    {
        try{
            File f = new File(String.format("%s.png",filename));
            ImageIO.write(getImageFromArray(), "png", f);
        }catch(IOException e){
            System.out.println(e);
        }
    }
    public BufferedImage getImageFromArray() {
        int width=getTransformArray().length;
        int height=getTransformArray()[0].length;
        int[][] pixelArray=getTransformArray();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        // Get the array of integers that represents the image
        byte[] pixels = new byte[width * height];
        for (int i = 0; i < pixelArray.length; i++) {
            for(int j=0;j<pixelArray[0].length;j++){
                pixels[i*pixelArray.length+j] = (byte)pixelArray[i][j];
            }
        }
        // Set the pixels of the image
        image.getRaster().setDataElements(0, 0, image.getWidth(), image.getHeight(), pixels);
        return image;
    }
    public void changeBitLevel(int oldLevel, int newLevel)
    {
        setTransformArray(originalArray.length,originalArray[0].length);
        GrayLevelResolution grayLevel = new GrayLevelResolution();
        grayLevel.changeBitLevel(oldLevel,newLevel,getOriginalArray(),getTransformArray());
    }
    public void subsampling(int num)
    {
        VaryingSpacialResolution sub = new VaryingSpacialResolution();
        setTransformArray((int) (originalArray.length/Math.pow(2,num)), (int) (originalArray.length/Math.pow(2,num)));
        sub.subsampling(num,getOriginalArray(),getTransformArray());
    }
    public void subsampling(int width, int height)
    {
        VaryingSpacialResolution sub = new VaryingSpacialResolution();
        setTransformArray(width,height);
        sub.subsampling(width,height,getOriginalArray(),getTransformArray());
    }
    public void upsampling(int num)
    {
        VaryingSpacialResolution up = new VaryingSpacialResolution();
        setTransformArray(originalArray.length * 2, originalArray[0].length * 2);
        up.setupTemp(originalArray);
        for(int i=0;i<num;i++) {
            up.upsampling(getTransformArray());
            up.nearestNeightbor(getTransformArray());
            up.setupTemp(getTransformArray());
            setTransformArray(transformArray.length*2, transformArray[0].length*2);
        }

    }
    public void upsampling(int width,int height)
    {
        VaryingSpacialResolution up = new VaryingSpacialResolution();
        setTransformArray(width,height);
        up.upsampling(originalArray,transformArray);
    }
    public void nearestNeighborUpsampling()
    {
        VaryingSpacialResolution up = new VaryingSpacialResolution();
        up.nearestNeightbor(transformArray);
    }
    public void linearInterpolationUpsampling()
    {
        VaryingSpacialResolution up = new VaryingSpacialResolution();
        up.linearInterpolation(transformArray);
    }
    public void bilinearInterpolationUpsampling()
    {
        VaryingSpacialResolution up = new VaryingSpacialResolution();
        up.bilinearInterpolation(transformArray);
    }
}
