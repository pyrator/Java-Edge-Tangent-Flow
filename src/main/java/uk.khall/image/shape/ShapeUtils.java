package uk.khall.image.shape;

import uk.khall.utils.MathUtilities;

import java.awt.*;
import java.awt.geom.*;

import static java.lang.Double.NaN;

/**
 * Created by IntelliJ IDEA.
 * User: keith.hall
 * Date: 21/05/12
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */
public class ShapeUtils {

    public static double approxArea(Area area, double flatness, int limit) {
        PathIterator i =
            new FlatteningPathIterator(area.getPathIterator(identity),
                                       flatness,
                                       limit);
        return approxArea(i);
    }

    public static double approxArea(Area area, double flatness) {
        PathIterator i = area.getPathIterator(identity, flatness);
        return approxArea(i);
    }

    public static double approxArea(PathIterator i) {
        double a = 0.0;
        double[] coords = new double[6];
        double startX = NaN, startY = NaN;
        Line2D segment = new Line2D.Double(NaN, NaN, NaN, NaN);
        while (! i.isDone()) {
            int segType = i.currentSegment(coords);
            double x = coords[0], y = coords[1];
            switch (segType) {
            case PathIterator.SEG_CLOSE:
                segment.setLine(segment.getX2(), segment.getY2(), startX, startY);
                a += hexArea(segment);
                startX = startY = NaN;
                segment.setLine(NaN, NaN, NaN, NaN);
                break;
            case PathIterator.SEG_LINETO:
                segment.setLine(segment.getX2(), segment.getY2(), x, y);
                a += hexArea(segment);
                break;
            case PathIterator.SEG_MOVETO:
                startX = x;
                startY = y;
                segment.setLine(NaN, NaN, x, y);
                break;
            default:
                throw new IllegalArgumentException("PathIterator contains curved segments");
            }
            i.next();
        }
        if (Double.isNaN(a)) {
            throw new IllegalArgumentException("PathIterator contains an open path");
        } else {
            return 0.5 * Math.abs(a);
        }
    }

    private static double hexArea(Line2D seg) {
        return seg.getX1() * seg.getY2() - seg.getX2() * seg.getY1();
    }

    private static final AffineTransform identity = AffineTransform.getQuadrantRotateInstance(0);

    public static void main (String[] params){
        Shape shaper = createPath (10,10, 4);
        PathIterator i = new FlatteningPathIterator(shaper.getPathIterator(identity),
                2,
                1);
        double[] coords = new double[6];
        while (! i.isDone()) {
            int segType = i.currentSegment(coords);
            double x = coords[0], y = coords[1];
            switch (segType) {
                case PathIterator.SEG_CLOSE:
                    System.out.println("seg close " + x + " " + y );
                    break;
                case PathIterator.SEG_LINETO:
                    System.out.println("seg lineto " + x + " " + y );
                    break;
                case PathIterator.SEG_MOVETO:
                    System.out.println("seg moveto " + x + " " + y );
                    break;
                default:
                    throw new IllegalArgumentException("PathIterator contains curved segments");
            }
            i.next();
        }
    }


    private static GeneralPath createPath(int x , int y, int r){
        //int m = Math.min(a, b);
        int edges = 6;
        GeneralPath path = new GeneralPath();

        int xx, yy;
        double t = 2 * Math.PI * 0 / edges;

        xx = ((int) Math.round(x + r * Math.cos(t)) );
        yy = ((int) Math.round(y + r * Math.sin(t)) );
        path.moveTo(xx,yy);
        for (int i = 1; i < edges; i++) {
            t = 2 * Math.PI * i / edges;
            xx = ((int) Math.round(x + r * Math.cos(t)) );
            yy = ((int) Math.round(y + r * Math.sin(t)) );
            path.lineTo(xx,yy);

        }
        path.closePath();
        return path;
    }
    public static final double EPSILON = 1e-5;
    /**
     * Expand or shrink a shape in all directions by a defined offset.
     *
     * @param s      Shape
     * @param offset Offset
     * @return New shape that was expanded or shrunk by the specified amount
     */
    public static Area grow(final Shape s, final double offset) {
        return grow(s, offset, BasicStroke.JOIN_MITER, 10f);
    }
    /**
     * Expand or shrink a shape in all directions by a defined offset.
     *
     * @param s          Shape
     * @param offset     Offset to expand/shrink
     * @param join       Method for handling edges (see BasicStroke)
     * @param miterlimit Limit for miter joining method
     * @return New shape that is expanded or shrunk by the specified amount
     */
    public static Area grow(final Shape s, final double offset, int join,
                            float miterlimit) {
        Area shape = new Area(s);

        if (MathUtilities.almostEqual(offset, 0.0, EPSILON)) {
            return shape;
        }

        Stroke stroke = new BasicStroke((float) Math.abs(2.0 * offset),
                BasicStroke.CAP_SQUARE, join, miterlimit);
        Area strokeShape = new Area(stroke.createStrokedShape(s));

        if (offset > 0.0) {
            shape.add(strokeShape);
        } else {
            shape.subtract(strokeShape);
        }

        return shape;
    }
}
