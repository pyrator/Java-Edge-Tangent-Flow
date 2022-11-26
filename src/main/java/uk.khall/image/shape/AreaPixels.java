package uk.khall.image.shape;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;

/**
 * Created by keith.hall on 29/10/2014.
 */
public class AreaPixels {

    private Area area;

    private ArrayList<Point> pixelList;


    public AreaPixels(Area area, ArrayList<Point> pixelList){
        this.area = area;
        this.pixelList = pixelList;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public ArrayList<Point> getPixelList() {
        return pixelList;
    }

    public void setPixelList(ArrayList<Point> pixelList) {
        this.pixelList = pixelList;
    }
}
