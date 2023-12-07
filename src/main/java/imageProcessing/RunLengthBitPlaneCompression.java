package imageProcessing;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class RunLengthBitPlaneCompression {
    public void runLengthEncoding(int[] imageRaster, ArrayList<Integer> encoder)
    {
        int lastByte = imageRaster[0];
        if(lastByte==0)
        {
            encoder.add(0);
        }
        int matchCount = 0;
        for(int i=0; i < imageRaster.length; i++) {
            int thisByte = imageRaster[i];
            if (lastByte == thisByte) {
                matchCount++;
            } else {
                encoder.add(matchCount);
                matchCount = 1;
                lastByte = thisByte;
            }
        }
        encoder.add(matchCount);
    }
    public ArrayList<Integer> runLengthDecoding(ArrayList<Integer>[] imageRaster)
    {
        ArrayList<Integer> decoder = new ArrayList<>();
        ArrayList<Integer>[] bitPlaneEncoding = new ArrayList[8];
        for(int i=0; i < imageRaster.length; i++)
        {
            bitPlaneEncoding[i]=new ArrayList<>();
            for(int j=0;j<imageRaster[i].size();j++)
            {
                for(int k=0;k<imageRaster[i].get(j);k++) {
                    bitPlaneEncoding[i].add((j%2==0)?1:0);
                }
            }
        }
//        System.out.println("bit");
        for(int i=0;i<bitPlaneEncoding[0].size();i++)
        {
            StringBuilder str = new StringBuilder();
            for(int bit=0;bit<bitPlaneEncoding.length;bit++)
            {
                str.append(bitPlaneEncoding[bit].get(i));
            }
            str=str.reverse();
            decoder.add((Integer.parseInt(str.toString(), 2)));
        }

        return decoder;
    }
}
