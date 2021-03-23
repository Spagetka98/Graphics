package cz.osu;

public class Triangle3D {
    public int pA;
    public int pB;
    public int pC;
    public Boolean visible;

    public Triangle3D(int pa,int pb,int pc){
        this.pA=pa;
        this.pB=pb;
        this.pC=pc;
    }

    public void setVisibility(Point3D[] p, Point3D viewVec){
        Point3D u = p[pB].vector(p[pA]);
        Point3D v = p[pC].vector(p[pB]);

        Point3D normal = MathematicalOperations.crossProduct(u,v);

        double cos = MathematicalOperations.dotProduct(normal,viewVec);

        if(cos < 0){
            visible = true;
        }else{
            visible = false;
        }
    }
}
