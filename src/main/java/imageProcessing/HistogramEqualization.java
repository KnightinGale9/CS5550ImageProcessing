package imageProcessing;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HistogramEqualization {
    //transform the hashmaps to just simple id linked list this version requires last value but its hard to do so
    HashMap<Integer,Integer> pixelValues =new HashMap<>();
    ArrayList<HistogramStorage> storage = new ArrayList<HistogramStorage>();
    public void setPixelValues(int[][] area)
    {
        for(int i =0;i< area.length;i++)
        {
            for(int j=0;j<area[i].length;j++)
            {
                if(!pixelValues.containsKey(area[i][j]))
                {
                    pixelValues.put(area[i][j],0);
                }
                pixelValues.put(area[i][j],pixelValues.get(area[i][j])+1);
            }
        }
    }
    public void globalEqualization(int[][] original, int[][] transform)
    {

        setPixelValues(original);
        System.out.println(pixelValues);
        int size = original.length * original[0].length;
        double oldvalue=0;
        for (Map.Entry<Integer,Integer> mapElement : pixelValues.entrySet()) {
            int key = mapElement.getKey();
            int value = (mapElement.getValue());
            storage.add(new HistogramStorage(key,value));
        }
        Collections.sort(storage);
        for (HistogramStorage comp:storage) {
            comp.histogramEqualizationCalculation(oldvalue,255.0,size);
            oldvalue= comp.getHeCalculation();
            pixelValues.put(comp.getPixelValue(),comp.getNewPixelValue());
        }
        for(int i =0;i< original.length;i++)
        {
            for(int j =0;j< original[i].length; j++)
            {
                transform[i][j]=pixelValues.get(original[i][j]);
            }
        }
        System.out.println(pixelValues);

    }
    public void localEqualization(int[][] original, int[][]transform,int mask)
    {
        int[][] localMask = new int[mask][mask];
        int[][] temp = padding(original, mask/2);
        for(int i=mask/2,i2=0;i< temp.length-mask/2;i++,i2++)
        {
            for(int j=mask/2,j2=0;j<temp[0].length-mask/2;j++,j2++)
            {
                for(int mi=0,offset=-mask/2; mi< localMask.length;mi++,offset++) {
                    System.arraycopy(temp[i+offset],j-mask/2,localMask[mi],0,localMask.length);
                }
                setPixelValues(localMask);
                int size = localMask.length * localMask[0].length;
                double oldvalue=0;
                for (Map.Entry<Integer,Integer> mapElement : pixelValues.entrySet()) {
                    int key = mapElement.getKey();
                    int value = (mapElement.getValue());
                    storage.add(new HistogramStorage(key,value));
                }
                Collections.sort(storage);
                for (HistogramStorage comp:storage) {
                    comp.histogramEqualizationCalculation(oldvalue,255,size);
                    oldvalue= comp.getHeCalculation();
                    pixelValues.put(comp.getPixelValue(),comp.getNewPixelValue());
                }
                transform[i2][j2]=pixelValues.get(temp[i][j]);
                pixelValues.clear();
                storage.clear();
            }
        }
        System.out.println("debug");
    }
    public int[][] padding(int[][] original, int pad)
    {
        int[][] padImage = new int[original.length+(2*pad)][original[0].length+(2*pad)];
        for(int i =0;i< original.length;i++)
        {
            System.arraycopy(original[i],0,padImage[i+pad],pad,original[i].length);
        }
        for(int i =0;i< pad;i++) {
            System.arraycopy(original[0],0,padImage[i],pad,original[0].length);
            System.arraycopy(original[original.length-1],0,padImage[original.length+i+pad],pad,original[original.length-1].length);
        }
        for(int i=0;i<padImage.length;i++)
        {
            for(int j=0;j<pad;j++)
            {
                padImage[i][j]=padImage[i][pad];
                padImage[i][padImage[0].length-pad+j]=padImage[i][padImage[0].length-pad-1];
            }
        }
        System.out.println(padImage.toString());
        return padImage;
    }
}
class HistogramStorage implements Comparable<HistogramStorage> {
    private int pixelValue;
    private int numberPixel;
    private double heCalculation;
    private int newPixelValue;


    public HistogramStorage(int pixel,int value) {
        pixelValue=pixel;
        numberPixel=value;
    }
    public void histogramEqualizationCalculation(double oldValue, double L,int size){
        heCalculation=oldValue+L*numberPixel/size;
        newPixelValue= (int) Math.round(heCalculation);
    }
    public int getPixelValue() {
        return pixelValue;
    }

    public int getNumberPixel() {
        return numberPixel;
    }
    public int getNewPixelValue(){
        return newPixelValue;
    }
    public double getHeCalculation(){
        return heCalculation;
    }
    public String toString(){
        return pixelValue + ":" + numberPixel+":"+newPixelValue;
    }

    @Override
    public int compareTo(HistogramStorage o) {
        if(this.pixelValue>o.getPixelValue()){
            return 1;
        }
        else if(this.pixelValue<o.getPixelValue()){
            return -1;
        }
        else{
            return 0;
        }
    }
}

