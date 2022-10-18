package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {

    BufferedImage img = new BufferedImage(600, 560, BufferedImage.TYPE_INT_ARGB);
    Graphics2D imgG2 = img.createGraphics();
    private Color currentColor = Color.black;
    private MouseAdapter freeHandDrawer = new MouseAdapter() {
        Point prev;
        Point next;
        @Override
        public void mousePressed(MouseEvent e) {
            prev = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (prev != null) {
                next = e.getPoint();
                imgG2.setColor(currentColor);
                imgG2.drawLine(prev.x, prev.y, next.x, next.y);
                repaint();
                prev = next;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            prev = null;

        }
    };


    private MouseAdapter lineDrawer = new MouseAdapter() {
        Point start;
        Point end;

        @Override
        public void mousePressed(MouseEvent e) {
            start = e.getPoint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            end = e.getPoint();
            imgG2.setColor(currentColor);
            imgG2.drawLine(start.x, start.y, end.x, end.y);
            repaint();
        }

    };


    private MouseAdapter circleDrawer = new MouseAdapter() {
        Point start;
        Point end;
        double radius;

        @Override
        public void mousePressed(MouseEvent e) {
            start = e.getPoint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            end = e.getPoint();
            radius = Math.sqrt(Math.pow(Math.abs(start.x-end.x), 2) + Math.pow(Math.abs(start.y-end.y), 2));
            imgG2.setColor(currentColor);
            imgG2.drawOval(start.x, start.y, (int) radius, (int) radius);
            repaint();
        }

    };

    private MouseAdapter triangleDrawer = new MouseAdapter() {
        Point one;
        Point two;
        Point three;

        @Override
        public void mousePressed(MouseEvent e) {
            if (one == null) {
                one = e.getPoint();
            } else {
                three = e.getPoint();
                imgG2.setColor(currentColor);
                imgG2.drawPolygon(new int[] {one.x, two.x, three.x}, new int[] {one.y, two.y, three.y}, 3);
                repaint();
                one = null;
                two = null;
                three = null;
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            two = e.getPoint();
        }
    };


    private MouseAdapter rectangleDrawer = new MouseAdapter() {
        Point start;
        Point end;

        @Override
        public void mousePressed(MouseEvent e) {
            start = e.getPoint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            double w;
            double h;
            end = e.getPoint();
            w = Math.abs(start.x-end.x);
            h = Math.abs(start.y-end.y);
            imgG2.setColor(currentColor);
            imgG2.drawRect(start.x, start.y, (int) w, (int) h);
            repaint();

        }
    };



    public DrawingPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Creating a copy of the Graphics
        // so any reconfiguration we do on
        // it doesn't interfere with what
        // Swing is doing.
        Graphics2D g2 = (Graphics2D) g.create();
        // Drawing the image.
        int w = img.getWidth();
        int h = img.getHeight();

        g2.drawImage(img, 0, 0, w, h, null);
    }

    public void setColor(String color){
        if (color.equals("black")) {
            currentColor = Color.black;
        } else if (color.equals("blue")) {
            currentColor = Color.blue;
        } else if (color.equals("cyan")) {
            currentColor = Color.cyan;
        } else if (color.equals("darkGray")) {
            currentColor = Color.darkGray;
        } else if (color.equals("gray")) {
            currentColor = Color.gray;
        } else if (color.equals("lightGray")) {
            currentColor = Color.lightGray;
        } else if (color.equals("magenta")) {
            currentColor = Color.magenta;
        } else if (color.equals("orange")) {
            currentColor = Color.orange;
        } else if (color.equals("pink")) {
            currentColor = Color.pink;
        } else if (color.equals("red")) {
            currentColor = Color.red;
        } else if (color.equals("white")) {
            currentColor = Color.white;
        } else if (color.equals("yellow")) {
            currentColor = Color.yellow;
        } else if (color.equals("brown")) {
            currentColor = new Color(139, 69, 19);
        } else if (color.equals("tomato")) {
            currentColor = new Color(255, 99, 71);
        }  else if (color.equals("purple")) {
            currentColor = new Color(128, 0, 128);
        }  else if (color.equals("gold")) {
            currentColor = new Color(255, 215, 0);
        }

    }

    public void toggleFreeHand() {

        for (MouseListener ml : this.getListeners(MouseListener.class)) {
            this.removeMouseListener(ml);
        }

        for (MouseMotionListener mml : this.getListeners(MouseMotionListener.class)) {
            this.removeMouseMotionListener(mml);
        }

        this.addMouseListener(freeHandDrawer);
        this.addMouseMotionListener(freeHandDrawer);
    }


    public void toggleLine() {
        for (MouseListener ml : this.getListeners(MouseListener.class)) {
            this.removeMouseListener(ml);
        }

        for (MouseMotionListener mml : this.getListeners(MouseMotionListener.class)) {
            this.removeMouseMotionListener(mml);
        }

        this.addMouseListener(lineDrawer);
        this.addMouseMotionListener(lineDrawer);
    }

    public void toggleCircle() {
        for (MouseListener ml : this.getListeners(MouseListener.class)) {
            this.removeMouseListener(ml);
        }

        for (MouseMotionListener mml : this.getListeners(MouseMotionListener.class)) {
            this.removeMouseMotionListener(mml);
        }

        this.addMouseListener(circleDrawer);
        this.addMouseMotionListener(circleDrawer);
    }

    public void toggleTriangle() {
        for (MouseListener ml : this.getListeners(MouseListener.class)) {
            this.removeMouseListener(ml);
        }

        for (MouseMotionListener mml : this.getListeners(MouseMotionListener.class)) {
            this.removeMouseMotionListener(mml);
        }

        this.addMouseListener(triangleDrawer);
        this.addMouseMotionListener(triangleDrawer);
    }

    public void toggleRect() {
        for (MouseListener ml : this.getListeners(MouseListener.class)) {
            this.removeMouseListener(ml);
        }

        for (MouseMotionListener mml : this.getListeners(MouseMotionListener.class)) {
            this.removeMouseMotionListener(mml);
        }

        this.addMouseListener(rectangleDrawer);
        this.addMouseMotionListener(rectangleDrawer);
    }


}
