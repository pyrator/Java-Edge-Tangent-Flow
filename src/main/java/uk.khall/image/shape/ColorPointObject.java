package uk.khall.image.shape;

import java.awt.*;

public class ColorPointObject extends PointObject{
    private Color color;

    public ColorPointObject(){

    }
    public ColorPointObject(Color color, int id, int x, int y){
        this.color = color;
        this.setId(id);
        this.setX(x);
        this.setY(y);
    }

    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return color;
    }

}
