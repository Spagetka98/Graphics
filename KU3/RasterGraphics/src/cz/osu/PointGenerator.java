package cz.osu;

import java.util.ArrayList;
import java.util.Arrays;

public class PointGenerator {

    public static Point2D[] generatorStripPoints(int count){
        Point2D[] export = new Point2D[2*count];

        for (int i = 0; i < count ; i++) {
            export[i] = new Point2D(10*i+5,20);
            export[i+count] = new Point2D(10*i+5, 30);
        }

        return export;
    }

    public static  Point2D[] generateFanPoints(int count){
        Point2D[] export = new Point2D[count];

        for (int i = 0; i < count; i++) {
            double t = 2 * Math.PI * i/count;
            export[i] = new Point2D((int)(25+10 * Math.cos(t)),(int) (25 +10*Math.sin(t)));
        }

        return export;
    }

    public static ArrayList<ArrayList<Point2D>> generateBezierPoints(ArrayList<Point2D> points){

        ArrayList<ArrayList<Point2D>> beziersPoints =
                new ArrayList<>();

        if (points.size() >= 3){
            ArrayList<Point2D> temp = new ArrayList<>();
            temp.add(0,points.get(0));
            temp.addAll(points);
            temp.add(points.get(points.size() -1));

            ArrayList<Point2D> ls = new ArrayList<>();
            ArrayList<Point2D> rs = new ArrayList<>();

            for (int i = 1; i <= points.size(); i++) {
                Point2D l = new Point2D(
                        temp.get(i).getRoundedPoint().x - ((double)(temp.get(i+1).getRoundedPoint().x-temp.get(i-1).getRoundedPoint().x)/6),
                        temp.get(i).getRoundedPoint().y - ((double)(temp.get(i+1).getRoundedPoint().y-temp.get(i-1).getRoundedPoint().y)/6)
                );
                Point2D r = new Point2D(
                        temp.get(i).getRoundedPoint().x + ((double)(temp.get(i+1).getRoundedPoint().x-temp.get(i-1).getRoundedPoint().x)/6),
                        temp.get(i).getRoundedPoint().y + ((double)(temp.get(i+1).getRoundedPoint().y-temp.get(i-1).getRoundedPoint().y)/6)
                );

                ls.add(l);
                rs.add(r);
            }

            for (int i = 1; i < points.size(); i++) {
                ArrayList<Point2D> points2D = new ArrayList<>();
                points2D.add(temp.get(i));
                points2D.add(rs.get(i-1));
                points2D.add(ls.get(i));
                points2D.add(temp.get(i+1));
                beziersPoints.add(points2D);
            }
        }

        return beziersPoints;
    }

}
