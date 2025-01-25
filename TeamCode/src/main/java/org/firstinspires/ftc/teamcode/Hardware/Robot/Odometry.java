package org.firstinspires.ftc.teamcode.Hardware.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.Util.Unit;
import org.firstinspires.ftc.teamcode.Hardware.Util.Vector2D;

public class Odometry {
    private DcMotor leftPod = null;
    private DcMotor rightPod = null;
    private DcMotor centerPod = null;
    private LinearOpMode opMode = null;
    private final double TRACKWIDTH_CM = 31.115;
    private final double CM_PER_TICK = (3.2 * Math.PI)/2000;
    //2000 PPR encoder; 32 mm diameter = 3.2 cm;
    private final double OFFSET_CM = 11;

    double[] pose = new double[3];
    double[] prevEncoder = new double[3]; // prevEncoder[0] is left; prevEncoder[1] is center; prevEncoder[2] is right

    public Odometry(LinearOpMode lom){
        opMode = lom;
    }
    public void init(){
        leftPod = opMode.hardwareMap.dcMotor.get("extendo");
        rightPod = opMode.hardwareMap.dcMotor.get("rightOdomPod");
        centerPod = opMode.hardwareMap.dcMotor.get("centerOdomPod");
        leftPod.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        centerPod.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightPod.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void setPose(double X, double Y, double headingRadians){ // Any angles in our code should be in radians we're grown by now
        pose[0] = X;
        pose[1] = Y;
        pose[2] = headingRadians;

        prevEncoder[0] = leftPod.getCurrentPosition();
        prevEncoder[1] = centerPod.getCurrentPosition();
        prevEncoder[2] = rightPod.getCurrentPosition();
    }
    public void updateOdom(){ // Look at GM0 if confused about any calculations done here

        double deltaLeftPod = ticksToCM(leftPod.getCurrentPosition() - prevEncoder[0]); // delta = final - initial
        double deltaCenterPod = ticksToCM(centerPod.getCurrentPosition() - prevEncoder[1]);
        double deltaRightPod =  ticksToCM(rightPod.getCurrentPosition() - prevEncoder[2]);


        double phi = (deltaLeftPod - deltaRightPod) / TRACKWIDTH_CM; // need units to be consistent for this calculation
        // see Gm0, phi (deltaHeading) is the  changes in right and left Pods divided by trackwidth, which we measured on the robot to be the constant TRACKWIDTH_cm
        double deltaMiddlePos = (deltaRightPod + deltaLeftPod) / 2; // The change in position of the robots center is the average of changes on right and left pod
        double deltaPerpPos = deltaCenterPod - OFFSET_CM * phi; // See Gm0, our offset on 2024-25 Robot is 0, our pod is in the center

        double deltaX = deltaMiddlePos * Math.cos(pose[2])- deltaPerpPos * Math.sin(pose[2]); // See Gm0 for derivation
        double deltaY = deltaMiddlePos * Math.sin(pose[2]) - deltaPerpPos * Math.cos(pose[2]); // See. G. m. 0.
        // deltaMiddlePos and deltaPerpPos are already in CM rather than motor ticks

        pose[0] += deltaX; // remember pose[0] is x position of robot
        pose[1] += deltaY; // remember pose[1] is y position of robot
        pose[2] += phi; // remember pose[2] is heading of robot (direction facing)

        // Reset prevEncoder values to access next iteration
        prevEncoder[0] = leftPod.getCurrentPosition();
        prevEncoder[1] = centerPod.getCurrentPosition();
        prevEncoder[2] = rightPod.getCurrentPosition();
    }
    public double getX(){
        return pose[0];
    }
    public double getY(){
        return pose[1];
    }
    public double getHeading(){
        return pose[2];
    }

    private double ticksToCM(double ticks){
        return ticks * CM_PER_TICK;
    }
}
