package uk.khall.image.utils.pallet;


import uk.khall.image.utils.AbstractBufferedImageOp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by keith.hall on 08/10/2014.
 */
public class TempPosterizeFilter extends AbstractBufferedImageOp {
    private static int[][] brightPallet =
            {
                    {7,7,7}
                    ,
                    {237,231,217}
                    ,
                    {44,37,29}
                    ,
                    {201,178,100}
                    ,
                    {222,212,140}
                    ,
                    {99,75,49}
                    ,
                    {164,143,96}
                    ,
                    {161,113,51}
                    ,
                    {174,168,146}
                    ,
                    {120,108,84}
                    ,
                    {147,60,32}
                    ,
                    {206,199,191}
                    ,
                    {59,77,91}
                    ,
                    {92,29,12}
                    ,
                    {123,122,140}
                    ,
                    {170,83,91}
                    ,
                    {193,75,45}
                    ,
                    {204,127,135}
                    ,
                    {207,119,71}
            }



    ;
    private ArrayList<Color> colors;
    private int[][]arrayBrightPallet;
    private boolean usesrcType = false;


    public TempPosterizeFilter() {
    }

    public ArrayList<Color> getColors(){

        for (int i = 0; i < brightPallet.length; i++){
            colors.add(new Color(brightPallet[i][0], brightPallet[i][1],brightPallet[i][2]));
        }
        return colors;
    }
    public void setColors(ArrayList<Color> colors){
            this.colors = colors;
            brightPallet = new int[colors.size()][3];
            int count = 0;

            for (Color color : colors){
                brightPallet[count][0]=color.getRed();
                brightPallet[count][1]=color.getGreen();
                brightPallet[count][2]=color.getBlue();
                count++;
            }
    }

    public boolean isUsesrcType() {
        return usesrcType;
    }

    public void setUsesrcType(boolean usesrcType) {
        this.usesrcType = usesrcType;
    }

    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        int width = src.getWidth();
        int height = src.getHeight();
        Graphics2D graphics;
        int dimx;
        int dimy;

        if ( dest == null ) {

            if(usesrcType)
                dest = new BufferedImage(src.getWidth(),src.getHeight(),src.getType());
            else
                dest = createCompatibleDestImage(src, null);
        }
        if(src != null) {
/*            BufferedImage posterizedImage = brightenImage(src, 15, 15, 15);
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    Color color = new Color(posterizedImage.getRGB(w, h));
                    Color newColor = getNearestColorFromArrayPallet(color);
                    dest.setRGB(w, h, newColor.getRGB());
                }
            }*/
            //BufferedImage posterizedImage = brightenImage(src, 15, 15, 15);
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    Color color = new Color(src.getRGB(w, h));
                    Color newColor = getNearestColorFromPallet(color);
                    dest.setRGB(w, h, newColor.getRGB());
                }
            }
        }
        return dest;
    }
    private BufferedImage brightenImage(BufferedImage substrateImage, float rlevel, float glevel, float blevel) {
        BufferedImage brightImage = null;
        if (substrateImage != null) {
            brightImage = createCompatibleDestImage(substrateImage, null);
            for (int w=0; w< substrateImage.getWidth(); w++){
                for (int h=0; h < substrateImage.getHeight(); h++){
                    Color color = new Color(substrateImage.getRGB(w,h));
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    r += (int)(r*(rlevel/100));
                    if (r > 255 )
                        r=255;
                    g += (int)(g*(glevel/100));
                    if (g > 255 )
                        g=255;
                    b +=  (int)(b*(blevel/100));
                    if (b > 255 )
                        b=255;
                    Color newColor = new Color(r, g, b);
                    brightImage.setRGB(w, h, newColor.getRGB());
                }
            }
        }
        return brightImage;
    }
    private Color getNearestColorFromPallet(Color testColor) {

        int nearest = 0;
        double diff = Double.MAX_VALUE;
        int testColorR = testColor.getRed();
        int testColorG = testColor.getGreen();
        int testColorB = testColor.getBlue();


        for (int n = 0; n < brightPallet.length; n++) {
            double test = Math.sqrt(Math.pow((testColorR - brightPallet[n][0]), 2) + Math.pow((testColorG - brightPallet[n][1]), 2) + Math.pow((testColorB - brightPallet[n][2]), 2));
            if (test < diff) {
                diff = test;
                nearest = n;
            }
        }

        return new Color(brightPallet[nearest][0], brightPallet[nearest][1], brightPallet[nearest][2]);

    }
    public static int[][] getBrightPallet() {
        return brightPallet;
    }

}
