package org.firstinspires.ftc.teamcode.Hardware.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Odometry {
    public DcMotor leftPod = null;
    public DcMotor rightPod = null;
    public DcMotor centerPod = null;
    public LinearOpMode opMode = null;
    double[] pose = new double[3]; // pose[0] is x; pose[1] is y; pose[2] is phi
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
    public void SetPose(double X, double Y, double phi){
        pose[0] = X;
        pose[1] = Y;
        pose[2] = phi;
    }
    public void UpdateOdom(){

    }

}
