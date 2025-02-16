//This is a class written for testing purposes only to control a new claw system
//This is not to be used for the actual competition
package org.firstinspires.ftc.teamcode.OpModes.TeleOp;
import android.text.method.Touch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Button;

@TeleOp(name = "RobotMainTeleOp")
public class RobotMainTeleOp extends LinearOpMode{
    private Servo FRotationServo = null;
    private Servo FWristServo = null;
    private Servo BWristServo = null;
    private Servo FClawRotationServo = null;
    private Servo FClawServo = null;
    private Servo BRClawServo = null;
    private Servo BLClawServo = null;
    private DcMotor extendo = null;
    private DcMotor MotorYRight = null;
    private DcMotor MotorYLeft = null;
    private DcMotor FRMotor = null;
    private DcMotor FLMotor = null;
    private DcMotor BRMotor = null;
    private DcMotor BLMotor = null;
    private TouchSensor slideStop = null;
    Button lTrigger = new Button();
    Button rTrigger = new Button();
    Button lBumper = new Button();
    Button rBumper = new Button();
    Button b = new Button();
    Button touchpad = new Button();
    private static ElapsedTime timer1 = new ElapsedTime();
    private static ElapsedTime masterClock = new ElapsedTime();

    //Varibles
    double FRotationServoPos = 0.5024;
    double FWristServoPos = 0.5;

    double FClawRotationServoPos = 0.3302;
    double BWristPos = 0.5;

    //Boolean expressions
    boolean wasPressed1 = false;

    public void runOpMode() {
        //Configured looking from BEHIND of the robot

        //Servos
        FRotationServo = hardwareMap.get(Servo.class, "frontRotation");
        FWristServo = hardwareMap.get(Servo.class, "frontWrist");
        FClawRotationServo = hardwareMap.get(Servo.class, "frontClawRotation");
        FClawServo = hardwareMap.get(Servo.class, "frontClaw");
        BLClawServo = hardwareMap.get(Servo.class, "backLeftClaw");
        BRClawServo = hardwareMap.get(Servo.class, "backRightClaw");
        BWristServo = hardwareMap.get(Servo.class, "backWrist");
        slideStop = hardwareMap.get(TouchSensor.class, "Button0");

        //Motors
        extendo = hardwareMap.get(DcMotor.class, "extendo");
        MotorYLeft = hardwareMap.get(DcMotor.class, "liftRight");
        MotorYRight = hardwareMap.get(DcMotor.class, "liftLeft");
        FLMotor = hardwareMap.get(DcMotor.class, "backRight");
        FRMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        BLMotor = hardwareMap.get(DcMotor.class, "frontRight");
        BRMotor = hardwareMap.get(DcMotor.class, "backLeft");

        //Enable encoders
        extendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        FWristServo.setDirection(Servo.Direction.REVERSE);
        MotorYRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BRClawServo.setDirection(Servo.Direction.REVERSE);
        extendo.setDirection(DcMotorSimple.Direction.REVERSE);

        MotorYLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorYRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorYLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorYRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //FClawServo.setPosition(0.3);

        waitForStart();
        while (opModeIsActive()){
            //Drive Train
            double rx = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double y = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            FRMotor.setPower((y + x + rx) / denominator);
            BRMotor.setPower((y - x + rx) / denominator);
            FLMotor.setPower((y - x - rx) / denominator);
            BLMotor.setPower((y + x - rx) / denominator);

            //Reset Claw to transit position
            if (b.press(gamepad2.b) && !gamepad1.options){
                FRotationServoPos = 0.5221;
                BWristPos = 0.155;
                wasPressed1 = true;
                rTrigger.changeState(false);
                timer1.reset();
            }
            if (timer1.milliseconds() >= 500 && wasPressed1){
                FWristServoPos = 0.55;
            }
            if (timer1.milliseconds() >= 1000 && wasPressed1){
                FClawRotationServoPos = 0;
            }
            if (timer1.milliseconds() >= 1500 && wasPressed1){
                FWristServoPos = 0.73;
            }
            if (timer1.milliseconds() >= 2000 && wasPressed1){
                rTrigger.changeState(true);
            }
            if (timer1.milliseconds() >= 2500 && wasPressed1){
                lTrigger.changeState(false);
            }
            if (timer1.milliseconds() >= 3000 && wasPressed1){
                FWristServoPos = 0.3;
            }
            if (timer1.milliseconds() >= 3500 && wasPressed1){
                wasPressed1 = false;
                FRotationServoPos = 0.3302;
                FWristServoPos = 0.273;
                FClawRotationServoPos = 0.65;
                BWristPos = 0.48435;
            }

            //Set claw to pickup position
            if (gamepad2.a){
                wasPressed1 = false;
                FRotationServoPos = 0.3302;
                FWristServoPos = 0.273;
                FClawRotationServoPos = 0.65;
                BWristPos = 0.5;
                //rTrigger.changeState(false);
                //lTrigger.changeState(false);
            }

            //Set claw to face perpendicular to the wall
            if (gamepad2.y){
                wasPressed1 = false;
                FRotationServoPos = 0.5221;
                FWristServoPos = 0.38;
                FClawRotationServoPos = 0.65;
                BWristPos = 0.5;
            }

            //Extend Linear Slides
            //Horizontal
            if (gamepad1.right_bumper){
                extendo.setPower(1);
            }
            else if (gamepad1.left_bumper){
                extendo.setPower(-1);
            }
            else {
                extendo.setPower(0);
            }
            //Vertical
            touchpad.changeState(false);
            if (gamepad2.dpad_up && MotorYRight.getCurrentPosition() < 2220) {
                MotorYLeft.setPower(1);
                MotorYRight.setPower(1);
            }
            else if (!slideStop.isPressed() && gamepad2.dpad_down){
                MotorYLeft.setPower(-1);
                MotorYRight.setPower(-1);
            }
            else{
                MotorYLeft.setPower(0);
                MotorYRight.setPower(0);
            }
            //Rotate Arm
            //*************************************************************
            FRotationServoPos += gamepad2.left_stick_x*0.01;
            if (FRotationServoPos < 0){
                FRotationServoPos = 0;
            }
            if (FRotationServoPos > 1){
                FRotationServoPos = 1;
            }
            FRotationServo.setPosition(FRotationServoPos);

            //*************************************************************

            //Back wrist logic
            //*************************************************************
            BWristPos += gamepad2.right_stick_y*0.01;
            BWristServo.setPosition(BWristPos);
            //*************************************************************

            //Move Wrist
            //*************************************************************

            FWristServoPos += -gamepad2.left_stick_y * 0.01;
            if (FWristServoPos < 0){
                FWristServoPos = 0;
            }
            if (FWristServoPos > 1){
                FWristServoPos = 1;
            }
            FWristServo.setPosition(FWristServoPos);
            //*************************************************************

            //Claw Open Close Logic: Back and Front Claws
            //*************************************************************
            if (lTrigger.toggle((int)gamepad2.left_trigger)){
                FClawServo.setPosition(0.6);
            }
            else{
                FClawServo.setPosition(0.3);
            }

            if (rTrigger.toggle((int)gamepad2.right_trigger)){
                BLClawServo.setPosition(0.25);
                BRClawServo.setPosition(0.25);
            }
            else{
                BLClawServo.setPosition(0.7);
                BRClawServo.setPosition(0.7);
            }
            //****************************************************************

            if (FClawRotationServoPos < 0.975 && lBumper.press(gamepad2.left_bumper)){
                FClawRotationServoPos += 0.325;
            }

            if (FClawRotationServoPos > 0.326 && rBumper.press(gamepad2.right_bumper)){
                FClawRotationServoPos -= 0.325;
            }

            FClawRotationServo.setPosition(FClawRotationServoPos);

            //Telemetry
            telemetry.update();
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");

            //Display Uptime
            telemetry.addLine("Up Time:");
            if (((int)(masterClock.nanoseconds()/1000000000)/3600) == 0 && ((int)(masterClock.nanoseconds()/1000000000)/60) == 0){
                telemetry.addLine(String.valueOf(((int)(masterClock.nanoseconds()/1000000000)%60) + "." + String.valueOf(masterClock.nanoseconds()%1000000000) + "\n"));
            }
            else if (((int)(masterClock.nanoseconds()/1000000000)/3600) == 0){
                telemetry.addLine(String.valueOf(((int)(masterClock.nanoseconds()/1000000000)/60)+ ":" + ((int)(masterClock.nanoseconds()/1000000000)%60) + "." + String.valueOf(masterClock.nanoseconds()%1000000000) + "\n"));
            }
            else{
                telemetry.addLine(String.valueOf(((int)(masterClock.nanoseconds()/1000000000)/3600)) + ":" + String.valueOf(((int)(masterClock.nanoseconds()/1000000000)/60)) + ":" + String.valueOf(((int)(masterClock.nanoseconds()/1000000000)%60) + "." + String.valueOf(masterClock.nanoseconds()%1000000000) + "\n"));
            }

            //Display Motor Power Values
            telemetry.addLine("Motor PWR Values: ");
            telemetry.addLine("FRMotor PWR:" + String.valueOf(FRMotor.getPower()));
            telemetry.addLine("FLMotor PWR:" + String.valueOf(FLMotor.getPower()));
            telemetry.addLine("BRMotor PWR:" + String.valueOf(BRMotor.getPower()));
            telemetry.addLine("BLMotor PWR: " + String.valueOf(BLMotor.getPower()));
            telemetry.addLine("Extendo PWR: " + String.valueOf(extendo.getPower()));
            telemetry.addLine("Right Vertical Slide PWR: " + String.valueOf(MotorYRight.getPower()));
            telemetry.addLine("Left Vertical Slide PWR: " + String.valueOf(MotorYLeft.getPower()) + "\n");

            //Motor Positions
            telemetry.addLine("Motor Positions: ");
            telemetry.addLine("Right Vertical Slide Pos: " + String.valueOf(MotorYRight.getCurrentPosition()));
            telemetry.addLine("Left Vertical Slide Pos: " + String.valueOf(MotorYLeft.getCurrentPosition()));
            telemetry.addLine("Extendo Pos: " + String.valueOf(extendo.getCurrentPosition()) + "\n");

            //Servo Positions
            telemetry.addLine("Servo Positions: ");
            telemetry.addLine("Front Rotation Servo: " + String.valueOf(FRotationServo.getPosition()));
            telemetry.addLine("Front Wrist Servo: " + String.valueOf(FWristServo.getPosition()));
            telemetry.addLine("Front Claw Rotation Servo: " + String.valueOf(FClawRotationServo.getPosition()));
            telemetry.addLine("Front Claw: " + String.valueOf(FClawServo.getPosition()));
            telemetry.addLine("Back Right Claw Servo: " + String.valueOf(BLClawServo.getPosition()));
            telemetry.addLine("Back Left Claw Servo: " + String.valueOf(BRClawServo.getPosition()) + "\n");

            //States
            telemetry.addLine("Right Bumper State: " + rBumper.getState());
            telemetry.addLine("Left Bumper State: " + lBumper.getState());
            telemetry.addLine("Right Trigger State: " + rTrigger.getState());
            telemetry.addLine("Left Trigger State: " + lTrigger.getState());
            telemetry.addLine("Slide Stop State: " + slideStop.isPressed());
            telemetry.addLine("b Button State: " + b.getState());

            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
        }

    }
}
