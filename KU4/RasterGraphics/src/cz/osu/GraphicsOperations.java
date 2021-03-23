package cz.osu;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicsOperations {

    public static void fillBrightness(V_RAM vram, int brightness){

        brightness = Math.min(255, Math.max(0, brightness));

        for(int y = 0; y < vram.getHeight(); y++)
            for(int x = 0; x < vram.getWidth(); x++)
                vram.getRawData()[y][x] = brightness;
    }

    public static int[][] getGrayImageMatrix(BufferedImage img){
        int[][] pixels = new int[img.getWidth()][img.getHeight()];
        int r,g,b;

        for(int i = 0; i < img.getWidth(); i++){
            for(int j = 0; j < img.getHeight(); j++){
                r = (img.getRGB(i,j)>>16)&0xff;
                g = (img.getRGB(i,j)>>8)&0xff;
                b = img.getRGB(i,j)&0xff;

                pixels[i][j] = (int)Math.round(0.299*r + 0.587*g + 0.114*b);
            }
        }

        return pixels;
    }

    public static int[][] getGrayImageMatrixExtended(int[][] pixels){
        int[][] pixelsExtended = new int[pixels.length + 2][pixels[0].length+2];

        for (int i = 1; i <= pixels.length ; i++) {
            for (int j = 1; j <= pixels[0].length; j++) {
                if (j == 1) pixelsExtended[i][j-1] = pixels[i-1][j-1];
                if (j == pixels[0].length) pixelsExtended[i][j+1] = pixels[i-1][j-1];

                pixelsExtended[i][j] = pixels[i-1][j-1];
            }
        }

        for (int i = 0; i < pixelsExtended[0].length; i++) {
            pixelsExtended[0][i] = pixelsExtended[1][i];
            pixelsExtended[pixelsExtended.length-1][i] = pixelsExtended[pixelsExtended.length-2][i];
        }

        return pixelsExtended;
    }

    public static BufferedImage convolve(BufferedImage img,double[][] kernel,int T){
        int[][] pixels = getGrayImageMatrix(img);
        int[][] pixelEx = GraphicsOperations.getGrayImageMatrixExtended(pixels);
        BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);

        int ry = (kernel.length-1) / 2;
        int rx = (kernel[0].length-1) / 2;
        double conv;
        int intConv;

        for(int i = 1; i <= pixels.length; i++ ){
            for(int j = 1; j <= pixels[0].length; j++ ){
                conv = 0;

               for (int ky =-ry; ky <= ry; ky++) {
                    for(int kx =-rx;kx<=rx;kx++){
                        conv += pixelEx[i+kx][j+ky] * kernel[ky+ry][kx+rx];
                    }
                }

                intConv = conv < 0 ? 0: conv > 255 ? 255 : (int)Math.round(conv);
                if (Math.abs(intConv - pixelEx[i][j]) >= T){
                    out.setRGB(i-1,j-1,new Color(pixelEx[i][j],pixelEx[i][j],pixelEx[i][j]).getRGB());
                }
                else{
                    intConv = 255 << 24 | intConv << 16 | intConv << 8 | intConv;
                    out.setRGB(i-1,j-1,intConv);
                }
            }
        }

        return out;
    }
}
