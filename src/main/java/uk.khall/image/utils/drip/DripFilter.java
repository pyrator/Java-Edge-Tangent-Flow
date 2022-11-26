package uk.khall.image.utils.drip;



import uk.khall.image.utils.AbstractBufferedImageOp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by keith.hall on 08/10/2014.
 */
public class DripFilter extends AbstractBufferedImageOp {
    private int dots = 45000;
    private int radius = 5;

    public int getDots() {
        return dots;
    }

    public void setDots(int dots) {
        this.dots = dots;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        int width = src.getWidth();
        int height = src.getHeight();
        Graphics2D graphics;
        int dimx;
        int dimy;
        if ( dest == null ) {
            dest = createCompatibleDestImage( src, null );


            System.out.println("width = " + src.getWidth() + " height " + src.getHeight());
            graphics = (Graphics2D) dest.getGraphics();
            graphics.setBackground(Color.white);
            graphics.setPaint(Color.white);
            dimx = dest.getWidth();
            dimy = dest.getHeight();
            graphics.clearRect(0, 0, dimx, dimy);
            graphics.fillRect(0, 0, dimx, dimy);
        } else {
            graphics = (Graphics2D) dest.getGraphics();
            dimx = dest.getWidth();
            dimy = dest.getHeight();
        }


        Drip drip = new Drip(graphics);
        Random random = new Random(System.currentTimeMillis());
        for (int n = 0; n < getDots(); n++) {
            int x = random.nextInt(dimx);
            int y = random.nextInt(dimy);
            Color color = new Color(src.getRGB(x, y));
            drip.addDrop(color, x, y, getRadius());
        }
        return dest;
    }
}
