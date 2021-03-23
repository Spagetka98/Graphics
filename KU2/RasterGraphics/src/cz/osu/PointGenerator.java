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

    public static TriangleIn2D[] generateStripTriangles(Point2D[] points){
        ArrayList<TriangleIn2D> export = new ArrayList<>();
        int lineLength = points.length/2;

        for (int i = 0; i< lineLength-1; i++){
            export.add(new TriangleIn2D(i,i + lineLength,i+lineLength+1));
            export.add(new TriangleIn2D(i,i+lineLength+1,i+1));
        }

        return export.toArray(new TriangleIn2D[0]);
    }

    public static TriangleIn2D[] generateFanTriangles(Point2D[] points){
        ArrayList<TriangleIn2D> export = new ArrayList<>();

        for (int i = 1; i < points.length - 1;i++){
            export.add(new TriangleIn2D(0,i,i+1));
        }

        return export.toArray(new TriangleIn2D[0]);
    }

    public static ArrayList<Point3D> generateFan3DPoints(int count,double yCoor){
        Point3D[] export = new Point3D[count];

        for (int i = 0; i < count ; i++) {
            double t = 2 * Math.PI * i /count;
            export[i] = new Point3D(Math.cos(t),yCoor,Math.sin(t));
        }

        return new ArrayList<>(Arrays.asList(export));
    }

    public static ArrayList<Point3D> generatorStrip3DPoints(int count){
        Point3D[] export = new Point3D[2*count];

        for (int i = 0; i < count ; i++) {
            export[i] = new Point3D(10*i+5,20,0);
            export[i+count] = new Point3D(10*i+5, 30,0);
        }

        return new ArrayList<>(Arrays.asList(export));
    }

    public static ArrayList<Triangle3D> generateCubeTriangles(){
        ArrayList<Triangle3D> export = new ArrayList<>();

        export.add(new Triangle3D(0,1,2));
        export.add(new Triangle3D(0,2,3));

        export.add(new Triangle3D(6,5,4));
        export.add(new Triangle3D(6,4,7));

        export.add(new Triangle3D(4,0,3));
        export.add(new Triangle3D(4,3,7));

        export.add(new Triangle3D(5,6,2));
        export.add(new Triangle3D(5,2,1));

        export.add(new Triangle3D(3,2,6));
        export.add(new Triangle3D(3,6,7));

        export.add(new Triangle3D(4,5,1));
        export.add(new Triangle3D(4,1,0));

        return export;
    }

}
