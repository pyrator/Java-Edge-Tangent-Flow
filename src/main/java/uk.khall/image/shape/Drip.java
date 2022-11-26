package uk.khall.image.shape;




import uk.khall.image.utils.pallet.KMColor;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: keith.hall
 * Date: 23/02/12
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
public class Drip {
    private Graphics2D graphics;

    private int yrand = 3000;
    private int xrand = 3000;
    private Random random = new Random(System.currentTimeMillis());
    private Shape shape;
    private Stroke pen1;
    BufferedImage bi;
    Graphics2D g2;
    BufferedImage image;

    public Drip(Graphics2D graphics){
        this.graphics = graphics;


    }

    public Drip(Graphics2D graphics, int width, int height, BufferedImage parent){
        this.graphics = graphics;
        bi = new BufferedImage(width, height,
                                BufferedImage.TYPE_INT_RGB);
        g2 = bi.createGraphics();
        this.image = parent;
    }
    public void addDrop(Color color, int x, int y, int r){
        //graphics.setPaint(color);
        graphics.setColor(color.brighter());
        shape =  createPath(x,y,r);
        graphics.draw(shape);
        pen1 = new BasicStroke (6.0F, BasicStroke.CAP_ROUND,
                                  BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		//				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.2f));
        graphics.fill(shape);
        //graphics.drawOval(x,y,250,250);

    }


    public void addDrop(Color color, int x, int y, int r, int variance){
        //graphics.setPaint(color);
        graphics.setColor(color.brighter());
        shape =  createPath(x,y,r,variance);
        graphics.draw(shape);
        pen1 = new BasicStroke (6.0F, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.2f));
        graphics.fill(shape);
        //graphics.drawOval(x,y,250,250);

    }
    public void addDrop(Color color, int x, int y, int r, int edges, int variance){
        //graphics.setPaint(color);
        graphics.setColor(color.brighter());
        shape =  createPath(x,y,r,edges,variance);
        graphics.draw(shape);
        pen1 = new BasicStroke (6.0F, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.2f));
        graphics.fill(shape);
        //graphics.drawOval(x,y,250,250);

    }
    public void addSolidDrop(Color color, int x, int y, int r){
        //graphics.setPaint(color);
        graphics.setColor(color);
        //shape =  createPath(x,y,r);
        //graphics.draw(shape);
        //pen1 = new BasicStroke (6.0F, BasicStroke.CAP_ROUND,
        //        BasicStroke.JOIN_ROUND);
        //graphics.setStroke(pen1);
        //graphics.setColor(color);
        //graphics.fill(shape);
        //graphics.drawOval(x,y,r,r);
        int radius = random.nextInt(r)+2;
        graphics.fillOval(x,y,radius,radius);

    }
    public void addDrop(Color color, int x, int y, int r, Shape shape){
        //graphics.setPaint(color);
        graphics.setColor(color.brighter());

        graphics.draw(shape);
        pen1 = new BasicStroke (6.0F, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.2f));
        graphics.fill(shape);
        //graphics.drawOval(x,y,250,250);

    }
    public void addDrop(Color color, int x, int y, int r, float outline, Composite composite, int variance){
        //graphics.setPaint(color);
        graphics.setColor(color.brighter());
        shape =  createPath(x,y,r,variance);
        graphics.draw(shape);
        pen1 = new BasicStroke (outline, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(composite);
        graphics.fill(shape);
        //graphics.setColor(color.brighter());
        graphics.draw(shape);
        //graphics.drawOval(x,y,250,250);

    }
    public void addDrop(Color color, int x, int y, double r, float outline, Composite composite, int variance){
        //graphics.setPaint(color);
        graphics.setColor(color.brighter());
        shape =  createPath(x,y,(int)r,variance);
        graphics.draw(shape);
        pen1 = new BasicStroke (outline, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(composite);
        graphics.fill(shape);
        //graphics.drawOval(x,y,250,250);

    }
    public void addCompDrop(Color color, int x, int y, double r, float outline, Composite composite, int variance){
        //graphics.setPaint(color);
        float[] hsb = new float[3];
        hsb = Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),hsb);
        System.out.println("hsb  " + hsb[0] + " " + hsb[1] + " " + hsb[2]);
        if (hsb[0]>0.5)
            hsb[0]-=0.5;
        else
            hsb[0]+=0.5;
/*        hsb[0] = (float)(hsb[0] / 6.2832 * 360.0 + 0);

        // Shift hue to opposite side of wheel and convert to [0-1] value
        hsb[0]+= 180;
        if (hsb[0] > 360) { hsb[0] -= 360; }
        hsb[0] /= 360;*/

        Color ccolor = new Color(Color.HSBtoRGB(hsb[0],hsb[1],hsb[2]));

        System.out.println("hsb  " + hsb[0] + " " + hsb[1] + " " + hsb[2]);

        //Color ccolor = getComplementary(color);


        shape =  createPath(x,y,(int)r,variance);

        pen1 = new BasicStroke (outline, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(composite);
        graphics.fill(shape);
        graphics.setColor(ccolor.brighter());
        graphics.draw(shape);
        //graphics.drawOval(x,y,250,250);

    }

    public void addFullCompDrop(Color color, int x, int y, double r, float outline, Composite composite, int variance){
        //graphics.setPaint(color);
        float[] hsb = new float[3];
        hsb = Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),hsb);
        //System.out.println("hsb  " + hsb[0] + " " + hsb[1] + " " + hsb[2]);
        if (hsb[0]>0.5)
            hsb[0]-=0.5;
        else
            hsb[0]+=0.5;
        hsb[0] = (float)(hsb[0] / 6.2832 * 360.0 + 0);

        // Shift hue to opposite side of wheel and convert to [0-1] value
        hsb[0]+= 180;
        if (hsb[0] > 360) { hsb[0] -= 360; }
        hsb[0] /= 360;

        Color ccolor = new Color(Color.HSBtoRGB(hsb[0],hsb[1],hsb[2]));

        //System.out.println("hsb  " + hsb[0] + " " + hsb[1] + " " + hsb[2]);

        //Color ccolor = getComplementary(color);


        shape =  createPath(x,y,(int)r,variance);

        pen1 = new BasicStroke (outline, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(ccolor);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(composite);
        graphics.fill(shape);
        //graphics.setColor(ccolor.brighter());
        graphics.draw(shape);
        //graphics.drawOval(x,y,250,250);

    }

    private Color getComplementary(Color color){
        double r  = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        r /= 255.0;
        g /= 255.0;
        b /= 255.0;
        double max = Math.max(r, g);
        max = Math.max(max,b) ;
        double min = Math.min(r, g);
        min  = Math.min(min, b);
        double h=0;
        double s=0;
        double l = (max + min) / 2.0;

        if(max == min) {
            h = s = 0;  //achromatic
        } else {
            double d = max - min;
            s = (l > 0.5 ? d / (2.0 - max - min) : d / (max + min));

            if(max == r && g >= b) {
                h = 1.0472 * (g - b) / d ;
            } else if(max == r && g < b) {
                h = 1.0472 * (g - b) / d + 6.2832;
            } else if(max == g) {
                h = 1.0472 * (b - r) / d + 2.0944;
            } else if(max == b) {
                h = 1.0472 * (r - g) / d + 4.1888;
            }
        }

        h = h / 6.2832 * 360.0 + 0;

        // Shift hue to opposite side of wheel and convert to [0-1] value
        h+= 180;
        if (h > 360) { h -= 360; }
        h /= 360;

        // Convert h s and l values into r g and b values
        // Adapted from answer by Mohsen http://stackoverflow.com/a/9493060/4939630
        if(s == 0){
            r = g = b = l; // achromatic
        } else {


            double q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            double p = 2 * l - q;

            r = hue2rgb(p, q, h + 1/3);
            g = hue2rgb(p, q, h);
            b = hue2rgb(p, q, h - 1/3);
        }

        r = Math.round(r * 255);
        g = Math.round(g * 255);
        b = Math.round(b * 255);
        return new Color((int)r,(int)g,(int)b);
    }
    private double hue2rgb(double p, double q, double t){
        if(t < 0) t += 1;
        if(t > 1) t -= 1;
        if(t < 1/6) return p + (q - p) * 6 * t;
        if(t < 1/2) return q;
        if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
        return p;
    }
    public void addDrop(int[] pixelA, int x, int y, int r){
        //graphics.setPaint(color);
        Color color = new Color(pixelA[0],pixelA[1],pixelA[2]);


        graphics.setColor(color.brighter());
        shape =  createPath(x,y,r);
        graphics.draw(shape);
        pen1 = new BasicStroke (6.0F, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        graphics.setStroke(pen1);
        graphics.setColor(color);
        //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //				RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setComposite(AlphaComposite.SrcOver);
        graphics.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.2f));
        graphics.fill(shape);
        //graphics.drawOval(x,y,250,250);

    }

    public void addMixDrop(Color color, int x, int y, int r){
        //graphics.setPaint(color);
        if (r < 5)
            r=5;
        double xxx=0, yyy=0;
        try {
            if (g2 != null && bi != null){
                g2.setBackground(Color.WHITE);
                g2.setPaint(Color.WHITE);
                g2.fillRect(0,0,bi.getWidth(), bi.getHeight());

                shape =  createPath(x,y,r);
                double width = shape.getBounds2D().getWidth();
                double height = shape.getBounds2D().getHeight();
                double xx = shape.getBounds2D().getX();
                double yy = shape.getBounds2D().getY();

                g2.setColor(color.brighter());
                g2.draw(shape);
                pen1 = new BasicStroke (6.0F, BasicStroke.CAP_ROUND,
                                          BasicStroke.JOIN_ROUND);
                g2.setStroke(pen1);
                g2.setColor(color);
                //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                //				RenderingHints.VALUE_ANTIALIAS_ON);
                //g2.setComposite(AlphaComposite.SrcOver);
                //g2.setComposite(AlphaComposite.getInstance(
                //                AlphaComposite.SRC_OVER, 0.9f));
                g2.fill(shape);
                for (xxx=xx; xxx < (xx+width); xxx++){
                    for (yyy=yy; yyy < (yy+height); yyy++){
                        if (yyy<image.getHeight() && xxx<image.getWidth() && yyy>0 && xxx>0){
                            KMColor back = new KMColor(new Color(image.getRGB((int)xxx,(int)yyy)));
                            back.mix(new Color(bi.getRGB((int)xxx,(int)yyy)));
                            graphics.setPaint(back.getColor());
                            graphics.fillRect((int)xxx,(int)yyy,1,1);
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("out of bounds " + xxx + "  " + yyy);
        }
        //graphics.drawOval(x,y,250,250);

    }



    public void addMixDrop(Color color, int x, int y, int r, float outline, int variance){
        //graphics.setPaint(color);
        if (r < 5)
            r=5;
        double xxx=0, yyy=0;
        try {
            if (g2 != null && bi != null){
                g2.setBackground(Color.WHITE);
                g2.setPaint(Color.WHITE);
                g2.fillRect(0,0,bi.getWidth(), bi.getHeight());

                shape =  createPath(x,y,r, variance);
                double width = shape.getBounds2D().getWidth();
                double height = shape.getBounds2D().getHeight();
                double xx = shape.getBounds2D().getX();
                double yy = shape.getBounds2D().getY();

                g2.setColor(color.brighter());
                g2.draw(shape);
                pen1 = new BasicStroke (outline, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND);
                g2.setStroke(pen1);
                g2.setColor(color);
                //graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                //				RenderingHints.VALUE_ANTIALIAS_ON);
                //g2.setComposite(AlphaComposite.SrcOver);
                g2.setComposite(AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.8f));
                g2.fill(shape);
                for (xxx=xx; xxx < (xx+width); xxx++){
                    for (yyy=yy; yyy < (yy+height); yyy++){
                        if (yyy<image.getHeight() && xxx<image.getWidth() && yyy>0 && xxx>0){
                            KMColor back = new KMColor(new Color(image.getRGB((int)xxx,(int)yyy)));
                            back.mix(new Color(bi.getRGB((int)xxx,(int)yyy)));
                            graphics.setPaint(back.getColor());
                            graphics.setComposite(AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER, 0.8f));
                            graphics.fillRect((int)xxx,(int)yyy,1,1);
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("out of bounds " + xxx + "  " + yyy);
        }
        //graphics.drawOval(x,y,250,250);

    }

    private GeneralPath createPath(int x , int y, int r){
        Random random = new Random(System.currentTimeMillis());
        //int m = Math.min(a, b);
        int edges = 30;
        GeneralPath path = new GeneralPath();

        int xx, yy;
        double t = 2 * Math.PI * 0 / edges;

        xx = ((int) Math.round(x + r * Math.cos(t)) ) + random.nextInt(10) - 5;
        yy = ((int) Math.round(y + r * Math.sin(t)) ) + random.nextInt(10) - 5;
        path.moveTo(xx,yy);
        for (int i = 1; i < edges; i++) {
            t = 2 * Math.PI * i / edges;
            xx = ((int) Math.round(x + r * Math.cos(t)) ) + random.nextInt(10) - 5;
            yy = ((int) Math.round(y + r * Math.sin(t)) ) + random.nextInt(10) - 5;
            path.lineTo(xx,yy);

        }
        path.closePath();
        return path;
    }
    private GeneralPath createPath(int x , int y, int r, int variance){
        Random random = new Random(System.currentTimeMillis());
        //int m = Math.min(a, b);
        int edges = 30;
        GeneralPath path = new GeneralPath();

        int xx, yy;
        double t = 2 * Math.PI * 0 / edges;

        xx = ((int) Math.round(x + r * Math.cos(t)) ) + random.nextInt(variance*2) - variance;
        yy = ((int) Math.round(y + r * Math.sin(t)) ) + random.nextInt(variance*2) - variance;
        path.moveTo(xx,yy);
        for (int i = 1; i < edges; i++) {
            t = 2 * Math.PI * i / edges;
            xx = ((int) Math.round(x + r * Math.cos(t)) ) + random.nextInt(variance*2) - variance;
            yy = ((int) Math.round(y + r * Math.sin(t)) ) + random.nextInt(variance*2) - variance;
            path.lineTo(xx,yy);

        }
        path.closePath();
        return path;
    }

    private GeneralPath createPath(int x , int y, int r, int edges, int variance){
        Random random = new Random(System.currentTimeMillis());
        //int m = Math.min(a, b);

        GeneralPath path = new GeneralPath();

        int xx, yy;
        double t = 2 * Math.PI * 0 / edges;

        xx = ((int) Math.round(x + r * Math.cos(t)) ) + random.nextInt(variance*2) - variance;
        yy = ((int) Math.round(y + r * Math.sin(t)) ) + random.nextInt(variance*2) - variance;
        path.moveTo(xx,yy);
        for (int i = 1; i < edges; i++) {
            t = 2 * Math.PI * i / edges;
            xx = ((int) Math.round(x + r * Math.cos(t)) ) + random.nextInt(variance*2) - variance;
            yy = ((int) Math.round(y + r * Math.sin(t)) ) + random.nextInt(variance*2) - variance;
            path.lineTo(xx,yy);

        }
        path.closePath();
        return path;
    }

    private GeneralPath createTeardropPath(int x , int y, int w, int h, int a, int r, int edges, int variance){
        /**
         *
         * float r = 90.0;
         float a = 0.0;
         x	=	cos t

         y	=	sin t sin^m(1/2t).


         void setup()
         {
         size(400,400);
         background(200);
         smooth();
         }

         void draw()
         {
         background(200);

         a = map(mouseX,0,width,0,9);
         beginShape();
         for(int i=0; i<360; i++)
         {
         float x = width/2 + cos( radians(i) ) *r;
         //The exponent a controls the shape of the curve
         float y = height/2+ sin( radians(i) ) * pow(sin(radians(i)/2), a) *r;

         //ellipse(x,y, 10,10);
         //point(x,y);
         vertex(x,y);
         }
         endShape();
         }
         */
        Random random = new Random(System.currentTimeMillis());
        //int m = Math.min(a, b);

        GeneralPath path = new GeneralPath();
        a = 5;
        w = 100;
        h = 100;
        r = 90;
        double xx, yy;
        xx = w/2 + Math.cos( Math.toRadians(0) ) * r;
        //The exponent a controls the shape of the curve
        yy = h/2+ Math.sin( Math.toRadians(0) ) * Math.pow(Math.sin(Math.toRadians(0)/2), a) * r;
        path.moveTo(xx,yy);

        for(int i=1; i<360; i++)
        {
            xx = w/2 + Math.cos( Math.toRadians(i) ) * r;
            //The exponent a controls the shape of the curve
            yy = h/2+ Math.sin( Math.toRadians(i) ) * Math.pow(Math.sin(Math.toRadians(i)/2), a) * r;
            path.lineTo(xx,yy);
        }


        path.closePath();
        return path;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
