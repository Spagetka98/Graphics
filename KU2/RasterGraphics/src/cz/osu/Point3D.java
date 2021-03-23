package cz.osu;

import java.awt.*;

public class Point3D {
    public double [] data;

    public Point3D(){
        data = new double[4];
    }

    public Point3D(double x, double y, double z){
        data = new double[4];

        data[0]=x;
        data[1]=y;
        data[2]=z;
        data[3]=1;
    }

    public Point3D applyMatrix(Matrix3D a){

        Point3D export = new Point3D();

        for (int i = 0; i < 4; i++) {
            export.data[0] += a.matrix[0][i] * data[i];
            export.data[1] += a.matrix[1][i] * data[i];
            export.data[2] += a.matrix[2][i] * data[i];
            export.data[3] += a.matrix[3][i] * data[i];
        }
        return export;
    }

    public Point getRoundedPoint(){
        return new Point((int)Math.round(data[0]), (int)Math.round(data[1]));
    }

    public Point3D vector(Point3D p){
        return new Point3D(data[0] - p.data[0],data[1] - p.data[1],data[2] - p.data[2]);
    }
}
