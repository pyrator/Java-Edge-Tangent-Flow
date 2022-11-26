package uk.khall.image.utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by keith.hall on 09/10/2014.
 */
public class FilterChain {
    ArrayList<AbstractBufferedImageOp> filterChainList = new ArrayList<AbstractBufferedImageOp>();
    public FilterChain(){

    }
    public void addFilter(AbstractBufferedImageOp filter){
        filterChainList.add(filter);
    }
    public BufferedImage runFilters(BufferedImage mainImage){
        for (AbstractBufferedImageOp filter: filterChainList ){
            mainImage = filter.filter(mainImage,null);
        }
        return mainImage;
    }
    public BufferedImage runFilters(BufferedImage mainImage, BufferedImage initialImage){
        for (AbstractBufferedImageOp filter: filterChainList ){
            mainImage = filter.filter(mainImage,initialImage);
        }
        return mainImage;
    }
}
