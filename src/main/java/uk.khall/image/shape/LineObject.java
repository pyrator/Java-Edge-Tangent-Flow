package uk.khall.image.shape;

public class LineObject {
    private int startx;
    private int endx;
    private int starty;
    private int endy;
    public LineObject(){

    }

    public LineObject(int startx, int endx, int starty, int endy) {
        this.startx = startx;
        this.endx = endx;
        this.starty = starty;
        this.endy = endy;
    }

    public int getStartx() {
        return startx;
    }

    public void setStartx(int startx) {
        this.startx = startx;
    }

    public int getEndx() {
        return endx;
    }

    public void setEndx(int endx) {
        this.endx = endx;
    }

    public int getStarty() {
        return starty;
    }

    public void setStarty(int starty) {
        this.starty = starty;
    }

    public int getEndy() {
        return endy;
    }

    public void setEndy(int endy) {
        this.endy = endy;
    }
}
