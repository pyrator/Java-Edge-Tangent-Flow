package uk.khall.image.shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * CustomShape
 * based on a Class from Andrew Thompson *
 * Source: http://stackoverflow.com/questions/7052422/image-graphic-into-a-shape-in-java/7059497#7059497
 * @author Samuel Schneider, Andrew Thompson
 *
 *
 */
public class CustomShape {

    private BufferedImage image=null;

    /**
     * Creates an Area with PixelPerfect precision
     * @param color The color that is draws the Custom Shape
     * @param tolerance The color tolerance
     * @return Area
     */
    public Area getArea(Color color, int tolerance) {
        if(image==null) return null;
        Area area = new Area();
        for (int x=0; x<image.getWidth(); x++) {
            for (int y=0; y<image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x,y));
                if (isIncluded(color, pixel, tolerance)) {
                    Rectangle r = new Rectangle(x,y,1,1);
                    area.add(new Area(r));
                }
            }
        }

        return area;
    }
    /**
     * Creates an Area with PixelPerfect precision
     * @param color The color that is draws the Custom Shape
     * @param tolerance The color tolerance
     * @return Area
     */
    public AreaPixels getAreaPixels(Color color, int tolerance) {
        if(image==null) return null;
        Area area = new Area();
        ArrayList<Point> pixelList = new ArrayList<Point>();

        for (int x=0; x<image.getWidth(); x++) {
            for (int y=0; y<image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x,y));
                if (isIncluded(color, pixel, tolerance)) {
                    Rectangle r = new Rectangle(x,y,1,1);
                    area.add(new Area(r));
                    Point point = new Point(x,y);
                    pixelList.add(point);
                }
            }
        }
        return new AreaPixels(area, pixelList);
    }


    public Area getArea_FastHack(Color color) {

        if(image==null) return null;

        Area area = new Area();
        Rectangle r;
        int y1,y2;

        for (int x=0; x<image.getWidth(); x++) {
            y1=99;
            y2=-1;
            for (int y=0; y<image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x,y));

                if (pixel.getRGB()==color.getRGB()) {
                    if(y1==99) {y1=y;y2=y;}
                    if(y>(y2+1)) {
                        r = new Rectangle(x,y1,1,y2-y1);
                        area.add(new Area(r));
                        y1=y;y2=y;
                    }
                    y2=y;
                }
            }
            if((y2-y1)>=0) {
                r = new Rectangle(x,y1,1,y2-y1);
                area.add(new Area(r));
            }
        }

        return area;
    }
    public BufferedImage drawOutline(Area area) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = new BufferedImage(
            w,
            h,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,w,h);

        g.setClip(area);
        g.setColor(Color.red);
        g.fillRect(0,0,w,h);

        g.setClip(null);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.blue);
        g.draw(area);

        return result;
    }
    public BufferedImage drawOutline(Area area, Color color) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = new BufferedImage(
            w,
            h,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,w,h);

        g.setClip(area);
        g.setColor(color);
        g.fillRect(0,0,w,h);

        g.setClip(null);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.blue);
        g.draw(area);

        return result;
    }

    public static BufferedImage drawOutline(Area area, Color color, int w, int h) {

        BufferedImage result = new BufferedImage(
            w,
            h,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,w,h);

        g.setClip(area);
        g.setColor(color);

        g.fillRect(0,0,w,h);

        g.setClip(null);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.blue);
        g.draw(area);

        return result;
    }

    public BufferedImage drawTextOutline(Area area, Color color) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = new BufferedImage(
                w,
                h,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0,0,w,h);

        g.setClip(area);
        g.setColor(color);
        GradientPaint gradient = new GradientPaint(90,90,color,92, 92, Color.WHITE,true);
        g.setPaint(gradient);

        g.fillRect(0,0,w,h);

        g.setClip(null);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.blue);
        g.draw(area);

        return result;
    }

    public void drawTextOutline(Graphics2D g, int w, int h, Area area, Color color) {
        g.setClip(area);
        g.setColor(color);
        Random random = new Random(System.currentTimeMillis());
        int r1 = random.nextInt(2)* 2;
        int rr1 = random.nextInt(2) * 2;
        int r2 = random.nextInt(2) * 2;
        int rr2 = random.nextInt(2) * 2;
        int alt = random.nextInt(2);
        GradientPaint gradient;
        if (alt==0)
            gradient = new GradientPaint(r1,r2,color,rr1, rr2, Color.WHITE,true);
        else
            gradient = new GradientPaint(r1,r2,color,rr1, rr2,color.brighter(),true);
        g.setPaint(gradient);
        g.fillRect(0,0,w,h);

    }

    public static void drawFill(Graphics2D g, double size, Area area, Color color) {
        //int w = image.getWidth();
        //int h = image.getHeight();


        //g.setColor(Color.white);
        //g.fillRect(0,0,w,h);
        double minX = area.getBounds2D().getMinX();
        double minY = area.getBounds2D().getMinY();
        double maxX = area.getBounds2D().getMaxX();
        double maxY = area.getBounds2D().getMaxY();
        System.out.println("minX = " + minX + " minY = " + minY + " maxX = " + maxX + " maxY = " + maxY);

        PathIterator i = area.getPathIterator(AffineTransform.getQuadrantRotateInstance(0), 1.0);

        double[] coords = new double[6];
        double prevX = 0.0, prevY = 0.0;
        while (! i.isDone()) {
            int segType = i.currentSegment(coords);
            double x = coords[0], y = coords[1] ;
            switch (segType) {
            case PathIterator.SEG_CLOSE:

                break;
            case PathIterator.SEG_LINETO:
                g.setColor(color);
                g.fillOval((int)x, (int) y, 3,3);
                break;
            case PathIterator.SEG_MOVETO:

                break;
            default:
                throw new IllegalArgumentException("PathIterator contains curved segments");
            }
            prevX = x;
            prevY = y;
            i.next();
        }
        //g.setClip(area);
        //g.setColor(color);

        //g.fillOval();
        //g.fillRect(0,0,w,h);

        //g.setClip(null);
        //g.setStroke(new BasicStroke(1));
        //g.setColor(Color.blue);
        //g.draw(area);

        //return g;
    }
    public static void drawFlatFill(Graphics2D g, double size, Area area, Color color, float flatness) {
        //int w = image.getWidth();
        //int h = image.getHeight();


        //g.setColor(Color.white);
        //g.fillRect(0,0,w,h);
        double minX = area.getBounds2D().getMinX();
        double minY = area.getBounds2D().getMinY();
        double maxX = area.getBounds2D().getMaxX();
        double maxY = area.getBounds2D().getMaxY();
        System.out.println("minX = " + minX + " minY = " + minY + " maxX = " + maxX + " maxY = " + maxY);

        PathIterator i = area.getPathIterator(null, flatness);

        double[] coords = new double[6];
        double prevX = 0.0, prevY = 0.0;
        while (! i.isDone()) {
            int segType = i.currentSegment(coords);
            double x = coords[0], y = coords[1] ;
            switch (segType) {
            case PathIterator.SEG_CLOSE:

                break;
            case PathIterator.SEG_LINETO:
                g.setColor(color);
                g.fillOval((int)x, (int) y, 3,3);
                break;
            case PathIterator.SEG_MOVETO:

                break;
            default:
                throw new IllegalArgumentException("PathIterator contains curved segments");
            }
            prevX = x;
            prevY = y;
            i.next();
        }
        //g.setClip(area);
        //g.setColor(color);

        //g.fillOval();
        //g.fillRect(0,0,w,h);

        //g.setClip(null);
        //g.setStroke(new BasicStroke(1));
        //g.setColor(Color.blue);
        //g.draw(area);

        //return g;
    }
    public static double[] shapeToPolyline(Shape s, float flatness) {
    AffineTransform IDENTITY_XFORM = AffineTransform.getQuadrantRotateInstance(0);
    Point2D aPoint = new Point2D.Double();
    ArrayList<Point2D> segList = new ArrayList<Point2D>();
    double[] pts = new double[8];
        segList.clear();
        aPoint.setLocation(0,0);

        PathIterator pi = s.getPathIterator(IDENTITY_XFORM,flatness);
	     while (!pi.isDone()) {
            int segType = pi.currentSegment(pts);
	              switch (segType) {
                case PathIterator.SEG_MOVETO:
                    aPoint.setLocation(pts[0],pts[1]);
                    segList.add(new Point2D.Double(pts[0],pts[1]));
                    break;
                case PathIterator.SEG_LINETO:
                    segList.add(new Point2D.Double(pts[0],pts[1]));
                    break;
                case PathIterator.SEG_CLOSE:
                    segList.add(new Point2D.Double(aPoint.getX(),aPoint.getY()));
                    break;
            }
           pi.next();
        }
        double[] polyObj = new double[2*segList.size()];
	    for(int i=0; i<segList.size(); i++) {
            Point2D p2 = segList.get(i);
            polyObj[2*i] = (int)(p2.getX()+0.5);
            polyObj[2*i+1] = (int)(p2.getY()+0.5);
        }

       return polyObj;
    }

    public static ArrayList<Area> shapeToShapes(Area shape, float flatness){
        ArrayList<Area> shapes = new ArrayList<Area>();;

        AffineTransform IDENTITY_XFORM = AffineTransform.getQuadrantRotateInstance(0);
        PathIterator pi = shape.getPathIterator(IDENTITY_XFORM,flatness);
        double[] pts = new double[8];
        ArrayList<Integer> xpoints = new ArrayList<Integer>();;
        ArrayList<Integer> ypoints = new ArrayList<Integer>();

        while (!pi.isDone()) {
            int segType = pi.currentSegment(pts);
            double x = pts[0], y = pts[1] ;
            switch (segType) {
                case PathIterator.SEG_MOVETO:
                    xpoints.add(new Integer((int) x));
                    ypoints.add(new Integer((int) y));
                    break;
                case PathIterator.SEG_LINETO:
                    if (xpoints == null){
                        xpoints = new ArrayList<Integer>();
                   }
                    if (ypoints == null){
                        ypoints = new ArrayList<Integer>();
                    }
                    xpoints.add(new Integer((int) x));
                    ypoints.add(new Integer((int) y));
                    break;
                case PathIterator.SEG_CLOSE:
                    xpoints.add(new Integer((int) x));
                    ypoints.add(new Integer((int) y));
                    int[] xarray = new int[xpoints.size()];
                    int[] yarray = new int[ypoints.size()];
                    int count = 0;
                    for (Integer i: xpoints){
                        xarray[count] = i;
                        count++;
                    }
                    count = 0;
                    for (Integer i: ypoints){
                        yarray[count] = i;
                        count++;
                    }
                    Polygon p = new Polygon(xarray,yarray,xpoints.size());
                    Area area = new Area(p);
                    shapes.add(area);
                    xpoints = new ArrayList<Integer>();
                    ypoints = new ArrayList<Integer>();
                    break;
            }
           pi.next();
        }

        return shapes;
    }

    public static ArrayList<ArrayList<Point2D>> shapeToArraylist(Shape s, float flatness) {
        AffineTransform IDENTITY_XFORM = AffineTransform.getQuadrantRotateInstance(0);
        ArrayList<ArrayList<Point2D>> segments =  new ArrayList<ArrayList<Point2D>>();
        ArrayList<Point2D> segList = new ArrayList<Point2D>();
        double[] pts = new double[8];
        PathIterator pi = s.getPathIterator(IDENTITY_XFORM,flatness);
	    while (!pi.isDone()) {
            int segType = pi.currentSegment(pts);
            double x = pts[0], y = pts[1] ;
	        switch (segType) {
                case PathIterator.SEG_MOVETO:
                    segList = new ArrayList<Point2D>();
                    segList.add(new Point2D.Double(x,y));
                    break;
                case PathIterator.SEG_LINETO:
                    segList.add(new Point2D.Double(x,y));
                    break;
                case PathIterator.SEG_CLOSE:
                    segList.add(new Point2D.Double(x,y));
                    segments.add(segList);
                    break;
            }
           pi.next();
        }


       return segments;
    }
    public static ArrayList<Area> shapeToSubAreas(Shape s, float flatness) {
        AffineTransform IDENTITY_XFORM = AffineTransform.getQuadrantRotateInstance(0);
        ArrayList<Area> segments =  new ArrayList<Area>();
        Polygon shape = new Polygon();
        Area area = new Area();
        double[] pts = new double[8];
        PathIterator pi = s.getPathIterator(IDENTITY_XFORM,flatness);
	    while (!pi.isDone()) {
            int segType = pi.currentSegment(pts);
            double x = pts[0], y = pts[1] ;
	        switch (segType) {
                case PathIterator.SEG_MOVETO:
                    shape = new Polygon();
                    shape.addPoint((int) x, (int) y);
                    break;
                case PathIterator.SEG_LINETO:
                    shape.addPoint((int) x, (int) y);
                    break;
                case PathIterator.SEG_CLOSE:
                    shape.addPoint((int) x, (int) y);
                    area = new Area(shape);
                    segments.add(area);
                    break;
                default:
                    System.out.println("PathIterator " + segType + " x = " + x + " y = " + y);
            }
           pi.next();
        }


       return segments;
    }

    public static boolean isIncluded(Color target, Color pixel, int tolerance) {
        int rT = target.getRed();
        int gT = target.getGreen();
        int bT = target.getBlue();
        int rP = pixel.getRed();
        int gP = pixel.getGreen();
        int bP = pixel.getBlue();
        return(
            (rP-tolerance<=rT) && (rT<=rP+tolerance) &&
            (gP-tolerance<=gT) && (gT<=gP+tolerance) &&
            (bP-tolerance<=bT) && (bT<=bP+tolerance) );
    }

    public CustomShape(BufferedImage image) {
            this.image = image;
    }
}
