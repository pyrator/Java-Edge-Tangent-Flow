package uk.khall.image.utils;

import uk.khall.image.shape.AreaColor;
import uk.khall.image.shape.CustomShape;
import uk.khall.image.shape.ShapeUtils;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by keith.hall on 08/10/2014.
 */
public class AverageOutlineFilter extends AbstractBufferedImageOp {
    private int maxAreaSize = 0;

    private ArrayList<AreaColor> sortedAreas;

    private BufferedImage posterizedImage;

    private ArrayList<Color> colors;

    private Color outlineColor = Color.black;

    public void setColors(ArrayList<Color> colors){
        this.colors = colors;
    }


    public BufferedImage getPosterizedImage() {
        return posterizedImage;
    }

    public void setPosterizedImage(BufferedImage posterizedImage) {
        this.posterizedImage = posterizedImage;
    }


    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public ArrayList<AreaColor> getSortedAreas(){
        return  sortedAreas;
    }
    public void createSortedAreas(BufferedImage substrateImage){
        double totalSize = substrateImage.getWidth() * substrateImage.getHeight();

        sortedAreas = new ArrayList<AreaColor>();
        if (colors==null){
            colors = new ArrayList<Color>();
            for (int x = 0; x < substrateImage.getWidth(); x++) {
                for (int y = 0; y < substrateImage.getHeight(); y++) {
                    Color color = new Color(substrateImage.getRGB(x, y));
                    if (!colors.contains(color))
                        colors.add(color);
                }
            }
        }
        CustomShape cs = null;
        if (posterizedImage !=null){
            cs = new CustomShape(posterizedImage);
        } else {
            cs = new CustomShape(substrateImage);
        }
        double total = 0;
        for (Color color : colors) {
            Area area = cs.getArea_FastHack(color);
            if (!area.isSingular()) {
                try {
                    ArrayList<Area> subshapes = CustomShape.shapeToSubAreas(area, 1.0f);

                    //System.out.println("subshapes size " + subshapes.size());
                    for (Area subshape : subshapes) {
                        double subareasize = ShapeUtils.approxArea(subshape, 1.0);
                        AreaColor areaColor = new AreaColor(subshape, color, totalSize - subareasize);
                        sortedAreas.add(areaColor);
                        total += (subshape.getBounds2D().getWidth() * subshape.getBounds2D().getHeight());
                    }
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } else {
                double areasize = ShapeUtils.approxArea(area, 1.0);
                AreaColor areaColor = new AreaColor(area, color, totalSize - areasize);
                sortedAreas.add(areaColor);
                total += (area.getBounds2D().getWidth() * area.getBounds2D().getHeight());

            }

        }
        System.out.println("total size = " + total);
        maxAreaSize=0;
        for (AreaColor areaColor : sortedAreas) {
            Area area = areaColor.getArea();
            double topX = area.getBounds().getMinX();
            double topY = area.getBounds().getMinY();
            double maxX = area.getBounds().getMaxX();
            double maxY = area.getBounds().getMaxY();

            System.out.println("topX = " + topX + " topY = " + topY + " maxX = " + maxX + "maxY = " + maxY);
            long totalR = 0;
            long totalG = 0;
            long totalB = 0;
            long count = 0;
            ArrayList<Point> pixelList = new ArrayList<Point>();
            for (double x = topX; x < maxX; x++ ){
                for(double y = topY; y < maxY; y++){
                    if (area.contains(x,y)){
                        Point point = new Point((int)x,(int)y);
                        pixelList.add(point);
                        Color sampleColor = new Color(substrateImage.getRGB((int)x,(int)y));
                        totalR+= sampleColor.getRed();
                        totalG+= sampleColor.getGreen();
                        totalB+= sampleColor.getBlue();
                        count++;
                    }
                }
            }
            System.out.println("count = " + count);
            areaColor.setPixelList(pixelList);
            if (pixelList.size() > maxAreaSize)
                maxAreaSize = pixelList.size();
            if(count>0) {

                int finalR = (int) (totalR / count);
                int finalG = (int) (totalG / count);
                int finalB = (int) (totalB / count);
                Color finalColor = new Color(finalR, finalG, finalB);
                areaColor.setColor(finalColor);
            }
        }
    }
    public BufferedImage filter(BufferedImage substrateImage, BufferedImage finalImage) {


        if (substrateImage != null) {
            Graphics2D g;
            int dimx = substrateImage.getWidth();
            int dimy = substrateImage.getHeight();
            if ( finalImage == null ) {
                finalImage = createCompatibleDestImage( substrateImage, null );
                System.out.println("width = " + substrateImage.getWidth() + " height " + substrateImage.getHeight());
                g = finalImage.createGraphics();
                g.setBackground(Color.white);
                g.clearRect(0, 0, dimx, dimy);
            } else {
                g = finalImage.createGraphics();
            }


            createSortedAreas(substrateImage);


            for (AreaColor areaColor : sortedAreas) {
                Area area = areaColor.getArea();
                Color color = areaColor.getColor();
                System.out.println("size= " + areaColor.getPixelList().size() + " color " + color.getRGB());
                g.setClip(null);
                g.setStroke(new BasicStroke(1));

                ArrayList<Point> pixelList = areaColor.getPixelList();

                //ArrayList<Integer> ridgeList = areaColor.getRidgeSet();
                int count = 0;
                for(Point point : pixelList){
                    g.setColor(color);
                    g.fillRect(point.x,  point.y,1,1);
                    count++;
                }
                if(getOutlineColor()!=null) {
                    g.setStroke(new BasicStroke(1));
                    g.setColor(getOutlineColor());
                    g.draw(area);
                } else {
                    g.setStroke(new BasicStroke(1));
                    g.setColor(color.darker());
                    g.draw(area);
                }
            }
        }

        return finalImage;
    }

    public int getMaxAreaSize(){
        return maxAreaSize;
    }
}
