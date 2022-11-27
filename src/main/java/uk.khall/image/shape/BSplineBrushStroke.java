package uk.khall.image.shape;

import uk.khall.image.utils.pallet.ColorSpaceUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BSplineBrushStroke {
    private Graphics2D graphics2D;
    private BufferedImage bufferedImage;
    private BufferedImage parentImage;
    private Graphics2D graphics2D1;
    private Color primaryColor;
    private Color endColor;
    private ArrayList<Color> secondaryColors;
    private ArrayList<Color> secondaryEndColors;
    Random random = new Random(System.currentTimeMillis());
    public BSplineBrushStroke(){};

    public BSplineBrushStroke(Graphics2D graphics2D){
        this.graphics2D = graphics2D;
    }


    public BSplineBrushStroke(Graphics2D graphics2D, int width, int height, BufferedImage parentImage){
        this.graphics2D = graphics2D;
        this.parentImage = parentImage;
        if(parentImage.getType()==0){
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        } else {
            bufferedImage = new BufferedImage(width, height, parentImage.getType());
        }
        graphics2D1 = bufferedImage.createGraphics();
    }

    public BSplineBrushStroke(Graphics2D graphics2D, int width, int height, BufferedImage parentImage, int type){
        this.graphics2D = graphics2D;
        this.parentImage = parentImage;
        bufferedImage = new BufferedImage(width,height,type);
        graphics2D1 = bufferedImage.createGraphics();
    }
    public void addBrushStroke(Color primaryColor, ArrayList<Color> secondaryColors, int startx, int endx, int starty, int endy, int width){
        this.primaryColor = primaryColor;

        graphics2D.setStroke(new BasicStroke(2.0f));
        LineObject midLine = new LineObject(startx,endx,starty,endy);
        ArrayList<PointObject> midPoints = getBSplineObjects(midLine);
        if (secondaryColors==null){
            secondaryColors = new ArrayList<>();
            Color colr = new Color(bufferedImage.getRGB(startx,starty));
            secondaryColors.add(colr.brighter());
            secondaryColors.add(colr.darker());
            secondaryColors.add(colr);
        }
        Color paintColor = getRandomColor(primaryColor, secondaryColors);
        graphics2D.setPaint(paintColor);
        //drawBrushLine(midPoints,paintColor);
        this.secondaryColors = secondaryColors;
        //graphics2D.drawLine(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy());
        //nibline(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy(),bufferedImage,graphics2D);
        drawBrushLine(midPoints,paintColor);
        for (int i=0; i<width/2; i++) {
           LineObject pLine1 = getParallelLine(startx, endx, starty, endy, i);
           LineObject pLine2 = getParallelLine(startx, endx, starty, endy, 0 - i);
            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
/*
            //graphics2D.drawLine(pLine1.getStartx(),pLine1.getStarty(),pLine1.getEndx(),pLine1.getEndy());
            nibline(pLine1.getStartx(),pLine1.getStarty(),pLine1.getEndx(),pLine1.getEndy(),bufferedImage,graphics2D);
            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
            //graphics2D.drawLine(pLine2.getStartx(),pLine2.getStarty(),pLine2.getEndx(),pLine2.getEndy());
            nibline(pLine2.getStartx(),pLine2.getStarty(),pLine2.getEndx(),pLine2.getEndy(),bufferedImage,graphics2D);
 */
           ArrayList<PointObject> points1 = getBSplineObjects(pLine1);

           drawBrushLine(points1,paintColor);
           ArrayList<PointObject> points2 = getBSplineObjects(pLine2);
           paintColor = getRandomColor(primaryColor, secondaryColors);
           drawBrushLine(points2,paintColor);
        }

    }

    public void addColorBrushStroke(Color primaryColor, ArrayList<Color> secondaryColors, int startx, int endx, int starty, int endy, int width){
        this.primaryColor = primaryColor;

        graphics2D.setStroke(new BasicStroke(2.0f));
        LineObject midLine = new LineObject(startx,endx,starty,endy);
        if (secondaryColors==null){
            secondaryColors = new ArrayList<>();
            Color colr = new Color(bufferedImage.getRGB(startx,starty));

            secondaryColors.add(colr.brighter());
            secondaryColors.add(colr.darker());
            secondaryColors.add(colr);
        }
        Color paintColor = getRandomColor(primaryColor, secondaryColors);
        ArrayList<ColorPointObject> midPoints = getColorBSplineObjects(midLine, paintColor);
        graphics2D.setPaint(paintColor);
        //drawBrushLine(midPoints,paintColor);
        this.secondaryColors = secondaryColors;
        //graphics2D.drawLine(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy());
        //nibline(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy(),bufferedImage,graphics2D);
        drawColorBrushLine(midPoints);
        for (int i=0; i<width/2; i++) {
            LineObject pLine1 = getParallelLine(startx, endx, starty, endy, i);
            LineObject pLine2 = getParallelLine(startx, endx, starty, endy, -i);
            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
/*
            //graphics2D.drawLine(pLine1.getStartx(),pLine1.getStarty(),pLine1.getEndx(),pLine1.getEndy());
            nibline(pLine1.getStartx(),pLine1.getStarty(),pLine1.getEndx(),pLine1.getEndy(),bufferedImage,graphics2D);
            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
            //graphics2D.drawLine(pLine2.getStartx(),pLine2.getStarty(),pLine2.getEndx(),pLine2.getEndy());
            nibline(pLine2.getStartx(),pLine2.getStarty(),pLine2.getEndx(),pLine2.getEndy(),bufferedImage,graphics2D);
 */
/*            ArrayList<ColorPointObject> points1 = getColorBSplineObjects(pLine1,getRandomColor(primaryColor, secondaryColors));
            drawColorBrushLine(points1);
            ArrayList<ColorPointObject> points2 = getColorBSplineObjects(pLine2,getRandomColor(primaryColor, secondaryColors));
            drawColorBrushLine(points2);*/
            ArrayList<ColorPointObject> points1 = getColorBSplineObjects(pLine1,getRandomColor(primaryColor));
            drawColorBrushLine(points1);
            ArrayList<ColorPointObject> points2 = getColorBSplineObjects(pLine2,getRandomColor(primaryColor));
            drawColorBrushLine(points2);
        }




    }

    public void addTaperingColorBrushStroke(Color primaryColor, ArrayList<Color> secondaryColors, int startx, int endx, int starty, int endy, int width){
        this.primaryColor = primaryColor;

        graphics2D.setStroke(new BasicStroke(2.0f));
        LineObject midLine = new LineObject(startx,endx,starty,endy);
        if (secondaryColors==null){
            secondaryColors = new ArrayList<>();
            Color colr = new Color(bufferedImage.getRGB(startx,starty));

            secondaryColors.add(colr.brighter());
            secondaryColors.add(colr.darker());
            secondaryColors.add(colr);
        }
        Color paintColor = getRandomColor(primaryColor, secondaryColors);
        ArrayList<ColorPointObject> midPoints = getColorBSplineObjects(midLine, paintColor);
        graphics2D.setPaint(paintColor);
        //drawBrushLine(midPoints,paintColor);
        this.secondaryColors = secondaryColors;
        //graphics2D.drawLine(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy());
        //nibline(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy(),bufferedImage,graphics2D);
        drawColorBrushLine(midPoints);
        for (int i=0; i<width/2; i++) {
            int absdiffx = i*2;
            int absdiffy = i*2;
            if (endx<startx)
                absdiffx=-i;
            if (endy<starty)
                absdiffy=-i;
            LineObject pLine1 = getParallelLine(startx+absdiffx, endx-absdiffx, starty+absdiffy, endy-absdiffy, i);
            LineObject pLine2 = getParallelLine(startx+absdiffx, endx-absdiffx, starty+absdiffy, endy-absdiffy, -i);

            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
/*
            //graphics2D.drawLine(pLine1.getStartx(),pLine1.getStarty(),pLine1.getEndx(),pLine1.getEndy());
            nibline(pLine1.getStartx(),pLine1.getStarty(),pLine1.getEndx(),pLine1.getEndy(),bufferedImage,graphics2D);
            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
            //graphics2D.drawLine(pLine2.getStartx(),pLine2.getStarty(),pLine2.getEndx(),pLine2.getEndy());
            nibline(pLine2.getStartx(),pLine2.getStarty(),pLine2.getEndx(),pLine2.getEndy(),bufferedImage,graphics2D);
 */
/*            ArrayList<ColorPointObject> points1 = getColorBSplineObjects(pLine1,getRandomColor(primaryColor, secondaryColors));
            drawColorBrushLine(points1);
            ArrayList<ColorPointObject> points2 = getColorBSplineObjects(pLine2,getRandomColor(primaryColor, secondaryColors));
            drawColorBrushLine(points2);*/
            ArrayList<ColorPointObject> points1 = getColorBSplineObjects(pLine1,getRandomColor(primaryColor));
            drawColorBrushLine(points1);
            ArrayList<ColorPointObject> points2 = getColorBSplineObjects(pLine2,getRandomColor(primaryColor));
            drawColorBrushLine(points2);
        }




    }
    public void addColorBrushStroke(Color primaryColor, Color endColor, ArrayList<Color> secondaryColors, ArrayList<Color> secondaryEndColors,int startx, int endx, int starty, int endy, int width){
        this.primaryColor = primaryColor;
        this.endColor = endColor;
        graphics2D.setStroke(new BasicStroke(2.0f));
        LineObject midLine = new LineObject(startx,endx,starty,endy);
        if (secondaryColors==null){
            secondaryColors = new ArrayList<>();
            Color colr = new Color(bufferedImage.getRGB(startx,starty));
            secondaryColors.add(colr.brighter());
            secondaryColors.add(colr.darker());
            secondaryColors.add(colr);
        }
        if (secondaryEndColors==null){
            secondaryEndColors = new ArrayList<>();
            Color colr = new Color(bufferedImage.getRGB(endx,endy));
            secondaryEndColors.add(colr.brighter());
            secondaryEndColors.add(colr.darker());
            secondaryEndColors.add(colr);
        }
        Color paintColor = getRandomColor(primaryColor, secondaryColors);
        ArrayList<ColorPointObject> midPoints = getColorBSplineObjects(midLine, getRandomColor(primaryColor, secondaryColors), getRandomColor(endColor, secondaryEndColors));
        graphics2D.setPaint(paintColor);
        //drawBrushLine(midPoints,paintColor);
        this.secondaryColors = secondaryColors;
        this.secondaryEndColors = secondaryEndColors;
        //graphics2D.drawLine(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy());
        //nibline(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy(),bufferedImage,graphics2D);
        drawColorBrushLine(midPoints);
        for (int i=0; i<width/2; i++) {
            LineObject pLine1 = getParallelLine(startx, endx, starty, endy, i);
            LineObject pLine2 = getParallelLine(startx, endx, starty, endy, -i);
            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
            ArrayList<ColorPointObject> points1 = getColorBSplineObjects(pLine1,getRandomColor(primaryColor, secondaryColors), getRandomColor(endColor, secondaryEndColors));
            drawColorBrushLine(points1);
            ArrayList<ColorPointObject> points2 = getColorBSplineObjects(pLine2,getRandomColor(primaryColor, secondaryColors), getRandomColor(endColor, secondaryEndColors));
            drawColorBrushLine(points2);
        }




    }

    public void addTaperingColorBrushStroke(Color primaryColor, Color endColor, ArrayList<Color> secondaryColors, ArrayList<Color> secondaryEndColors,int startx, int endx, int starty, int endy, int width){
        this.primaryColor = primaryColor;
        this.endColor = endColor;
        graphics2D.setStroke(new BasicStroke(2.0f));
        LineObject midLine = new LineObject(startx,endx,starty,endy);
        if (secondaryColors==null){
            secondaryColors = new ArrayList<>();
            Color colr = new Color(bufferedImage.getRGB(startx,starty));
            secondaryColors.add(colr.brighter());
            secondaryColors.add(colr.darker());
            secondaryColors.add(colr);
        }
        if (secondaryEndColors==null){
            secondaryEndColors = new ArrayList<>();
            Color colr = new Color(bufferedImage.getRGB(endx,endy));
            secondaryEndColors.add(colr.brighter());
            secondaryEndColors.add(colr.darker());
            secondaryEndColors.add(colr);
        }
        Color paintColor = getRandomColor(primaryColor, secondaryColors);
        ArrayList<ColorPointObject> midPoints = getColorBSplineObjects(midLine, getRandomColor(primaryColor, secondaryColors), getRandomColor(endColor, secondaryEndColors));
        graphics2D.setPaint(paintColor);
        //drawBrushLine(midPoints,paintColor);
        this.secondaryColors = secondaryColors;
        this.secondaryEndColors = secondaryEndColors;
        //graphics2D.drawLine(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy());
        //nibline(midLine.getStartx(),midLine.getStarty(),midLine.getEndx(),midLine.getEndy(),bufferedImage,graphics2D);
        drawColorBrushLine(midPoints);
        for (int i=0; i<width/2; i++) {
            int absdiffx = i*2;
            int absdiffy = i*2;
            if (endx<startx)
                absdiffx=-i;
            if (endy<starty)
                absdiffy=-i;
            LineObject pLine1 = getParallelLine(startx+absdiffx, endx-absdiffx, starty+absdiffy, endy-absdiffy, i);
            LineObject pLine2 = getParallelLine(startx+absdiffx, endx-absdiffx, starty+absdiffy, endy-absdiffy, -i);


            graphics2D.setPaint(getRandomColor(primaryColor, secondaryColors));
            ArrayList<ColorPointObject> points1 = getColorBSplineObjects(pLine1,getRandomColor(primaryColor, secondaryColors), getRandomColor(endColor, secondaryEndColors));
            drawColorBrushLine(points1);
            ArrayList<ColorPointObject> points2 = getColorBSplineObjects(pLine2,getRandomColor(primaryColor, secondaryColors), getRandomColor(endColor, secondaryEndColors));
            drawColorBrushLine(points2);
        }




    }

    private Color getRandomColor(Color defaultColor){
        Color paintColor ;
        int rand = random.nextInt(10 );
        switch(rand){
            case 1:
                paintColor = darker(defaultColor);
                break;
            case 2:
                paintColor = brighter(defaultColor);
                break;
            default:
                paintColor = defaultColor;

        }
        return paintColor;
    }
    private Color getRandomColor(Color defaultColor, ArrayList<Color> secondaryColors){
        Color paintColor ;
        int size = secondaryColors.size();
        int rand = random.nextInt(size+20 );
        int rand2 = 0;
        if (size>0)
            rand2 = random.nextInt(size);
        if (rand == 1)
            paintColor = darker(defaultColor);
        else if (rand == 2)
            paintColor = brighter(defaultColor);
        else if ( rand >2 && rand < 19)
            paintColor = defaultColor;
        else if (rand2 > 0)
            paintColor = secondaryColors.get(rand2);
        else
            paintColor = defaultColor;
        return paintColor;
    }

    private Color getRandomColor(Color defaultColor, Color secondaryColor){
        Color paintColor ;
        int rand = random.nextInt(10 );
        switch(rand){
            case 1:
            case 2:
                paintColor = darker(defaultColor);
                break;
            case 3:
            case 4:
                paintColor = brighter(defaultColor);
                break;
            case 5:
                paintColor = secondaryColor;
                break;
            case 6:
                paintColor = brighter(secondaryColor);
                break;
            default:
                paintColor = defaultColor;

        }
        return paintColor;
    }
    private double FACTOR = 0.85;
    public Color brighter(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        /* From 2D group:
         * 1. black.brighter() should return grey
         * 2. applying brighter to blue will always return blue, brighter
         * 3. non pure color (non zero rgb) will eventually return white
         */
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;
        int newr = ((int)(r/FACTOR)) + random.nextInt(4)-2;
        int newg = ((int)(g/FACTOR)) + random.nextInt(4)-2;
        int newb = ((int)(b/FACTOR)) + random.nextInt(4)-2;
        if (newr < 0) newr=0;
        if (newg < 0) newg=0;
        if (newb < 0) newb=0;
        return new Color(Math.min(newr, 255),
                Math.min(newg, 255),
                Math.min(newb, 255),
                alpha);
    }


    public Color darker(Color color) {
        int r = (int)(color.getRed()*FACTOR)+random.nextInt(4)-2;
        int g = (int)(color.getGreen()*FACTOR)+random.nextInt(4)-2;
        int b = (int)(color.getBlue()*FACTOR)+random.nextInt(4)-2;
        if (r > 255) r=255;
        if (g > 255) g=255;
        if (b > 255) b=255;
        return new Color(Math.max(r, 0),
                Math.max(g, 0),
                Math.max(b, 0),
                color.getAlpha());
    }

    private void drawBrushLine(ArrayList<PointObject> points, Color color){



        for (int c = 0; c < points.size()-1; c++){
            PointObject pointObject = points.get(c);
            PointObject nextPoint = points.get(c+1);
            try {
                Color underlyingColor = new Color(parentImage.getRGB((int)pointObject.getX(),  (int)pointObject.getY()));
                //KMColor kmColor = new KMColor(primaryColor);
                //kmColor.mix(underlyingColor);
                graphics2D.setPaint(getRandomColor(underlyingColor,color));
                //graphics2D.drawLine((int)pointObject.getX(),  (int)pointObject.getY(),(int)nextPoint.getX(),(int)nextPoint.getY());
                line((int)pointObject.getX(),  (int)pointObject.getY(),(int)nextPoint.getX(),(int)nextPoint.getY(),parentImage,graphics2D);
            } catch (Exception e) {
                //e.printStackTrace();
                //System.out.println("error at " + pointObject.getX() + " " + pointObject.getY());
            }

        }
    }
    private void drawColorBrushLine(ArrayList<ColorPointObject> points){



        for (int c = 0; c < points.size()-1; c++){
            ColorPointObject pointObject = points.get(c);
            ColorPointObject nextPoint = points.get(c+1);
            try {
                Color underlyingColor = new Color(parentImage.getRGB((int)pointObject.getX(),  (int)pointObject.getY()));
                //KMColor kmColor = new KMColor(primaryColor);
                //kmColor.mix(underlyingColor);
                graphics2D.setPaint(pointObject.getColor());
                //graphics2D.drawLine((int)pointObject.getX(),  (int)pointObject.getY(),(int)nextPoint.getX(),(int)nextPoint.getY());
                line((int)pointObject.getX(),(int)pointObject.getY(),(int)nextPoint.getX(),(int)nextPoint.getY(),pointObject.getColor(),graphics2D);
            } catch (Exception e) {
                //e.printStackTrace();
                //System.out.println("error at " + pointObject.getX() + " " + pointObject.getY());
            }

        }
    }
    public void nibline(int x,int y,int x2, int y2, BufferedImage src, Graphics2D fg ) {
        int x1 = x;
        int y1 = y;
        double angle = getAngle(x1,y1,x2,y2);
        angle = angle+90;
        if(angle > 360)
            angle = angle-360;
        double rangle = (angle * Math.PI / 180);
        angle = angle-180;
        if(angle < 0 )
            angle = angle+360;
        double langle = (angle * Math.PI / 180);
        int w = x2 - x ;
        int h = y2 - y ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;
        }
        int numerator = longest >> 1 ;
        int count = 1;
        int nib  = 5;

        for (int i=0;i<=longest;i++) {
            double minSize = count < longest-count ? count : longest-count;
            double pencilWidth = ((minSize/longest)) * nib;

            Color mainColor = getRandomColor(primaryColor,secondaryColors);//new Color(primaryColor.getRGB());
            fg.setColor(mainColor);
            fg.fillRect(x,y,2,2);
            int startX = x;
            int startY = y;
            int endX   = (int)(x + pencilWidth * Math.sin(rangle));
            int endY   = (int)(y + pencilWidth * Math.cos(rangle));
            line(startX,startY,endX,endY,src,fg);
            endX   = (int)(x + pencilWidth * Math.sin(langle));
            endY   = (int)(y + pencilWidth * Math.cos(langle));
            line(startX,startY,endX,endY,src,fg);
            numerator += shortest ;


            if (!(numerator<longest)) {
                numerator -= longest ;
                x += dx1 ;
                y += dy1 ;
            } else {
                x += dx2 ;
                y += dy2 ;
            }
            count ++;
        }
    }
    public void line(int x,int y,int x2, int y2, BufferedImage src, Graphics2D fg ) {
        int w = x2 - x ;
        int h = y2 - y ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;
        }
        int numerator = longest >> 1 ;
        int count = 1;
        int nib  = 5;


        Color mainColor = getRandomColor(primaryColor,secondaryColors);//new Color(primaryColor.getRGB());

        for (int i=0;i<=longest;i++) {
            double minSize = Math.min(count, longest - count);//count < longest-count ? count : longest-count;
            double pencilWidth = ((minSize/longest)) * nib;
            float composite = 0.7f;
            try {
                mainColor = getRandomColor(primaryColor,secondaryColors);;//new Color(src.getRGB(x,y));
            } catch (Exception e) {
                System.out.println("error at " + x + " : " + y);
            }
            //fg.setComposite(AlphaComposite.DstOver);
            fg.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, composite));
            //fg.setColor(kmColor.getColor());
            fg.setColor(mainColor);
            fg.fillRect(x,y,2,2);


            numerator += shortest ;


            if (!(numerator<longest)) {
                numerator -= longest ;
                x += dx1 ;
                y += dy1 ;
            } else {
                x += dx2 ;
                y += dy2 ;
            }
            count ++;
        }
    }

    public void line(int x,int y,int x2, int y2, Color src, Graphics2D fg ) {
        int w = x2 - x ;
        int h = y2 - y ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;
        }
        /**/int numerator = longest >> 1 ;
        int count = 1;
        int nib  = 5;


        for (int i=0;i<=longest;i++) {
            double minSize = Math.min(count, longest - count);//count < longest-count ? count : longest-count;
            double pencilWidth = ((minSize/longest)) * nib;

            float composite = 0.7f;
            fg.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, composite));
            fg.setColor(src);
            fg.fillRect(x,y,2,2);


            numerator += shortest ;


            if (!(numerator<longest)) {
                numerator -= longest ;
                x += dx1 ;
                y += dy1 ;
            } else {
                x += dx2 ;
                y += dy2 ;
            }
            count ++;
        }
    }
    private LineObject getParallelLine(int x1, int x2, int y1, int y2, int offsetPixels){
        double L = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));

// This is the second line
        int x1p = (int)(x1 + offsetPixels * (y2-y1) / L);
        int x2p = (int)(x2 + offsetPixels * (y2-y1) / L);
        int y1p = (int)(y1 + offsetPixels * (x1-x2) / L);
        int y2p = (int)(y2 + offsetPixels * (x1-x2) / L);
        return new LineObject(x1p,x2p,y1p,y2p);
    }

    private ArrayList<ColorPointObject> getColorBSplineObjects(LineObject lineObject, Color color){
        ArrayList<ColorPointObject> pointObjects  = new ArrayList<>();

        pointObjects.add(new ColorPointObject(color, 1,lineObject.getStartx(), lineObject.getStarty()));


        double x11 = .875*lineObject.getStartx() + .125*lineObject.getEndx() ;
        double y11 = .875*lineObject.getStarty() + .125*lineObject.getEndy();
        ColorPointObject p11 = new ColorPointObject();
        p11.setId(2);
        p11.setX(x11);
        p11.setY(y11);
        p11.setColor(color);
        pointObjects.add(p11);

        double x1 = .75*lineObject.getStartx() + .25*lineObject.getEndx();
        double y1 = .75*lineObject.getStarty() + .25*lineObject.getEndy();
        ColorPointObject p1 = new ColorPointObject();
        p1.setId(3);
        p1.setX(x1);
        p1.setY(y1);
        p1.setColor(color);
        pointObjects.add(p1);

        double x12 = .625*lineObject.getStartx() + .375*lineObject.getEndx();
        double y12 = .625*lineObject.getStarty() + .375*lineObject.getEndy();
        ColorPointObject p12 = new ColorPointObject();
        p12.setId(4);
        p12.setX(x12);
        p12.setY(y12);
        p12.setColor(color);
        pointObjects.add(p12);


        double midx1 = .5*lineObject.getStartx() + .5*lineObject.getEndx();
        double midy1 = .5*lineObject.getStarty() + .5*lineObject.getEndy();
        ColorPointObject mp1 = new ColorPointObject();
        mp1.setId(5);
        mp1.setX(midx1);
        mp1.setY(midy1);
        mp1.setColor(color);
        pointObjects.add(mp1);


        double x121 = .375*lineObject.getStartx() + .625*lineObject.getEndx();
        double y121 = .375*lineObject.getStarty() + .625*lineObject.getEndy();
        ColorPointObject p121 = new ColorPointObject();
        p121.setId(6);
        p121.setX(x121);
        p121.setY(y121);
        p121.setColor(color);
        pointObjects.add(p121);


        double x2 = .25*lineObject.getStartx() + .75*lineObject.getEndx();
        double y2 = .25*lineObject.getStarty() + .75*lineObject.getEndy();
        ColorPointObject p2 = new ColorPointObject();
        p2.setId(7);
        p2.setX(x2);
        p2.setY(y2);
        p2.setColor(color);
        pointObjects.add(p2);

        double x21 = .125*lineObject.getStartx() + .875*lineObject.getEndx();
        double y21 = .125*lineObject.getStarty() + .875*lineObject.getEndy();
        ColorPointObject p21 = new ColorPointObject();
        p21.setId(8);
        p21.setX(x21);
        p21.setY(y21);
        p21.setColor(color);
        pointObjects.add(p21);


        ColorPointObject p3 = new ColorPointObject();
        p3.setId(9);
        p3.setX(lineObject.getEndx());
        p3.setY(lineObject.getEndy());
        p3.setColor(color);
        pointObjects.add(p3);
        for(ColorPointObject pointObject : pointObjects){
            pointObject.setX(pointObject.getX()+random.nextInt(5)-2);
            pointObject.setY(pointObject.getY()+random.nextInt(5)-2);
        }
        ColorBSpline bSpline = new ColorBSpline();
        bSpline.setColorControlObjects(pointObjects);
        return bSpline.run();

    }
    private ArrayList<ColorPointObject> getColorBSplineObjects(LineObject lineObject, Color color, Color endColor){
        ArrayList<ColorPointObject> pointObjects  = new ArrayList<>();

        pointObjects.add(new ColorPointObject(color, 1,lineObject.getStartx(), lineObject.getStarty()));


        double x11 = .875*lineObject.getStartx() + .125*lineObject.getEndx() ;
        double y11 = .875*lineObject.getStarty() + .125*lineObject.getEndy();
        ColorPointObject p11 = new ColorPointObject();
        p11.setId(2);
        p11.setX(x11);
        p11.setY(y11);
        Color mixedColor = getMixedColor(color, endColor, .875, .125);
        p11.setColor(mixedColor);

        pointObjects.add(p11);

        double x1 = .75*lineObject.getStartx() + .25*lineObject.getEndx();
        double y1 = .75*lineObject.getStarty() + .25*lineObject.getEndy();
        ColorPointObject p1 = new ColorPointObject();
        p1.setId(3);
        p1.setX(x1);
        p1.setY(y1);
        mixedColor = getMixedColor(color, endColor, .75, .25);
        p1.setColor(mixedColor);

        pointObjects.add(p1);

        double x12 = .625*lineObject.getStartx() + .375*lineObject.getEndx();
        double y12 = .625*lineObject.getStarty() + .375*lineObject.getEndy();
        ColorPointObject p12 = new ColorPointObject();
        p12.setId(4);
        p12.setX(x12);
        p12.setY(y12);
        mixedColor = getMixedColor(color, endColor, .625, .375);
        p12.setColor(mixedColor);

        pointObjects.add(p12);


        double midx1 = .5*lineObject.getStartx() + .5*lineObject.getEndx();
        double midy1 = .5*lineObject.getStarty() + .5*lineObject.getEndy();
        ColorPointObject mp1 = new ColorPointObject();
        mp1.setId(5);
        mp1.setX(midx1);
        mp1.setY(midy1);
        mixedColor = getMixedColor(color, endColor, .5, .5);
        mp1.setColor(mixedColor);

        pointObjects.add(mp1);


        double x121 = .375*lineObject.getStartx() + .625*lineObject.getEndx();
        double y121 = .375*lineObject.getStarty() + .625*lineObject.getEndy();
        ColorPointObject p121 = new ColorPointObject();
        p121.setId(6);
        p121.setX(x121);
        p121.setY(y121);
        mixedColor = getMixedColor(color, endColor, .375, .625);
        p121.setColor(mixedColor);

        pointObjects.add(p121);


        double x2 = .25*lineObject.getStartx() + .75*lineObject.getEndx();
        double y2 = .25*lineObject.getStarty() + .75*lineObject.getEndy();
        ColorPointObject p2 = new ColorPointObject();
        p2.setId(7);
        p2.setX(x2);
        p2.setY(y2);
        mixedColor = getMixedColor(color, endColor, .25, .75);
        p2.setColor(mixedColor);

        pointObjects.add(p2);

        double x21 = .125*lineObject.getStartx() + .875*lineObject.getEndx();
        double y21 = .125*lineObject.getStarty() + .875*lineObject.getEndy();
        ColorPointObject p21 = new ColorPointObject();
        p21.setId(8);
        p21.setX(x21);
        p21.setY(y21);
        mixedColor = getMixedColor(color, endColor, .125, .875);
        p21.setColor(mixedColor);

        pointObjects.add(p21);


        ColorPointObject p3 = new ColorPointObject();
        p3.setId(9);
        p3.setX(lineObject.getEndx());
        p3.setY(lineObject.getEndy());
        p3.setColor(endColor);
        pointObjects.add(p3);

        for(ColorPointObject pointObject : pointObjects){
            pointObject.setX(pointObject.getX()+random.nextInt(5)-2);
            pointObject.setY(pointObject.getY()+random.nextInt(5)-2);
        }
        ColorBSpline bSpline = new ColorBSpline();
        bSpline.setColorControlObjects(pointObjects);
        return bSpline.run();

    }
    private Color getMixedColor(Color primaryColor, Color endColor, double primeRatio, double endRatio){

        double[] hsb = ColorSpaceUtils.RGBtoHSB(primaryColor.getRed(),primaryColor.getGreen(),primaryColor.getBlue());
        double[] hsb2 = ColorSpaceUtils.RGBtoHSB(endColor.getRed(),endColor.getGreen(), endColor.getBlue());
        double[] avghsb = new double[3];
        avghsb[0] = (hsb[0]*primeRatio) + (hsb2[0]*endRatio);
        avghsb[1] = (hsb[1]*primeRatio) + (hsb2[1]*endRatio);
        avghsb[2] = (hsb[2]*primeRatio) + (hsb2[2]*endRatio);

        int[] avgcolor = ColorSpaceUtils.HSBtoRGB(avghsb[0],avghsb[1],avghsb[2]);
        return new Color(avgcolor[0], avgcolor[1], avgcolor[2]);

    }
    private ArrayList<PointObject> getBSplineObjects(LineObject lineObject){
        ArrayList<PointObject> pointObjects  = new ArrayList<>();

        pointObjects.add(new PointObject(1,lineObject.getStartx(), lineObject.getStarty()));


        double x11 = .875*lineObject.getStartx() + .125*lineObject.getEndx() ;
        double y11 = .875*lineObject.getStarty() + .125*lineObject.getEndy();
        PointObject p11 = new PointObject();
        p11.setId(2);
        p11.setX(x11);
        p11.setY(y11);
        pointObjects.add(p11);

        double x1 = .75*lineObject.getStartx() + .25*lineObject.getEndx();
        double y1 = .75*lineObject.getStarty() + .25*lineObject.getEndy();
        PointObject p1 = new PointObject();
        p1.setId(3);
        p1.setX(x1);
        p1.setY(y1);
        pointObjects.add(p1);

        double x12 = .625*lineObject.getStartx() + .375*lineObject.getEndx();
        double y12 = .625*lineObject.getStarty() + .375*lineObject.getEndy();
        PointObject p12 = new PointObject();
        p12.setId(4);
        p12.setX(x12);
        p12.setY(y12);
        pointObjects.add(p12);




        double midx1 = .5*lineObject.getStartx() + .5*lineObject.getEndx();
        double midy1 = .5*lineObject.getStarty() + .5*lineObject.getEndy();
        PointObject mp1 = new PointObject();
        mp1.setId(5);
        mp1.setX(midx1);
        mp1.setY(midy1);
        pointObjects.add(mp1);


        double x121 = .375*lineObject.getStartx() + .625*lineObject.getEndx();
        double y121 = .375*lineObject.getStarty() + .625*lineObject.getEndy();
        PointObject p121 = new PointObject();
        p121.setId(6);
        p121.setX(x121);
        p121.setY(y121);
        pointObjects.add(p121);


        double x2 = .25*lineObject.getStartx() + .75*lineObject.getEndx();
        double y2 = .25*lineObject.getStarty() + .75*lineObject.getEndy();
        PointObject p2 = new PointObject();
        p2.setId(7);
        p2.setX(x2);
        p2.setY(y2);
        pointObjects.add(p2);

        double x21 = .125*lineObject.getStartx() + .875*lineObject.getEndx();
        double y21 = .125*lineObject.getStarty() + .875*lineObject.getEndy();
        PointObject p21 = new PointObject();
        p21.setId(8);
        p21.setX(x21);
        p21.setY(y21);
        pointObjects.add(p21);


        PointObject p3 = new PointObject();
        p3.setId(9);
        p3.setX(lineObject.getEndx());
        p3.setY(lineObject.getEndy());
        pointObjects.add(p3);
        for(PointObject pointObject : pointObjects){
            pointObject.setX(pointObject.getX()+random.nextInt(5)-2);
            pointObject.setY(pointObject.getY()+random.nextInt(5)-2);
        }
        BSpline bSpline = new BSpline();
        bSpline.setControlObjects(pointObjects);
        return bSpline.run();

    }

    private float getAngle(int startx, int endx, int starty, int endy){
        float angle = (float) Math.toDegrees(Math.atan2(endy - starty, endx - startx));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    /**
     * Calculates the angle from centerPt to targetPt in degrees.
     * The return should range from [0,360), rotating CLOCKWISE,
     * 0 and 360 degrees represents NORTH,
     * 90 degrees represents EAST, etc...
     *
     * Assumes all points are in the same coordinate space.  If they are not,
     * you will need to call SwingUtilities.convertPointToScreen or equivalent
     * on all arguments before passing them  to this function.
     *
     * @param centerPt   Point we are rotating around.
     * @param targetPt   Point we want to calcuate the angle to.
     * @return angle in degrees.  This is the angle from centerPt to targetPt.
     */
    private double calcRotationAngleInDegrees(Point centerPt, Point targetPt)
    {
        // calculate the angle theta from the deltaY and deltaX values
        // (atan2 returns radians values from [-PI,PI])
        // 0 currently points EAST.
        // NOTE: By preserving Y and X param order to atan2,  we are expecting
        // a CLOCKWISE angle direction.
        double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);

        // rotate the theta angle clockwise by 90 degrees
        // (this makes 0 point NORTH)
        // NOTE: adding to an angle rotates it clockwise.
        // subtracting would rotate it counter-clockwise
        theta += Math.PI/2.0;

        // convert from radians to degrees
        // this will give you an angle from [0->270],[-180,0]
        double angle = Math.toDegrees(theta);

        // convert to positive range [0-360)
        // since we want to prevent negative angles, adjust them now.
        // we can assume that atan2 will not return a negative value
        // greater than one partial rotation
        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

public static void main(String[] params){
        BSplineBrushStroke bSplineBrushStroke = new BSplineBrushStroke();
        Color color = new Color(0,255,0,255);
        color = bSplineBrushStroke.darker(color);
        color = new Color(0,255,0,255);
        color = bSplineBrushStroke.brighter(color);
        color = new Color(255,0,0,255);
        color = bSplineBrushStroke.brighter(color);
        color = new Color(255,0,0,255);
        color = bSplineBrushStroke.darker(color);
}



}
