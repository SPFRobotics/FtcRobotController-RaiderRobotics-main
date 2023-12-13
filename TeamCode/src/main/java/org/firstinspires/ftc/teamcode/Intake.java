package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public LinearOpMode opmode = null;
    public DcMotor intakeMotor = null;
    public Servo lipServo = null;
    public Intake(LinearOpMode lom){
        opmode = lom;
    }
    public void initIntake(){
        intakeMotor = opmode.hardwareMap.get(DcMotor.class, "intake");
        lipServo = opmode.hardwareMap.get(Servo.class, ""); //Edit Servo
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void raiseLip(){ //Test This Position, .5 is a placeholder
        lipServo.setPosition(.5);
    }
    public void lowerLip(){ //Test This Position, 0 is a placeholder
        lipServo.setPosition(0);
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
