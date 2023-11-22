package imageProcessing;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

public class RunLengthCompression {
    public byte[] runLengthEncoding(byte[] imageRaster)
    {
        ByteArrayOutputStream encoder = new ByteArrayOutputStream();
        byte lastByte = imageRaster[0];
        int matchCount = 1;
        for(int i=1; i < imageRaster.length; i++)
        {
            byte thisByte = imageRaster[i];
            if (lastByte == thisByte) {
                matchCount++;
            }
            else {
                encoder.write(lastByte);
                encoder.write((byte)matchCount);
                matchCount=1;
                lastByte = thisByte;
            }
        }
        encoder.write(lastByte);
        encoder.write((byte)matchCount);
        return encoder.toByteArray();
    }
    public byte[] runLengthDecoding(byte[] imageRaster)
    {
        ByteArrayOutputStream decoder = new ByteArrayOutputStream();
        for(int i=1; i < imageRaster.length; i+=2)
        {
            for(int l=0;l<imageRaster[i];l++){
                decoder.write(imageRaster[i-1]);
            }
        }
        return decoder.toByteArray();
    }

}
