package uk.khall.image.utils.pallet;

import uk.khall.image.utils.AbstractBufferedImageOp;
import uk.khall.image.utils.Posterize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by keith.hall on 08/10/2014.
 */
public class PosterizeFilter extends AbstractBufferedImageOp {
    private int level = 3;
    private Posterize posterize;
    
    
	public PosterizeFilter(){
		posterize = new Posterize();
	}


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Color> getColors(){
    	return posterize.getColors();
    }

    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        dest = posterize.apply(src, level);


        return dest;
    }
}
