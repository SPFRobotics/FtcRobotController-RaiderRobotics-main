package org.firstinspires.ftc.teamcode.Hardware.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.Util.Unit;
import org.firstinspires.ftc.teamcode.Hardware.Util.Vector2D;

public class Odometry {
    public DcMotor leftPod = null;
    public DcMotor rightPod = null;
    public DcMotor centerPod = null;
    public LinearOpMode opMode = null;
    private final double TRACKWIDTH_cm = 30;

    double[] pose = new double[3];
    double[] prevEncoder = new double[3]; // prevEncoder[0] is left; prevEncoder[1] is center; prevEncoder[2] is right

    public Odometry(LinearOpMode lom){
        opMode = lom;
    }
    public void Init(){
        leftPod = opMode.hardwareMap.dcMotor.get("leftPod");
        rightPod = opMode.hardwareMap.dcMotor.get("rightPod");
        centerPod = opMode.hardwareMap.dcMotor.get("centerPod");
        leftPod.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        centerPod.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightPod.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftPod.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void SetPose(double X, double Y, double headingRadians){ // Any angles in our code should be in radians we're grown by now
        pose[0] = X;
        pose[1] = Y;
        pose[2] = headingRadians;
    }
    public void UpdateOdom(){ // Look at GM0 if confused about any calculations done here
        double deltaLeftPod = leftPod.getCurrentPosition() - prevEncoder[0]; // delta = final - initial
        double deltaCenterPod = centerPod.getCurrentPosition() - prevEncoder[1];
        double deltaRightPod = rightPod.getCurrentPosition() - prevEncoder[2];

        double phi = (deltaLeftPod - deltaRightPod) / Unit.cm_convert(TRACKWIDTH_cm);
        // see Gm0, phi (deltaHeading) is the  changes in right and left Pods divided by trackwidth, which we measured on the robot to be the constant TRACKWIDTH_cm
        double deltaMiddlePos = (deltaLeftPod + deltaRightPod) / 2; // The change in position of the robots center is the average of changes on right and left pod
        double deltaPerpPos = deltaCenterPod; // See Gm0, our offset on 2024-25 Robot is 0, our pod is in the center

        double deltaX = deltaMiddlePos * Math.cos(pose[2]) - deltaPerpPos * Math.sin(pose[2]); // See Gm0 for derivation
        double deltaY = deltaMiddlePos * Math.sin(pose[2]) - deltaPerpPos * Math.cos(pose[2]); // See. G. m. 0.

        pose[0] += deltaX; // remember pose[0] is x position of robot
        pose[1] += deltaY; // remember pose[1] is y position of robot
        pose[2] += phi; // remember pose[2] is heading of robot (direction facing)

        // Reset prevEncoder values to access next iteration
        prevEncoder[0] = leftPod.getCurrentPosition();
        prevEncoder[1] = centerPod.getCurrentPosition();
        prevEncoder[2] = rightPod.getCurrentPosition();
    }

}
