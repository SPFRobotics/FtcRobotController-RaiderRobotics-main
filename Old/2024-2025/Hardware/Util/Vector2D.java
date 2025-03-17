package org.firstinspires.ftc.teamcode.Hardware.Util;

public class Vector2D {
    // Made this to help with odometry... but then didn't need it.
    // So now it's here for simplicity if we use vectors in the future. You're welcome?
    double xComponent;
    double yComponent;
    double thetaRadians;
    double magnitude;
    public Vector2D(double x, double y, double directionRadians){
        // Technically directionRadians is unneeded and can be calculated by x and y, but is included here for the sake of overloading
        this.setX(x);
        this.setY(y);
        this.setDirection(directionRadians);
        this.setMagnitude();
    }
    public Vector2D(double mag, double directionRadians){
        this(mag*Math.cos(directionRadians),mag*Math.sin(directionRadians),directionRadians);
    }
    public Vector2D(){ // makes unit vector to the right
        this(1,0,0);
    }
    void setX(double x){
        xComponent = x;
    }
    void setY(double y){
        yComponent = y;
    }
    void setDirection(double theta){
        thetaRadians = theta;
    }
    void setDirection() {
        thetaRadians = Math.atan2(yComponent,xComponent);
    }
    void setMagnitude() {
        magnitude = Math.sqrt(Math.pow(xComponent,2)+Math.pow(yComponent,2));
    }

    public double getX(){
        return xComponent;
    }
    public double getY(){
        return yComponent;
    }
    public double getMagnitude(){
        return magnitude;
    }
    public double getDirectionRadians(){
        return thetaRadians;
    }
    public static Vector2D addVectors(Vector2D vector1, Vector2D vector2){
        Vector2D resultant = new Vector2D();
        resultant.setX(vector1.getX()+vector2.getX());
        resultant.setY(vector1.getY()+vector2.getY());
        resultant.setMagnitude();
        resultant.setDirection();
        return resultant;
    }
    public static Vector2D subtractVectors(Vector2D vector1, Vector2D vector2){
        Vector2D resultant = new Vector2D();
        resultant.setX(vector1.getX()-vector2.getX());
        resultant.setY(vector1.getY()-vector2.getY());
        resultant.setMagnitude();
        resultant.setDirection();
        return resultant;
    }

}

