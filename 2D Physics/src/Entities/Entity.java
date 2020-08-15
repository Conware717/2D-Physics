package Entities;

import Game.Handler;

import java.awt.*;

public abstract class Entity {

    protected Handler handler;

    private float G = 1;

    protected double x, y, radius;

    protected double velx, vely;
    protected double accx, accy;
    protected double mass;
    protected boolean gravity;
    protected boolean select = false;
    public Entity(Handler handler, double x, double y, double velx, double vely, double accx, double accy, double radius, double mass, boolean gravity) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.velx = velx;
        this.vely = vely;
        this.accx = accx;
        this.accy = accy;
        this.radius = radius;
        this.mass = mass;
        this.gravity = gravity;
    }

    public void move() {
        if (gravity) {
            updateGravity();
        }
        updateVelocity();
        updatePosition();
    }

    public void updatePosition() {
        this.x += this.velx;
        this.y += this.vely;
    }

    public void updateVelocity() {
        this.velx += this.accx;
        this.vely += this.accy;
    }

    public void updateGravity() {
        for (Entity e : handler.getEntityManager().entities) {
            if (e.equals(this)) {
                continue;
            }
            double accg = (G*e.mass)/Math.pow(calcDistance(this.x, this.y, this.radius, e.x, e.y, e.radius), 2);
            double X1 = this.x;
            double Y1 = this.y;
            double X2 = e.x;
            double Y2 = e.y;
            this.accx = (accg*Math.cos(tan(X1, Y1, X2, Y2)));
            this.accy = (accg*Math.sin(tan(X1, Y1, X2, Y2)));
        }
    }

    public double calcDistance(double x1, double y1, double radius1, double x2, double y2, double radius2) {
        return Math.sqrt((x1 + radius1 - (x2 + radius2))*(x1 + radius1 - (x2 + radius2)) + (y1 + radius1 - (y2 + radius2))*(y1 + radius1 - (y2 + radius2)));
    }

    public double tan (double x, double y, double mx, double my) {
        double normy = my - y;
        double normx = mx - x;
        return Math.atan2(normy, normx);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public boolean getSelect() {
        return select;
    }

    public double getMass () {
        return mass;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public double getVelx() {
        return velx;
    }

    public double getVely() {
        return vely;
    }

    public double getAccx() {
        return accx;
    }

    public double getAccy() {
        return accy;
    }

    public boolean isGravity() {
        return gravity;
    }
}
