package uk.khall.image.utils.pallet;

import uk.khall.image.utils.AbstractBufferedImageOp;
import uk.khall.image.utils.ImageMath;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MedianFilter extends AbstractBufferedImageOp {

    private int hRadius;
    private int vRadius;
    private int iterations = 1;

    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();

        if (dst == null)
            dst = createCompatibleDestImage(src, null);

        BufferedImage intermediate = src.getSubimage(0, 0, width, height);
        for (int loop = 0; loop < getIterations(); loop++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    ArrayList<Integer> reds = new ArrayList<Integer>();
                    ArrayList<Integer> greens = new ArrayList<Integer>();
                    ArrayList<Integer> blues = new ArrayList<Integer>();

                    // Retrieve mask pixels and calculate median value for new pixel
                    // This is done primitively for efficiency
                    for (int row = y - getVRadius(); row <= y + getVRadius(); row++) {
                        for (int col = x - getHRadius(); col <= x + getHRadius(); col++) {
                            if (row >= 0 && col >= 0 && row < height && col < width) {
                                Color color = new Color(intermediate.getRGB(col, row));
                                reds.add(color.getRed());
                                greens.add(color.getGreen());
                                blues.add(color.getBlue());
                            }
                        }
                    }

                    int red = getMedian(reds);
                    int green = getMedian(greens);
                    int blue = getMedian(blues);
                    dst.setRGB(x, y, new Color(red, green, blue).getRGB());

                }
            }
            intermediate = dst.getSubimage(0, 0, width, height);
        }
        return dst;
    }

    /**
     * Returns the median value of the passed value list
     */
    private int getMedian(ArrayList<Integer> values) {
        Collections.sort(values);
        int size = values.size();

        if (size % 2 != 0) {
            return values.get(size / 2);
        } else {
            return (values.get(size / 2) + values.get(size / 2 - 1)) / 2;
        }
    }

    public void setHRadius(int hRadius) {
        this.hRadius = hRadius;
    }

    public int getHRadius() {
        return hRadius;
    }

    public void setVRadius(int vRadius) {
        this.vRadius = vRadius;
    }

    public int getVRadius() {
        return vRadius;
    }

    public void setRadius(int radius) {
        this.hRadius = this.vRadius = radius;
    }

    public int getRadius() {
        return hRadius;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }

    public String toString() {
        return "Blur/Box Blur...";
    }
}