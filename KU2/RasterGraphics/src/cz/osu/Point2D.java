package cz.osu;

import java.awt.*;

public class Point2D {

    public double[] vector;

    public Point2D(){

        vector = new double[3];
    }

    public Point2D(double x, double y){

        vector = new double[3];

        vector[0] = x;
        vector[1] = y;
        vector[2] = 1;
    }

    public Point2D applyMatrix(Matrix2D a){

        Point2D export = new Point2D();

        for (int i = 0; i < 3; i++) {
            export.vector[0] += a.matrix[0][i] * vector[i];
            export.vector[1] += a.matrix[1][i] * vector[i];
            export.vector[2] += a.matrix[2][i] * vector[i];
        }
        return export;
    }

    public Point2D clone(){

        return new Point2D(vector[0], vector[1]);
    }

    public Point getRoundedPoint(){

        return new Point((int)Math.round(vector[0]), (int)Math.round(vector[1]));
    }

}
