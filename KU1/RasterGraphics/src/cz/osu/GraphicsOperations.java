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

    public static void drawTriangle(V_RAM vram, Triangle2D triangle, int brightness){

        line(vram, new Line2D(triangle.pointA, triangle.pointB), brightness);
        line(vram, new Line2D(triangle.pointB, triangle.pointC), brightness);
        line(vram, new Line2D(triangle.pointC, triangle.pointA), brightness);
    }

    private static void line(V_RAM vram, Line2D line, int brightness) {

        Point pz = line.pointA.getRoundedPoint();
        Point pk = line.pointB.getRoundedPoint();

        int dx = pk.x - pz.x;
        int dy = pk.y - pz.y;

        //Speciální případy úsečky
        if(pk.x == pz.x && pk.y == pz.y) {

            vram.setPixel(pz.x, pz.y, brightness);
            return;
        }else if (dx == 0){

            if (pz.y > pk.y) switchPoints(pz,pk);
            verticalLine(pz, pk,vram,brightness);
            System.out.println("Svislá úsečka");
            return;
        }else if(dy == 0){

            if (pz.x > pk.x) switchPoints(pz,pk);
            horizontalLine(pz, pk,vram,brightness);
            System.out.println("Vodorovná úsečka");
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
            vram.setPixel(pz.x,y,brightness);
            if (h > 0){
                pz.x += d;
                h += h2;
            }else
                h += h1;
        }

        vram.setPixel(pk.x, pk.y, brightness);
    }

    private static void bresenhamLow(Point pz, Point pk, V_RAM vram, int brightness) {

        int d = pz.y > pk.y ? -1 : 1;
        int h1 = 2*Math.abs(pk.y - pz.y);
        int h = h1-(pk.x - pz.x);
        int h2 = h1 - 2*(pk.x - pz.x);

        for (int x = pz.x; x < pk.x; x++) {
            vram.setPixel(x,pz.y,brightness);
            if (h > 0){
                pz.y += d;
                h += h2;
            }else
                h += h1;
        }

        vram.setPixel(pk.x, pk.y, brightness);
    }

    private static void DDAHigh(Point pz, Point pk, V_RAM vram, int brightness) {
        float a = (float)(pk.x - pz.x) / (pk.y - pz.y);

        float x = pz.x;
        for(int y = pz.y+1; y <= pk.y; y++){
            x += a;
            vram.setPixel(Math.round(x), y,brightness);
        }
    }

    private static void DDALow(Point pz, Point pk, V_RAM vram, int brightness) {
        float a = (float)(pk.y - pz.y) / (pk.x - pz.x);

        float y = pz.y;
        for(int x = pz.x+1; x <= pk.x; x++){
            y += a;
            vram.setPixel(x,Math.round(y),brightness);
        }
    }

    private static void naiveLineHigh(Point pz, Point pk, V_RAM vram, int brightness) {
        System.out.println("Úsečka pro velký sklon nad 45");

        float a = (float)(pk.x - pz.x) / (pk.y - pz.y);

        float b = (float)(pk.y * pz.x - pz.y* pk.x) / (pk.y - pz.y);
        int x;
        for(int y = pz.y; y <= pk.y; y++){
            x = Math.round(a * y + b);
            vram.setPixel(x,y,brightness);
        }
    }

    private static void naiveLineLow(Point pz, Point pk, V_RAM vram, int brightness) {
        float a = (float)(pk.y - pz.y) / (pk.x - pz.x);

        float b = (float)(pk.x * pz.y - pz.x* pk.y) / (pk.x - pz.x);
        int y;
        for(int x = pz.x; x <= pk.x; x++){
            y = Math.round(a * x + b);
            vram.setPixel(x,y,brightness);
        }
    }

    private static void verticalLine(Point pz, Point pk, V_RAM vram, int brightness) {
        int x= pz.x;
        for(int y = pz.y; y <= pk.y; y++){
            vram.setPixel(x,y,brightness);
        }
    }

    private static void horizontalLine(Point pz, Point pk, V_RAM vram, int brightness) {
        int y = pz.y;
        for(int x = pz.x; x <= pk.x; x++){
            vram.setPixel(x,y,brightness);
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
}
