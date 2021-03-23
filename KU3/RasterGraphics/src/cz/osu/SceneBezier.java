package cz.osu;

import java.util.ArrayList;

public class SceneBezier {
    private ArrayList<Point2D> points;
    private ArrayList<Bezier> beziers;

    public SceneBezier() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point2D point){
        points.add(point);

        int beziersCount = points.size() / 4;

        beziers = new ArrayList<>(beziersCount);

        for (int i = 0; i < beziersCount ; i++) {
            beziers.add(new Bezier(points,4*i));
        }
    }

    public void drawBezierToV_RAM(V_RAM vram){
       for (int i = 1; i < points.size(); i++) {
            GraphicsOperations.drawLine(vram,points.get(i-1).getRoundedPoint(),points.get(i).getRoundedPoint(),200);
        }

        for (Point2D point:points) {
            GraphicsOperations.drawPoint(vram,point.getRoundedPoint(),3,50);
        }

        for (Bezier bezier : beziers){
            GraphicsOperations.drawBezier(vram,bezier,10,10);
        }
    }
}
