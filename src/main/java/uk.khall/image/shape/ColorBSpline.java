package uk.khall.image.shape;

import java.util.ArrayList;

public class ColorBSpline {
    /**
     * Our order which is the nth_degree+1
     */
    private int order=4;
    public ColorBSpline(){

    }

    public int getOrder() {
        return order;
    }



    public void setOrder(int order) {
        this.order = order;
    }


    ArrayList<ColorPointObject> colorControlObjects;
    public void setColorControlObjects(ArrayList<ColorPointObject> controlObjects) {
        this.colorControlObjects = controlObjects;
    }
    /**
     * Take in four PointObject Objects as containing control points and calculate our curve.
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    private ColorPointObject calcPoint(ColorPointObject p1, ColorPointObject p2, ColorPointObject p3, ColorPointObject p4){

        ColorPointObject retobject=new ColorPointObject();

        float basisax=(float)(((-1*p1.getX())+(3*p2.getX())-(3*p3.getX())+p4.getX())/6.0);
        float basisay=(float)(((-1*p1.getY())+(3*p2.getY())-(3*p3.getY())+p4.getY())/6);

        float basisbx=(float)(((3*p1.getX())-(6*p2.getX())+(3*p3.getX()))/6);
        float basisby=(float)(((3*p1.getY())-(6*p2.getY())+(3*p3.getY()))/6);

        float basiscx=(float)(((-3*p1.getX())+(3*p3.getX()))/6);
        float basiscy=(float)(((-3*p1.getY())+(3*p3.getY()))/6);

        float basisdx=(float)((p1.getX()+(4*p2.getX())+p3.getX())/6);
        float basisdy=(float)((p2.getY()+(4*p2.getY())+p3.getY())/6);

        float t;

        //get the vector determinant
        for(int i=0;i<(order-1);i++){
            t=i/order;
            retobject.setX(((basiscx+t*(basisbx+t*basisax))*t+basisdx));
            retobject.setY(((basiscy+t*(basisby+t*basisay))*t+basisdy));

        }

        //return the point object
        return retobject;
    }


    /**
     * Control the creation of bspline points.
     * @return
     */
    private ArrayList<ColorPointObject> getSplinePoints(){

        ArrayList<ColorPointObject> po=new ArrayList<ColorPointObject>();

        if(colorControlObjects.size()>0){
            //calc bspline
            int i=0;

            //add the initial points
            while(i<3 & i<colorControlObjects.size()){
                po.add(colorControlObjects.get(i));
                i++;
            }

            //add the remaining calculated points
            if(colorControlObjects.size()>3){
                for(i=3;i<colorControlObjects.size()-3;i++){
                    po.add(calcPoint(colorControlObjects.get(i),colorControlObjects.get((i+1)),colorControlObjects.get((i+2)),colorControlObjects.get((i+3))));
                }
            }
        }
        else{
            try{
                throw new NullPointerException("No Points Provided!");
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }

        return po;
    }

    /**
     * Run the program and get an ArrayList<PointObject> of spline points.
     * The number of points returned depends on the number of control points provided.
     * @return
     */
    public ArrayList<ColorPointObject> run(){
        return getSplinePoints();
    }
}
