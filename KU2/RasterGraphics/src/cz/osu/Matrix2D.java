package cz.osu;

public class Matrix2D {

    public double[][] matrix;

    public Matrix2D(int size){

        matrix = new double[size][size];

    }

    public static Matrix2D createIdentityMatrix(){

        Matrix2D export = new Matrix2D(3);
        export.matrix[0][0]=1;
        export.matrix[1][1]=1;
        export.matrix[2][2]=1;
        return export;
    }

    public static Matrix2D createTranslationMatrix(double dX, double dY){
        //3 bo jsme ve 2D -- x,y,1
        Matrix2D export = new Matrix2D(3);
        //Index - řádku/sloupce
        export.matrix[0][0] = 1;
        export.matrix[1][1] = 1;
        export.matrix[2][2] = 1;
        export.matrix[0][2] = dX;
        export.matrix[1][2] = dY;

        return export;
    }

    public static Matrix2D createRotationMatrix(double angle){

        Matrix2D export = new Matrix2D(3);

        export.matrix[0][0] = Math.cos(angle);
        export.matrix[1][1] = export.matrix[0][0];
        export.matrix[1][0] = Math.sin(angle);
        export.matrix[0][1] = -export.matrix[1][0];
        export.matrix[1][2] = 1;

        return export;
    }

    public static Matrix2D createScalingMatrix(double scale){

        Matrix2D export = new Matrix2D(3);
        export.matrix[0][0]= scale;
        export.matrix[1][1]=scale;
        export.matrix[2][2]=1;
        return export;
    }

    public static Matrix2D createScalingMatrix(double sX, double sY){

        Matrix2D export = new Matrix2D(3);
        export.matrix[0][0]= sX;
        export.matrix[1][1]= sY;
        export.matrix[2][2]=1;
        return export;
    }

    public static Matrix2D multiplyMatrix(Matrix2D a, Matrix2D b) {

        Matrix2D export = new Matrix2D(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    export.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }

        return export;
    }
}
