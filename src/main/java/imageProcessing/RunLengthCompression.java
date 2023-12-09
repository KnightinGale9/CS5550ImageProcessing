package imageProcessing;

import java.util.ArrayList;

public class RunLengthCompression {
    public ArrayList<Integer> runLengthEncoding(int[] imageRaster)
    {
        ArrayList<Integer> encoder = new ArrayList<>();
        int lastByte = imageRaster[0];
        int matchCount = 0;
        for(int i=0; i < imageRaster.length; i++)
        {
            int thisByte = imageRaster[i];
            if (lastByte == thisByte) {
                matchCount++;
            }
            else {
                encoder.add(lastByte);
                encoder.add(matchCount);
                matchCount=1;
                lastByte = thisByte;
            }
        }
        encoder.add(lastByte);
        encoder.add(matchCount);
        return encoder;
    }
    public ArrayList<Integer> runLengthDecoding(ArrayList<Integer> imageRaster)
    {
        ArrayList<Integer> decoder = new ArrayList<>();
        for(int i=1; i < imageRaster.size(); i+=2)
        {
            for(int l = 0; l< imageRaster.get(i); l++){
                decoder.add(imageRaster.get(i - 1));
            }
        }
        return decoder;
    }

}
