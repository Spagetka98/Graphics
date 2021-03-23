package cz.osu;

public class Matrix3D {
    public double[][] matrix;

    public Matrix3D(int size){

        matrix = new double[size][size];

    }

    public static Matrix3D createIdentityMatrix3D(){
        Matrix3D export = new Matrix3D(4);
        export.matrix[0][0]=1;
        export.matrix[1][1]=1;
        export.matrix[2][2]=1;
        export.matrix[3][3]=1;
        return export;
    }
    public static Matrix3D createScaleMatrix3D(double scale){
        Matrix3D export = new Matrix3D(4);
        export.matrix[0][0] = scale;
        export.matrix[1][1] = scale;
        export.matrix[2][2] = scale;
        export.matrix[3][3] = 1;
        return export;
    }

    public static Matrix3D createTranslationMatrix3D(double tX,double tY, double tZ){
        Matrix3D export = new Matrix3D(4);
        export.matrix[0][0] =1;
        export.matrix[1][1] =1;
        export.matrix[2][2] =1;
        export.matrix[3][3] =1;

        export.matrix[0][3] =tX;
        export.matrix[1][3] =tY;
        export.matrix[2][3] =tZ;
        return export;
    }

    public static Matrix3D createRotationMatrix3D_XY(double angle){
        angle = Math.toRadians(angle);

        Matrix3D export = new Matrix3D(4);
        export.matrix[0][0] = Math.cos(angle);
        export.matrix[1][1] = export.matrix[0][0];
        export.matrix[1][0] = Math.sin(angle);
        export.matrix[0][1] = -export.matrix[1][0];
        export.matrix[2][2] = 1;
        export.matrix[3][3] = 1;
        return export;
    }

    public static Matrix3D createRotationMatrix3D_YZ(double angle){
        angle = Math.toRadians(angle);

        Matrix3D export = new Matrix3D(4);
        export.matrix[0][0] = 1;
        export.matrix[1][1] = Math.cos(angle);
        export.matrix[2][2] = export.matrix[1][1];
        export.matrix[2][1] = Math.sin(angle);
        export.matrix[1][2] = -export.matrix[2][1];
        export.matrix[3][3] = 1;
        return export;
    }

    public static Matrix3D createRotationMatrix3D_ZX(double angle){
        angle = Math.toRadians(angle);

        Matrix3D export = new Matrix3D(4);
        export.matrix[0][0] = Math.cos(angle);
        export.matrix[1][1] = 1;
        export.matrix[2][2] = export.matrix[0][0];
        export.matrix[2][0] = -Math.sin(angle);
        export.matrix[0][2] = -export.matrix[2][0];
        export.matrix[3][3] = 1;
        return export;
    }

    public static Matrix3D createPerspective(double distance){
        Matrix3D export = new Matrix3D(4);

        export.matrix[0][0]=1;
        export.matrix[1][1]=1;
        export.matrix[3][3]=1;
        export.matrix[3][2] = 1/distance;
        return export;
    }

    public static Matrix3D multiply(Matrix3D a,Matrix3D b){
        Matrix3D export = new Matrix3D(4);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    export.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }

        return export;
    }

    public static Matrix3D createOrthogonalMatrixXY(){
        Matrix3D export = new Matrix3D(4);

        export.matrix[0][0]=1;
        export.matrix[1][1]=1;
        export.matrix[3][3]=1;

        return export;
    }

    public static Matrix3D createOrthogonalMatrixYZ(){
        Matrix3D export = new Matrix3D(4);

        export.matrix[1][1]=1;
        export.matrix[2][2]=1;
        export.matrix[3][3]=1;

        return export;
    }

        public static Matrix3D createOrthogonalMatrixXZ(){
        Matrix3D export = new Matrix3D(4);

        export.matrix[1][1]=1;
        export.matrix[2][2]=1;
        export.matrix[3][3]=1;

        return export;
    }

}
