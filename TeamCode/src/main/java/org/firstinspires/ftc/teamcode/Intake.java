package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public LinearOpMode opmode = null;
    public DcMotor intakeMotor1 = null;
    public DcMotor intakeMotor2 = null;
    public Servo lipServo = null;
    public Servo armServo = null;
    
    private double maxLipPos = 0.46;
    private double minLipPos = 0;
    private double maxArmPos = .535;
    private double minArmPos = 0;
    public Intake(LinearOpMode lom){
        opmode = lom;
    }
    public void initIntake(){
        intakeMotor1 = opmode.hardwareMap.get(DcMotor.class, "intake1");
        intakeMotor2 = opmode.hardwareMap.dcMotor.get("intake2");  /** Port: ControlHub MotorPort 3 **/
        lipServo = opmode.hardwareMap.get(Servo.class, "intakeRamp");
        armServo = opmode.hardwareMap.get(Servo.class, "intakeArm");
        intakeMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void raiseLip(){ //Test This Position, .5 is a placeholder
        lipServo.setPosition(minLipPos);
    }
    public void lowerLip(){ //Test This Position, 0 is a placeholder
        lipServo.setPosition(maxLipPos);
    }
    public void raiseArm(){
        armServo.setPosition(maxArmPos);
    }
    public void lowerArm(){
        lipServo.setPosition(minArmPos);
    }
    public void powerOn(double power){
        intakeMotor1.setPower(power);intakeMotor2.setPower(power);
    }
    public void powerOnTimed(double power, int seconds){
        this.powerOn(power);
        opmode.sleep(seconds * 1000);
        this.powerOff();
    }
    public void powerOff(){
        intakeMotor1.setPower(0);intakeMotor2.setPower(0);
    }
}
