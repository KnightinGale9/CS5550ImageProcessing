package imageProcessing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
//PGM converter credit: https://gist.github.com/armanbilge/3276d80030d1caa2ed7c

public class GrayPixelReader {
    private BufferedImage image;
    private BufferedInputStream stream;
    private boolean method;
    public GrayPixelReader(File originalImage) throws IOException {
        try {
            image = ImageIO.read(originalImage);
            ImageIO.write(image, "pnm", new File("src/main/image.pgm"));
            stream = new BufferedInputStream(new FileInputStream("src/main/image.pgm"));
            if (!next(stream).equals("P5"))
                method = false;
            else
            {
                method = true;
            }
        } catch (IOException ex) {
            method = false;
        }

    }
    public void conversionMethod(BufferedImage img, int[][] imgArray) {
        if (method)
        {
            try {
                convertImageToArrayPGM(imgArray);
            } catch (IOException e) {
                convertImagetoArrayRGB(img,imgArray);
                System.out.println(" png");
            }
        }
        else{
            convertImagetoArrayRGB(img,imgArray);
            System.out.println(" png");
        }

    }
    public void convertImagetoArrayRGB(BufferedImage img,int[][] imgArray)
    {
        for(int i = 0; i < imgArray.length; i++) {
            for (int j = 0; j < imgArray[0].length; j++) {
                int p = img.getRGB(i, j);

                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate average
                imgArray[i][j] = (r + g + b) / 3;

            }
        }
    }
    // Convert the JPG image to a PGM image
    public void convertImageToArrayPGM(int[][] imgarray) throws IOException {
        try {
//            if (!next(stream).equals("P5"))
//                throw new IOException("File " + "image.pgm" + " is not a binary PGM image.");
            final int col = Integer.parseInt(next(stream));
            final int row = Integer.parseInt(next(stream));
            final int max = Integer.parseInt(next(stream));
            if (max < 0 || max > 255)
                throw new IOException("The image's maximum gray value must be in range [0, " + 255 + "].");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    final int p = stream.read();
                    if (p == -1)
                        throw new IOException("Reached end-of-file prematurely.");
                    else if (p < 0 || p > max)
                        throw new IOException("Pixel value " + p + " outside of range [0, " + max + "].");
                    imgarray[j][i] = p;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            stream.close();
            File deleting = new File("src/main/image.pgm");
            deleting.delete();
        }
    }
    private static String next(InputStream stream) throws IOException {
        final List<Byte> bytes = new ArrayList<Byte>();
        while (true) {
            final int b = stream.read();

            if (b != -1) {

                final char c = (char) b;
                if (c == '#') {
                    int d;
                    do {
                        d = stream.read();
                    } while (d != -1 && d != '\n' && d != '\r');
                } else if (!Character.isWhitespace(c)) {
                    bytes.add((byte) b);
                } else if (bytes.size() > 0) {
                    break;
                }

            } else {
                break;
            }

        }
        final byte[] bytesArray = new byte[bytes.size()];
        for (int i = 0; i < bytesArray.length; ++i)
            bytesArray[i] = bytes.get(i);
        return new String(bytesArray);
    }

}
