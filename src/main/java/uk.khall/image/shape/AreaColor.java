package uk.khall.image.shape;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: keith.hall
 * Date: 23/05/12
 * Time: 08:46
 * To change this template use File | Settings | File Templates.
 */
public class AreaColor implements Comparable {

    private Area area;
    private Color color;
    private double size;
    private Point2D center;
    private ArrayList<Point> pixelList;
    private ArrayList<Point> reducedPixelList;
    private ArrayList<Integer> ridgeSet;
    private ArrayList<Area> subareas;

    public AreaColor(Area area, Color color, double size){
        this.area = area;
        this.color = color;
        this.size = size;
        this.pixelList = new ArrayList<Point>();
        this.ridgeSet = new ArrayList<Integer>();
        //createRidgeValues();
    }
    public AreaColor(Area area, Color color, double size, ArrayList<Point> pixelList){
        this.area = area;
        this.color = color;
        this.size = size;
        this.pixelList = pixelList;
        this.ridgeSet = new ArrayList<Integer>();
        //createRidgeValues();
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ArrayList<Point> getPixelList() {
        return pixelList;
    }
    public void createReducedRandomPixeList(){
        Random random = new Random(System.currentTimeMillis());
        int inverseColor = 255-getColor().getRed();
        reducedPixelList = new ArrayList<>();
        for (int nn = 0; nn < inverseColor; nn++) {
            int randomPoint = random.nextInt(pixelList.size());
            java.awt.Point pp = pixelList.get(randomPoint);
            reducedPixelList.add(pp);
        }
    }
    public ArrayList<Point> getReducedRandomPixelList(){
        if (reducedPixelList == null)
            createReducedRandomPixeList();
        return reducedPixelList;
    }
    public void setPixelList(ArrayList<Point> pixelList) {
        this.pixelList = pixelList;
        //this.ridgeSet = new ArrayList<>();
        //createRidgeValues();
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public ArrayList<Integer> getRidgeSet(){
        if(subareas != null && ridgeSet.isEmpty()) {
            createRidgeValues();
        }
        return ridgeSet;
    }

    public void createRidgeValues(){

        for (int i = 0; i < pixelList.size(); i++) {
            ridgeSet.add(0);
        }
        int pos = 0;
        for (Point point : pixelList) {
            int xx = point.x;
            int yy = point.y;
            Point2D point2D = new Point2D.Double(xx,yy);
            int count = 0;

            if (area.contains(point2D)){
                count++;
                if (subareas!=null) {
                    for (Area sub : subareas) {
                        if (sub.contains(point2D)) {
                            count++;
                        }
                    }
                }
            }
            ridgeSet.set(pos, count);
            pos++;
        }
    }
    public ArrayList<Integer> getRidgeSetOld(){
        if(ridgeSet.isEmpty())
            createRidgeValuesOld();
        return ridgeSet;
    }



    private void createRidgeValuesOld(){
        int count = 0;
        for (int i =0; i < pixelList.size();i++){
            ridgeSet.add(0);
        }
        for(int c= 0; c < 4; c++ ){
            count = 0;
            for (Point point : pixelList) {
                int xx = point.x;
                int yy = point.y;
                boolean boundary = false;
                for (int y = -1; y < 2; y++) {
                    for (int x = -1; x < 2; x++) {
                        if (x != 0 && y != 0) {
                            Point testpoint = new Point(xx + x, yy + y);
                            if (!pixelList.contains(testpoint)) {
                                boundary = true;
                            }
                        }
                    }
                }
                if (!boundary) {
                    ridgeSet.set(count, 1);
                }
                count++;
            }
        }

        int c = 2;
        count = 0;
        for (Point point : pixelList) {
            int xx = point.x;
            int yy = point.y;
            int currentVal = ridgeSet.get(count);
            if(currentVal==1) {
                boolean boundary = false;
                for (int y = -2; y < 3; y=y+2) {
                    for (int x = -2; x < 3; x=x+2) {
                        if (x != 0 && y != 0) {
                            Point testpoint = new Point(xx + x, yy + y);
                            if (!pixelList.contains(testpoint)) {
                                boundary = true;
                            }

                        }
                        if((Math.abs(x)==1 && (Math.abs(y)==2) || (Math.abs(x)==2 && (Math.abs(y)==1)))){
                            Point testpoint = new Point(xx + x, yy + y);
                            if (!pixelList.contains(testpoint)) {
                                boundary = true;
                            }
                        }
                    }

/*                    if(!boundary){
                        Point testpoint = new Point(xx + -1, yy + -2);

                    }*/

                    if (!boundary) {
                        ridgeSet.set(count, c);
                    }
                }
            }

            count++;

        }
    }

    public ArrayList<Area> getSubareas() {
        return subareas;
    }

    public void setSubareas(ArrayList<Area> subareas) {
        this.subareas = subareas;
    }

    public int compareTo(Object o) {
        if (o instanceof AreaColor){
            AreaColor testarea = (AreaColor)o;
            if(this.size < testarea.getSize()) {
                return -1;
            } else if (this.size > testarea.getSize()){
                return 1;

            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

}
