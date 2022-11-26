package uk.khall.image.utils;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: keith.hall
 * Date: 21/05/12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class Posterize {
    private ArrayList<Color> colors;
    public Posterize(){
        colors = new ArrayList<Color>();
    }

    public ArrayList<Color> getColors(){
        return colors;
    }

    public BufferedImage apply(BufferedImage image, int level) {


        int[] levels = new int[256];
        levels = calculate(level, levels);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y),
                        a = (rgb >> 24) & 0xFF,
                        r = (rgb >> 16) & 0xff,
                        g = (rgb >> 8) & 0xff,
                        b = rgb & 0xff;
                r = levels[r];
                g = levels[g];
                b = levels[b];
                rgb = (a << 24) + (r << 16) + (g << 8) + b;
                image.setRGB(x, y, rgb);
                Color color = new Color(rgb);
                if (!colors.contains(color)) {
                    colors.add(color);
                }
            }
        }
        return image;
    }


    private int[] calculate(int level, int[] levels) {
        if (level != 1)
            for (int i = 0; i < 256; i++)
                levels[i] = 255 * (level * i / 256) / (level - 1);
        return levels;
    }


}
