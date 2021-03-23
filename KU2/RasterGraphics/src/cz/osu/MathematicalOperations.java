package cz.osu;

public class MathematicalOperations {

    public static Point3D crossProduct(Point3D u,Point3D v){
        return new Point3D(
                (u.data[1] * v.data[2]) - (u.data[2]*v.data[1]),
                (u.data[2] * v.data[0]) - (u.data[0]*v.data[2]),
                (u.data[0] * v.data[1]) - (u.data[1]*v.data[0])
        );
    }

    public static double dotProduct(Point3D normal,Point3D view){
        double product = 0;

        for (int i = 0; i < 3; i++)
            product += normal.data[i] * view.data[i];

        return product;
    }
}
