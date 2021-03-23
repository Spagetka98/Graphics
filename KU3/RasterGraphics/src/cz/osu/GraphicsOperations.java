package cz.osu;

import java.awt.*;

public class GraphicsOperations {

    public static void fillBrightness(V_RAM vram, int brightness){

        brightness = Math.min(255, Math.max(0, brightness));

        for(int y = 0; y < vram.getHeight(); y++)
            for(int x = 0; x < vram.getWidth(); x++)
                vram.getRawData()[y][x] = brightness;
    }

    public static void drawLine(V_RAM vram, Line2D line, int brightness){
        line(vram, line, brightness);
    }
    public static void drawLine(V_RAM vram, Point2D a, Point2D b, int brightness){
        line(vram, a,b, brightness);
    }

    public static void drawLine(V_RAM vram, Point a, Point b, int brightness){
        line(vram, a,b, brightness);
    }

    public static void drawPoint(V_RAM v_ram,Point point,int size ,int brightness){
        if (size == 0)
            v_ram.setPixelSafe(point.getX(), point.getY(),brightness);
        else if (size > 0){
            Point2D start = new Point2D(point.getX()-size, point.getY()-size);
            for (int i = start.getRoundedPoint().x; i <= start.getRoundedPoint().x + size*2 ; i++) {
                for (int o = start.getRoundedPoint().y; o <= start.getRoundedPoint().y + size*2 ; o++) {
                    v_ram.setPixelSafe(i,o,brightness);
                }
            }

        }
    }

    private static void line(V_RAM vram, Line2D line, int brightness) {
        Point pz = line.pointA.getRoundedPoint();
        Point pk = line.pointB.getRoundedPoint();

        lineBase(vram,pz,pk,brightness);
    }

    private static void line(V_RAM vram, Point2D a, Point2D b, int brightness) {
        Point pz = a.getRoundedPoint();
        Point pk = b.getRoundedPoint();

        lineBase(vram,pz,pk,brightness);
    }

    private static void line(V_RAM vram, Point a, Point b, int brightness) {
        lineBase(vram,a,b,brightness);
    }

    private static void lineBase(V_RAM vram, Point pz, Point pk, int brightness){

        int dx = pk.x - pz.x;
        int dy = pk.y - pz.y;

        //Speciální případy úsečky
        if(pk.x == pz.x && pk.y == pz.y) {
            vram.setPixelSafe(pz.x, pz.y, brightness);
            return;
        }else if (dx == 0){

            if (pz.y > pk.y) switchPoints(pz,pk);
            verticalLine(pz, pk,vram,brightness);
            return;
        }else if(dy == 0){

            if (pz.x > pk.x) switchPoints(pz,pk);
            horizontalLine(pz, pk,vram,brightness);
            return;
        }

        if(Math.abs(dx) < Math.abs(dy)){
            if (pz.y > pk.y) switchPoints(pz,pk);
            bresenhamHigh(pz,pk,vram,brightness);
        }else{
            if (pz.x > pk.x) switchPoints(pz,pk);
            bresenhamLow(pz,pk,vram,brightness);
        }

    }

    private static void bresenhamHigh(Point pz, Point pk, V_RAM vram, int brightness) {

        int d = pz.x > pk.x ? -1 : 1;
        int h1 = 2*Math.abs(pk.x - pz.x);
        int h = h1-(pk.y - pz.y);
        int h2 = h1 - 2*(pk.y - pz.y);

        for (int y = pz.y; y < pk.y; y++) {
            vram.setPixelSafe(pz.x,y,brightness);
            if (h > 0){
                pz.x += d;
                h += h2;
            }else
                h += h1;
        }

        vram.setPixelSafe(pk.x, pk.y, brightness);
    }

    private static void bresenhamLow(Point pz, Point pk, V_RAM vram, int brightness) {

        int d = pz.y > pk.y ? -1 : 1;
        int h1 = 2*Math.abs(pk.y - pz.y);
        int h = h1-(pk.x - pz.x);
        int h2 = h1 - 2*(pk.x - pz.x);

        for (int x = pz.x; x < pk.x; x++) {
            vram.setPixelSafe(x,pz.y,brightness);
            if (h > 0){
                pz.y += d;
                h += h2;
            }else
                h += h1;
        }

        vram.setPixelSafe(pk.x, pk.y, brightness);
    }

    private static void DDAHigh(Point pz, Point pk, V_RAM vram, int brightness) {
        float a = (float)(pk.x - pz.x) / (pk.y - pz.y);

        float x = pz.x;
        for(int y = pz.y+1; y <= pk.y; y++){
            x += a;
            vram.setPixelSafe(Math.round(x), y,brightness);
        }
    }

    private static void DDALow(Point pz, Point pk, V_RAM vram, int brightness) {
        float a = (float)(pk.y - pz.y) / (pk.x - pz.x);

        float y = pz.y;
        for(int x = pz.x+1; x <= pk.x; x++){
            y += a;
            vram.setPixelSafe(x,Math.round(y),brightness);
        }
    }

    private static void naiveLineHigh(Point pz, Point pk, V_RAM vram, int brightness) {
        System.out.println("Úsečka pro velký sklon nad 45");

        float a = (float)(pk.x - pz.x) / (pk.y - pz.y);

        float b = (float)(pk.y * pz.x - pz.y* pk.x) / (pk.y - pz.y);
        int x;
        for(int y = pz.y; y <= pk.y; y++){
            x = Math.round(a * y + b);
            vram.setPixelSafe(x,y,brightness);
        }
    }

    private static void naiveLineLow(Point pz, Point pk, V_RAM vram, int brightness) {
        float a = (float)(pk.y - pz.y) / (pk.x - pz.x);

        float b = (float)(pk.x * pz.y - pz.x* pk.y) / (pk.x - pz.x);
        int y;
        for(int x = pz.x; x <= pk.x; x++){
            y = Math.round(a * x + b);
            vram.setPixelSafe(x,y,brightness);
        }
    }

    private static void verticalLine(Point pz, Point pk, V_RAM vram, int brightness) {
        int x= pz.x;
        for(int y = pz.y; y <= pk.y; y++){
            vram.setPixelSafe(x,y,brightness);
        }
    }

    private static void horizontalLine(Point pz, Point pk, V_RAM vram, int brightness) {
        int y = pz.y;
        for(int x = pz.x; x <= pk.x; x++){
            vram.setPixelSafe(x,y,brightness);
        }
    }

    private static void switchPoints(Point pz, Point pk) {
        int x = pz.x;
        int y = pz.y;

        pz.x = pk.x;
        pz.y = pk.y;

        pk.x = x;
        pk.y = y;
    }

    public static void drawClippingWindow(V_RAM vram, Point2D[] cp, int brightness) {
        for (int i = 0; i < cp.length-1; i++) {
            drawLine(vram,cp[i],cp[i+1],brightness);
        }
    }

    public static void drawBezier(V_RAM vram, Bezier bezier, int steps, int brightness) {
        bezier.calculateValues();

        Point startP = bezier.p0.getRoundedPoint();
        GraphicsOperations.drawPoint(vram,startP,1,brightness);

        for (int i = 1; i <= steps; i++){
            Point endP = bezier.getPointForT(1.0 * i / steps).getRoundedPoint();

            GraphicsOperations.drawLine(vram,new Point(startP),new Point(endP),brightness);

            GraphicsOperations.drawPoint(vram,endP,1,brightness);

            startP = endP;
        }
    }

}
