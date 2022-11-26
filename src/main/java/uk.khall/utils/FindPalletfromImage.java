package uk.khall.utils;


import org.nd4j.shade.guava.collect.SortedSetMultimap;
import org.nd4j.shade.guava.collect.TreeMultimap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Keith on 08/02/2017.
 */
public class FindPalletfromImage {
    private BufferedImage image;
    private ArrayList<Color> reducedColorList = new ArrayList<Color>();
    private int cutOff =  80;
    private double diffVal = 50;
    public FindPalletfromImage(){

    }
    public void setImage(BufferedImage image){
        this.image = image;
    }
    public void setImageFromPath(String path) throws IOException {
        image = ImageIO.read(new File(path));
    }

    public int getCutOff() {
        return cutOff;
    }

    public void setCutOff(int cutOff) {
        this.cutOff = cutOff;
    }

    public void createPallet(){

            int width = image.getWidth();
            int height = image.getHeight();

            HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();

            ArrayList<Color> fullColorList = new ArrayList<Color>();
            for (int h = 0; h < height; h=h+10){
                for (int w = 0; w < width; w=w+20){
                    Integer color = image.getRGB(w,h);
                    Color testColor = new Color(color);

                    if(!colors.containsKey(testColor.getRGB())){
                        colors.put(testColor.getRGB(),1);
                    } else {
                        Integer csize = colors.get(testColor.getRGB())+1;
                        colors.put(testColor.getRGB(),csize);
                    }
                }
                //System.out.println(" h " + h);
            }
            System.out.println("colors " + colors.size());

            int count = 0;
            SortedSetMultimap<Integer, Integer> sortedColors = TreeMultimap.create();
            for (Integer col : colors.keySet()){
                sortedColors.put(colors.get(col),col);
                count++;
            }
            Set<Integer> set = sortedColors.keySet();
            ArrayList<Integer> countSet = new ArrayList<Integer>(set);
            for (int c = countSet.size()-1; c > -1 ;c-- ){
                Integer cval = countSet.get(c);
                Set<Integer> sortedColorset = sortedColors.get(cval);
                int ccount = 0;
                for(Integer sortedCol:sortedColorset) {
                    Color color = new Color(sortedCol);
                    if(color.getRed() > getCutOff() && color.getBlue() > getCutOff() && color.getGreen() > getCutOff()) {
                        fullColorList.add(color);
                        ccount++;
                    }
                }

            }
            for(Color testColor:fullColorList){

                if(reducedColorList.size()==0)
                    reducedColorList.add(testColor);
                else {
                    boolean testdiff = false;
                    for (Color reducedColor : reducedColorList){
                        if(findDiff(testColor,reducedColor, diffVal)){
                            testdiff=true;
                        }
                    }
                    if (!testdiff){
                        reducedColorList.add(testColor);
                    }
                }

            }
            System.out.println("sortedColors " + sortedColors.size());
            System.out.println("reducedColor " + reducedColorList.size());
            System.out.println("{");
            int c=0;
            for (Color reducedColor : reducedColorList ){

                System.out.println("{" + reducedColor.getRed() + "," + reducedColor.getGreen() + "," + reducedColor.getBlue() + "}");
                c++;
                if( c <reducedColorList.size())
                    System.out.println(",");
            }
            System.out.println("}");

    }
    public int roundToNearest(int number, int nearest){
        float result = (number/nearest) + 0.5f;
        int returnVal = (int)(result * nearest);

        return returnVal;

    }
    public ArrayList<Color> brighterList(ArrayList<java.awt.Color> colorList) {

        ArrayList<java.awt.Color> colors = new ArrayList<>();
        for (java.awt.Color color : colorList) {
            double FACTOR = 0.98;
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            int alpha = color.getAlpha();

            colors.add(color);
            /* From 2D group:
             * 1. black.brighter() should return grey
             * 2. applying brighter to blue will always return blue, brighter
             * 3. non pure color (non zero rgb) will eventually return white
             */
            for (int loopCount = 0; loopCount<6;loopCount++){
                int i = (int)(1.0/(1.0-FACTOR));
                if ( r == 0 && g == 0 && b == 0) {
                    colors.add(new java.awt.Color(i, i, i, alpha));
                }
                if ( r > 0 && r < i ) r = i;
                if ( g > 0 && g < i ) g = i;
                if ( b > 0 && b < i ) b = i;

                r = Math.min((int)(r/FACTOR), 255);
                g = Math.min((int)(g/FACTOR), 255);
                b = Math.min((int)(b/FACTOR), 255);
                if (r<0)
                    r=0;
                if (g<0)
                    g=0;
                if (b<0)
                    b=0;
                if (r>255)
                    r=255;
                if (g>255)
                    g=255;
                if (b>255)
                    b=255;
                colors.add(new java.awt.Color(r,g,b,alpha)
                );
                FACTOR = FACTOR-0.02;
            }
        }
        System.out.println("brighterList " + colors.size());
        System.out.println("{");
        int c=0;
        for (java.awt.Color reducedColor : colors ){

            System.out.println("{" + reducedColor.getRed() + "," + reducedColor.getGreen() + "," + reducedColor.getBlue() + "}");
            c++;
            if( c <colors.size())
                System.out.println(",");
        }
        System.out.println("}");
        return colors;
    }

    public ArrayList<Color> darkerList(ArrayList<java.awt.Color> colorList) {

        ArrayList<java.awt.Color> colors = new ArrayList<>();
        for (java.awt.Color color : colorList) {

            double FACTOR = 0.98;
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            int alpha = color.getAlpha();

            colors.add(color);
            /* From 2D group:
             * 1. black.brighter() should return grey
             * 2. applying brighter to blue will always return blue, brighter
             * 3. non pure color (non zero rgb) will eventually return white
             */
            for (int loopCount = 0; loopCount<6;loopCount++){
                r = (int)(r * FACTOR);
                g = (int)(g * FACTOR);
                b = (int)(b * FACTOR);
                if (r<0)
                    r=0;
                if (g<0)
                    g=0;
                if (b<0)
                    b=0;
                if (r>255)
                    r=255;
                if (g>255)
                    g=255;
                if (b>255)
                    b=255;
                colors.add(new java.awt.Color(r,g,b,alpha)
                );
                FACTOR = FACTOR-0.02;
            }
        }
        System.out.println("darkerList " + colors.size());
        System.out.println("{");
        int c=0;
        for (java.awt.Color reducedColor : colors ){

            System.out.println("{" + reducedColor.getRed() + "," + reducedColor.getGreen() + "," + reducedColor.getBlue() + "}");
            c++;
            if( c <colors.size())
                System.out.println(",");
        }
        System.out.println("}");
        return colors;
    }

    public boolean findDiff(Color testColor, Color searchColor, double diffComp){

        int testColorR = testColor.getRed();
        int testColorG = testColor.getGreen();
        int testColorB = testColor.getBlue();
        int searchColorR = searchColor.getRed();
        int searchColorG = searchColor.getGreen();
        int searchColorB = searchColor.getBlue();
        double diff =  (Math.sqrt(Math.pow((testColorR - searchColorR), 2) + Math.pow((testColorG - searchColorG), 2) + Math.pow((testColorB - searchColorB), 2)));
        if(diff < diffComp)
            return true;
        else
            return false;


    }
    public ArrayList<Color> getReducedColorList(){
        return reducedColorList;
    }
    public ArrayList<Color> getReducedColorList(int size){
        if(reducedColorList.size()>size)
            return new ArrayList<Color>(reducedColorList.subList(0,size));
        else
            return reducedColorList;
    }
    public void setDiffVal(double diffVal) {
        this.diffVal = diffVal;
    }

    public static void main(String[] params){
        FindPalletfromImage finder = new FindPalletfromImage();
        String image = "C:\\Users\\Keith\\Pictures\\Chitty Chitty Bang Bang Nov 2018\\James_Tissot_-_Ball_on_Shipboard.jpg";
        finder.setDiffVal(50);
        try {
            finder.setImageFromPath(image);
            finder.createPallet();
            ArrayList<Color> colors = finder.getReducedColorList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
