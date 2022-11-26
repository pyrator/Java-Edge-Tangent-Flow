package uk.khall.image.shape;



import uk.khall.image.utils.AbstractBufferedImageOp;
import uk.khall.image.utils.drip.Drip;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by keith.hall on 08/10/2014.
 */
public class BrushFilter extends AbstractBufferedImageOp {
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
            graphics = dest.createGraphics();
            graphics.setBackground(Color.white);
            graphics.setPaint(Color.white);
            dimx = dest.getWidth();
            dimy = dest.getHeight();
            graphics.clearRect(0, 0, dimx, dimy);
            graphics.fillRect(0, 0, dimx, dimy);
        } else {
            graphics = dest.createGraphics();
            dimx = dest.getWidth();
            dimy = dest.getHeight();
        }
        BSplineBrushStroke brushStroke = new BSplineBrushStroke(graphics,dimx,dimy,src);


        Random random = new Random(System.currentTimeMillis());
        for (int n = 0; n < getDots(); n++) {
            int x = random.nextInt(dimx);
            int x2 = x + random.nextInt(radius*2)-radius;
            if(x2<0)
                x2=0;
            if(x2>=dimx)
                x2=dimx-1;


            int y = random.nextInt(dimy);
            int y2 = y + random.nextInt(radius*2)-radius;
            if(y2<0)
                y2=0;
            if(y2>=dimy)
                y2=dimy-1;
            Color color = new Color(src.getRGB(x, y));
            Color endColor = new Color(src.getRGB(x2, y2));
            ArrayList<Color> secondaryColors = new ArrayList<>();
            secondaryColors.add(color.brighter());
            secondaryColors.add(color.darker());
            secondaryColors.add(color);
            ArrayList<Color> secondaryEndColors = new ArrayList<>();
            secondaryColors.add(endColor.brighter());
            secondaryColors.add(endColor.darker());
            secondaryColors.add(color);
            brushStroke.addTaperingColorBrushStroke(color, endColor, secondaryColors, secondaryEndColors,x, x2, y, y2, radius*2);
        }
        return dest;
    }
}
