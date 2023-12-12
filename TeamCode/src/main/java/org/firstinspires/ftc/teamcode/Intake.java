package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public LinearOpMode opmode = null;
    public DcMotor intakeMotor = null;
    public Servo deployment = null;
    public double minPos = 0;
    public double maxPos = 1;

    public Intake(LinearOpMode lom){
        opmode = lom;
    }
    public void initIntake(){
        intakeMotor = opmode.hardwareMap.get(DcMotor.class, "intake");
        deployment = opmode.hardwareMap.get(Servo.class, "intakeArm");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void bringUp(){

    }
    public void powerOn(double power){
        intakeMotor.setPower(power);
    }
    public void powerOnTimed(double power, int seconds){
        this.powerOn(.3);
        opmode.sleep(seconds * 1000);
        this.powerOff();
    }
    public void powerOff(){
        intakeMotor.setPower(0);
    }
}
