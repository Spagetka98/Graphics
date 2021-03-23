package cz.osu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JPanel{

    private ImagePanel imagePanel;
    private JLabel infoLabel;

    private V_RAM vram;

    private Matrix3D r_X;
    private Matrix3D r_y_plus;
    private Matrix3D r_y_minus;
    private Matrix3D r_Z;

    private Matrix3D tX_plus;
    private Matrix3D tX_minus;
    private Matrix3D tY_plus;
    private Matrix3D tY_minus;

    private Matrix3D s_bigger;
    private Matrix3D s_smaller;

    private Matrix3D q;
    private Matrix3D k;

    private ArrayList<Point3D> points;
    private ArrayList<Triangle3D> triangles;

    private Point3D viewVec;

    public MainWindow(){

        initialize();
        vram = new V_RAM(100,100);

        viewVec = new Point3D(0,0,1);

        GraphicsOperations.fillBrightness(vram,255);

        points = PointGenerator.generateFan3DPoints(4,0);
        points.addAll(PointGenerator.generateFan3DPoints(4,Math.sqrt(2)));

        triangles = PointGenerator.generateCubeTriangles();

        r_X = Matrix3D.createRotationMatrix3D_YZ(2);
        r_y_plus = Matrix3D.createRotationMatrix3D_ZX(2);
        r_y_minus = Matrix3D.createRotationMatrix3D_ZX(-2);
        r_Z = Matrix3D.createRotationMatrix3D_XY(2);

        tX_plus = Matrix3D.createTranslationMatrix3D(0.01,0,0);
        tX_minus = Matrix3D.createTranslationMatrix3D(-0.01,0,0);
        tY_plus = Matrix3D.createTranslationMatrix3D(0,0.01,0);
        tY_minus = Matrix3D.createTranslationMatrix3D(0,-0.01,0);

        s_bigger = Matrix3D.createScaleMatrix3D(1.01);
        s_smaller = Matrix3D.createScaleMatrix3D(0.99);

        double xMin = points.get(0).data[0];
        double xMax = xMin;

        double yMin = points.get(0).data[1];
        double yMax = yMin;

        double zMin = points.get(0).data[2];
        double zMax = zMin;

        for (int i = 1; i < points.size() ; i++) {
            Point3D point = points.get(i);

            xMin = Math.min(xMin,point.data[0]);
            xMax = Math.max(xMax,point.data[0]);

            yMin = Math.min(yMin,point.data[1]);
            yMax = Math.max(yMax,point.data[1]);

            zMin = Math.min(zMin,point.data[2]);
            zMax = Math.max(zMax,point.data[2]);
        }

        Matrix3D t1 = Matrix3D.createTranslationMatrix3D(-(xMin+xMax)/2.0,-(yMin+yMax)/2.0,-(zMin+zMax)/2.0);
        Matrix3D s1 = Matrix3D.createScaleMatrix3D(2/Math.max(Math.max(xMax-xMin,yMax-yMin),zMax-zMin));
        q = Matrix3D.multiply(s1,t1);

        Matrix3D s2 = Matrix3D.createScaleMatrix3D(Math.min(vram.getWidth(),vram.getHeight())/2.0);
        Matrix3D t2 = Matrix3D.createTranslationMatrix3D(vram.getWidth()/2.0,vram.getHeight()/2.0,0);
        k = Matrix3D.multiply(t2,s2);

        Matrix3D p = Matrix3D.createOrthogonalMatrixXY();
        k = Matrix3D.multiply(k,p);

        Point3D[] drawPoints = new Point3D[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Point3D point = points.get(i).applyMatrix(Matrix3D.multiply(k,q));
            point.data[0] /= point.data[3];
            point.data[1] /= point.data[3];
            point.data[2] /= point.data[3];
            drawPoints[i] = point;
        }

        for (Triangle3D tr : triangles) {
            tr.setVisibility(drawPoints,viewVec);
            if (!tr.visible){
                GraphicsOperations.drawTriangle(vram,tr,drawPoints,240);
            }
        }

        for (Triangle3D tr : triangles) {
            if (tr.visible){
                GraphicsOperations.drawTriangle(vram,tr,drawPoints,0);
            }
        }

        imagePanel.setImage(vram.getImage());

    }

    private void initialize(){

        setLayout(null);
        setFocusable(true);
        requestFocusInWindow();

        imagePanel = new ImagePanel();
        imagePanel.setBounds(10,60, 970, 600);
        this.add(imagePanel);

        /*
        //open image
        JButton button = new JButton();
        button.setBounds(150,10,120,30);
        button.setText("Load Image");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openImage();
            }
        });

        this.add(button);
        */

        //save image as PNG
        JButton button4 = new JButton();
        button4.setBounds(10,10,120,30);
        button4.setText("Save as PNG");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImageAsPNG();
            }
        });

        this.add(button4);

        infoLabel = new JLabel();
        infoLabel.setBounds(850,10,120,30);
        infoLabel.setText("Rotation");
        infoLabel.setFont(new Font(infoLabel.getName(), Font.BOLD, 20));

        this.add(infoLabel);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                if(e.getKeyChar() == 'r'){

                    infoLabel.setText("Rotation");
                }

                if(e.getKeyChar() == 't'){

                    infoLabel.setText("Translation");
                }

                if(e.getKeyChar() == 's'){

                    infoLabel.setText("Scale");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                GraphicsOperations.fillBrightness(vram, 255);

                if(infoLabel.getText().equals("Translation")){

                    if (key == KeyEvent.VK_LEFT) q=Matrix3D.multiply(tX_minus,q);

                    if (key == KeyEvent.VK_UP) q=Matrix3D.multiply(tY_plus,q);

                    if (key == KeyEvent.VK_RIGHT) q=Matrix3D.multiply(tX_plus,q);

                    if (key == KeyEvent.VK_DOWN) q=Matrix3D.multiply(tY_minus,q);
                }

                if(infoLabel.getText().equals("Rotation")){

                    if (key == KeyEvent.VK_LEFT) q=Matrix3D.multiply(r_y_plus,q);
                    if (key == KeyEvent.VK_RIGHT) q=Matrix3D.multiply(r_y_minus,q);

                    if (key == KeyEvent.VK_UP) q=Matrix3D.multiply(r_X,q);
                    if (key == KeyEvent.VK_DOWN) q=Matrix3D.multiply(r_Z,q);
                }

                if(infoLabel.getText().equals("Scale")){

                    if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_UP) q=Matrix3D.multiply(s_bigger,q);

                    if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN) q=Matrix3D.multiply(s_smaller,q);
                }

                Point3D[] drawPoints = new Point3D[points.size()];
                for (int i = 0; i < points.size(); i++) {
                    Point3D point = points.get(i).applyMatrix(Matrix3D.multiply(k,q));
                    point.data[0] /= point.data[3];
                    point.data[1] /= point.data[3];
                    point.data[2] /= point.data[3];
                    drawPoints[i] = point;
                }

                for (Triangle3D tr : triangles) {
                    tr.setVisibility(drawPoints,viewVec);
                    if (!tr.visible){
                        GraphicsOperations.drawTriangle(vram,tr,drawPoints,240);
                    }
                }

                for (Triangle3D tr : triangles) {
                    if (tr.visible){
                        GraphicsOperations.drawTriangle(vram,tr,drawPoints,0);
                    }
                }

                imagePanel.setImage(vram.getImage());
            }
        });
        JFrame frame = new JFrame("Raster Graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setSize(1004, 705);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void openImage(){

        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir +"/Desktop");
        fc.setDialogTitle("Load Image");

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            try {

                BufferedImage temp = ImageIO.read(file);

                if(temp != null){

                    imagePanel.setImage(temp);

                }else {

                    JOptionPane.showMessageDialog(null, "Unable to load image", "Open image: ", JOptionPane.ERROR_MESSAGE);
                }

            }catch (IOException e){

                e.printStackTrace();
            }
        }
    }

    private void saveImageAsPNG(){

        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir +"/Desktop");
        fc.setDialogTitle("Save Image as PNG");

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            String fname = file.getAbsolutePath();

            if(!fname.endsWith(".png") ) file = new File(fname + ".png");

            try {

                ImageIO.write(imagePanel.getImage(), "png", file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        new MainWindow();
    }
}
