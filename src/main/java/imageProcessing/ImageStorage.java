package imageProcessing;

import imageProcessing.filterProcessing.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.util.*;

public class ImageStorage {
    private File originalImage;
    private BufferedImage originalIMG;
    private int[][] originalArray;
    private int[][] transformArray;
    private BufferedImage transformIMG;

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
        originalArray=new int[transformArray.length][transformArray[0].length];
        for(int i=0;i< transformArray.length;i++){
            originalArray[i]=transformArray[i].clone();
        }
    }
    public void setOriginalArray(int[][] arr) {
        originalArray=new int[arr.length][arr[0].length];
        for(int i=0;i< arr.length;i++){
            originalArray[i]=arr[i].clone();
        }
    }
    public void setOriginalArray(){
        try {
            GrayPixelReader pixel = new GrayPixelReader(originalImage);
            pixel.conversionMethod(originalIMG,originalArray);
            System.out.println("debug");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public BufferedImage getOriginalBufferedImage()
    {
        return originalIMG;
    }
    public BufferedImage getTransformIMG(){return transformIMG;}
    public void setTransformArray(int height, int width)
    {
        transformArray = new int[height][width];
    }
    public int[][] getTransformArray()
    {
        return transformArray;
    }
    public int[][] getOriginalArray()
    {
        return originalArray;
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
        int width=transformArray.length;
        int height=transformArray[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        // Get the array of integers that represents the image
        byte[] pixels = new byte[width * height];
        for (int i = 0; i < transformArray.length; i++) {
            for(int j=0;j<transformArray[0].length;j++){
                pixels[j*transformArray.length+i] = (byte)transformArray[i][j];
            }
        }
        // Set the pixels of the image
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        return image;
    }
    public void getImageFromArray(ArrayList<Integer> pass){
        int width=originalArray.length;
        int height=originalArray[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        // Get the array of integers that represents the image
        byte[] pixels = new byte[width * height];
        for (int i = 0; i < pass.size(); i++) {
            pixels[i]=pass.get(i).byteValue();
        }
        // Set the pixels of the image
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        transformIMG = image;
    }
    public void changeBitLevel(int oldLevel, int newLevel)
    {
        setTransformArray(originalArray.length,originalArray[0].length);
        GrayLevelResolution grayLevel = new GrayLevelResolution();
        grayLevel.changeBitLevel(oldLevel,newLevel,getOriginalArray(),getTransformArray());
        linearscale();
    }
    public void subsampling(int num)
    {
        VaryingSpacialResolution sub = new VaryingSpacialResolution();
        setTransformArray((int) (originalArray.length/Math.pow(2,num)), (int) (originalArray[0].length/Math.pow(2,num)));
        sub.subsampling(num,getOriginalArray(),getTransformArray());
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

    public void bitPlaneImage(Set<Integer> bit)
    {
        BitPlane bitplane = new BitPlane();
        setTransformArray(originalArray.length,originalArray[0].length);
        bitplane.bitPlaneCreation(originalArray,transformArray,bit);
        this.linearscale();
    }
    public void globalHistogramEqualization()
    {
        HistogramEqualization HE = new HistogramEqualization();
        setTransformArray(originalArray.length,originalArray[0].length);
        HE.globalEqualization(originalArray,transformArray);
    }
    public void localHistogramEqualization(int mask)
    {
        HistogramEqualization HE = new HistogramEqualization();
        setTransformArray(originalArray.length,originalArray[0].length);
        HE.localEqualization(originalArray,transformArray,mask);
    }
    //
    public void filter(Filter type)
    {
        FilterMask filter = new FilterMask(type);
        setTransformArray(originalArray.length,originalArray[0].length);
        filter.runFilter(originalArray,transformArray);
        this.pixelOverall();
    }
    public void sharpeningFilter(int m,int adj,int sign)
    {
        FilterMask filter = new FilterMask(new SharpeningFilter(m,adj,sign));
        setTransformArray(originalArray.length,originalArray[0].length);
        filter.runFilter(originalArray,transformArray);
        this.outlierScale();
        this.pixelOverall();
    }
    public void highBoostFiter(Filter type,int boost)
    {
        FilterMask filter = new FilterMask(type);
        setTransformArray(originalArray.length,originalArray[0].length);
        filter.runFilter(originalArray,transformArray);
        HighBoostFilter highBoost = new HighBoostFilter(boost);
        highBoost.highBoost(originalArray,transformArray,transformArray);
        this.outlierScale();
        this.pixelOverall();
    }
    public String runLengthCompression()
    {
        RunLengthCompression rle = new RunLengthCompression();
        DataBuffer data = originalIMG.getRaster().getDataBuffer();
        int[] imageRaster = new int[data.getSize()];
        for(int i=0;i<data.getSize();i++)
        {
            imageRaster[i] = data.getElem(i);
        }

        long encodeStart = System.nanoTime();
        ArrayList<Integer> encode =rle.runLengthEncoding(imageRaster);
        long encodeEnd = System.nanoTime();
        long encodeDuration = encodeEnd - encodeStart;
        BufferedImage image = new BufferedImage(encode.size(), 1, BufferedImage.TYPE_BYTE_GRAY);
        // Get the array of integers that represents the image
        byte[] pixels = new byte[encode.size()];
        for (int i = 0; i < encode.size(); i++) {
            pixels[i]= encode.get(i).byteValue();
        }
        // Set the pixels of the image
        image.getRaster().setDataElements(0, 0, encode.size(), 1, pixels);
        File f = new File(String.format("%s.png","RLComprssion"));
        try {
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long decodeStart = System.nanoTime();
        ArrayList<Integer> decode = rle.runLengthDecoding(encode);
        long decodeEnd = System.nanoTime();
        long decodeDuration = decodeEnd - decodeStart;
        System.out.println("Encode Time: " + encodeDuration + "  Decode Time: " + decodeDuration);
        int sum=0;
        for(int i=0;i< imageRaster.length;i++)
        {
            sum+=Math.pow((imageRaster[i]- decode.get(i)),2);
        }
        System.out.println("MSE: " + sum/ imageRaster.length);
        System.out.println(imageRaster.length + " " + encode.size() +"=" + imageRaster.length/encode.size());
        getImageFromArray(decode);
        System.out.println("Encode Time: " + encodeDuration + " NanoSeconds / Decode Time: " + decodeDuration + " NanoSeconds / Compression Ratio: " + decode.size()/(double)encode.size() + " / Mean Square Error: " + sum/ imageRaster.length);

        return ("Encode Time: " + encodeDuration + " NanoSeconds / Decode Time: " + decodeDuration + " NanoSeconds / Compression Ratio: " + decode.size()/(double)encode.size() + " / Mean Square Error: " + sum/ imageRaster.length);

    }
    public String runLengthBitPlaneCompression()
    {
        RunLengthBitPlaneCompression rle = new RunLengthBitPlaneCompression();
        DataBuffer data = originalIMG.getRaster().getDataBuffer();
        BitPlane bit = new BitPlane();
        int[] imageRaster = new int[data.getSize()];
        for(int i=0;i<data.getSize();i++)
        {
            imageRaster[i] =  data.getElem(i);
        }
        ArrayList<Integer>[] encode= new ArrayList[8];
        int encodeLength=0;
        long encodeStart = System.nanoTime();
        for(int i =0;i<encode.length;i++){
            Set<Integer> num = new HashSet<>(Arrays.asList(0,1,2,3,4,5,6,7));
            num.remove(i);
            encode[i]=new ArrayList<>();
            rle.runLengthEncoding(bit.bitPlaneCreation(imageRaster,num),encode[i]);
            encodeLength+= encode[i].size();
        }
        long encodeEnd = System.nanoTime();
        long encodeDuration = encodeEnd - encodeStart;

        BufferedImage image = new BufferedImage(encodeLength, 1, BufferedImage.TYPE_BYTE_GRAY);
        // Get the array of integers that represents the image
        byte[] pixels = new byte[encodeLength];
        int temp =0;
        for (int i = 0; i < encode.length; i++) {
            for(int j=0;j< encode[i].size();j++)
            {
                pixels[temp++]=encode[i].get(j).byteValue();
            }
        }
        // Set the pixels of the image
        image.getRaster().setDataElements(0, 0, encodeLength, 1, pixels);
        File f = new File(String.format("%s.png","RLBComprssion"));
        try {
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long decodeStart = System.nanoTime();
        ArrayList<Integer> decode = rle.runLengthDecoding(encode);
        long decodeEnd = System.nanoTime();
        long decodeDuration = decodeEnd - decodeStart;
//        System.out.println("Encode Time: " + encodeDuration + "  Decode Time: " + decodeDuration);
//        System.out.println(imageRaster.length +" " +encodeLength);
        setTransformArray(originalArray.length,originalArray[0].length);
        int sum=0;
        for(int i=0;i<imageRaster.length;i++)
        {
            sum+=Math.pow(imageRaster[i]- decode.get(i),2);
        }
        getImageFromArray(decode);
        System.out.println("Encode Time: " + encodeDuration + " NanoSeconds / Decode Time: " + decodeDuration + " NanoSeconds / Compression Ratio: " + decode.size()/(double)encodeLength + " / Mean Square Error: " + sum/ imageRaster.length);
        return ("Encode Time: " + encodeDuration + " NanoSeconds / Decode Time: " + decodeDuration + " NanoSeconds / Compression Ratio: " + decode.size()/(double)encodeLength + " / Mean Square Error: " + sum/ imageRaster.length);

    }
    public String huffmanEncoding()
    {
        HuffmanCompression huff = new HuffmanCompression();
        DataBuffer data = originalIMG.getRaster().getDataBuffer();
        int[] imageRaster = new int[data.getSize()];
        for(int i=0;i<data.getSize();i++)
        {
            imageRaster[i] =  data.getElem(i);
        }
        long encodeStart = System.nanoTime();
        ArrayList<String> encoded = huff.huffmanEncoding(imageRaster);
        long encodeEnd = System.nanoTime();
        long encodeDuration = encodeEnd - encodeStart;

        BufferedImage image = new BufferedImage(encoded.size()+huff.getHuffmanStorage().size()+1, 1, BufferedImage.TYPE_BYTE_GRAY);
        // Get the array of integers that represents the image
        byte[] pixels = new byte[encoded.size()+huff.getHuffmanStorage().size()+1];

        for(int i=0;i<huff.getHuffmanStorage().size();i++)
        {
            for (Integer key: huff.getHuffmanStorage().keySet()) {

                pixels[key]= (byte) Integer.parseInt(huff.getHuffmanStorage().get(key), 2);
            }
        }
        pixels[256]=Byte.MIN_VALUE;

        for (int i = 0; i < encoded.size(); i++) {

            pixels[huff.getHuffmanStorage().size()+i]= (byte) Integer.parseInt(encoded.get(i), 2);
        }
        // Set the pixels of the image
        image.getRaster().setDataElements(0, 0, encoded.size()+huff.getHuffmanStorage().size()+1, 1, pixels);
        File f = new File(String.format("%s.png","HuffComprssion"));
        try {
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        long decodeStart = System.nanoTime();
        ArrayList<Integer> decode = huff.decodeHuffman(encoded);
        long decodeEnd = System.nanoTime();
        long decodeDuration = decodeEnd - decodeStart;
//        System.out.println("Encode Time: " + encodeDuration + "  Decode Time: " + decodeDuration);
        int sum=0;
        for(int i=0;i< imageRaster.length;i++)
        {
            sum+=Math.pow(((imageRaster[i])- decode.get(i)),2);
        }
        getImageFromArray(decode);
        System.out.println("Encode Time: " + encodeDuration + " NanoSeconds / Decode Time: " + decodeDuration + " NanoSeconds / Compression Ratio: " + 8*decode.size()/(double)huff.huffmanLength() + " / Mean Square Error: " + sum/ imageRaster.length);

        return ("Encode Time: " + encodeDuration + " NanoSeconds / Decode Time: " + decodeDuration + " NanoSeconds / Compression Ratio: " + 8*decode.size()/(double)huff.huffmanLength() + " / Mean Square Error: " + sum/ imageRaster.length);
    }
    public void edgeDetection(Filter fil)
    {
        FilterMask filter = new FilterMask(fil);
        setTransformArray(originalArray.length,originalArray[0].length);
        filter.runFilter(originalArray,transformArray);
        linearscale();
        this.pixelOverall();
    }


    public void linearscale()
    {
        HashMap<Integer,Double> scaleMap = new HashMap();
        for(int i =0;i<transformArray.length;i++)
        {
            for(int j=0;j<transformArray[0].length;j++)
            {
                scaleMap.put(transformArray[i][j],0.0);
            }
        }
        double interval = 255 / ((double) scaleMap.size()-1);
        Integer[] sorting = scaleMap.keySet().toArray(new Integer[0]);
        Arrays.sort(sorting);
//        System.out.println(Arrays.toString(sorting));
        for(int i=0;i<scaleMap.size()-1;i++)
        {
            scaleMap.put(sorting[i], i * interval);

        }
        scaleMap.put(sorting[sorting.length-1],255.0);

//        System.out.println(scaleMap);
        if(scaleMap.size() !=1) {
            for (int i = 0; i < transformArray.length; i++) {
                for (int j = 0; j < transformArray[i].length; j++) {
                    transformArray[i][j] = (int) Math.round(scaleMap.get(transformArray[i][j]));
                }
            }
        }
    }
    public void outlierScale()
    {
        HashMap<Integer,Double> scaleMap = new HashMap();
        for(int i =0;i<transformArray.length;i++)
        {
            for(int j=0;j<transformArray[0].length;j++)
            {
                scaleMap.put(transformArray[i][j],0.0);
            }
        }
        Integer[] sorting = scaleMap.keySet().toArray(new Integer[0]);
        Arrays.sort(sorting);
        System.out.println(Arrays.toString(sorting));
        for(int i=0;i<scaleMap.size();i++)
        {
            if(sorting[i]<0) {
                scaleMap.put(sorting[i],  Math.abs((sorting[i]/(double)sorting[0]) * 25));
            }
            else if(sorting[i]>255) {
                scaleMap.put(sorting[i],  (sorting[i]/(double)sorting[sorting.length-1]) * 25+230);
            }
            else {
                scaleMap.put(sorting[i], Double.valueOf((sorting[i])*0.8+10));

            }
        }
        System.out.println(scaleMap);

        for(int i=0;i<transformArray.length;i++)
        {
            for(int j=0;j<transformArray[i].length;j++)
            {
                transformArray[i][j] = (int)Math.round(scaleMap.get(transformArray[i][j]));
            }
        }
    }

    public void pixelOverall()
    {
        HashMap<Integer,Integer> pixelValues = new HashMap<>();
        for(int i =0;i< transformArray.length;i++)
        {
            for(int j=0;j<transformArray[i].length;j++)
            {
                if(!pixelValues.containsKey(transformArray[i][j]))
                {
                    pixelValues.put(transformArray[i][j],0);
                }
                pixelValues.put(transformArray[i][j],pixelValues.get(transformArray[i][j])+1);
            }
        }
        System.out.println(pixelValues);
    }
}
