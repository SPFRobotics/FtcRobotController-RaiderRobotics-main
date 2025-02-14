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

    //Varibles
    double FRotationServoPos = 0.5024;
    double FWristServoPos = 0.5;

    double FClawRotationServoPos = 0;
    static double BWristPos = 0;
    static double BWristPosCurrent = 0;

    //Boolean expressions
    boolean wasPressed1 = false;

    private static float servoTrack(Servo x, int pos, int current){
        if (x.getPosition() < pos) {
             current += 0.001;
        }
        else if (x.getPosition() > pos){
            current -= 0.001;
        }
        return current;
    }

    public void runOpMode() {
        //Configured looking from the FRONT of the robot
        /*Hardware mapping is done based on the ports each hardware device is plugged into. The name of the hardware device is followed by the number port it is plugged into.
          If there are multiple expansion hubs the first digit represents the hub the device is plugged into. Ex: Servo10 means it is plugged into port 0 on the expansion hub.
          Ex: Motor1 means it is plugged into port 1 on the control hub.
         */

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
            if (b.press(gamepad2.b)){
                FRotationServoPos = 0.5221;
                FWristServoPos = 0.7587;
                FClawRotationServoPos = 0;
                BWristPos = 0.1131;
            }

            //Set claw to pickup position
            if (gamepad2.a){
                FRotationServoPos = 0.3302;
                FWristServoPos = 0.273;
                FClawRotationServoPos = 0.65;
                BWristPos = 0.48435;
            }

            //Set claw to face perpendicular to the wall
            if (gamepad2.y){
                FRotationServoPos = 0.5221;
                FWristServoPos = 0.4;
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
            if (touchpad.toggle(gamepad2.touchpad) && !slideStop.isPressed()){
                MotorYLeft.setPower(-0.5);
                MotorYRight.setPower(-0.5);
            }
            else{
                if (gamepad2.dpad_up) {
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
            }
            //Rotate Arm
            //*************************************************************
            FRotationServoPos += gamepad2.left_stick_x*0.004;
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
            BWristPos += gamepad2.right_stick_y*0.004;
            BWristServo.setPosition(BWristPos);
            //*************************************************************

            //Move Wrist
            //*************************************************************

            FWristServoPos += -gamepad2.left_stick_y * 0.002;
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
                BLClawServo.setPosition(0.50);
                BRClawServo.setPosition(0.50);
            }
            //****************************************************************

            if (FClawRotationServoPos < 0.975 && lBumper.press(gamepad2.left_bumper)){
                FClawRotationServoPos += 0.325;
            }

            if (FClawRotationServoPos > 0.325 && rBumper.press(gamepad2.right_bumper)){
                FClawRotationServoPos -= 0.325;
            }

            FClawRotationServo.setPosition(FClawRotationServoPos);


            //Telemetry
            telemetry.update();
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("Rotation Servo Pos: " + FRotationServo.getPosition());
            telemetry.addLine("Wrist Servo Pos: " + FWristServo.getPosition());
            telemetry.addLine("Back Wrist Servo Pos: " + BWristServo.getPosition());
            telemetry.addLine("Back Claw: " + BRClawServo.getPosition());
            telemetry.addLine("Claw Rotation Servo Pos: " + FClawRotationServo.getPosition());
            telemetry.addLine("Claw Servo Pos: " + FClawServo.getPosition());
            telemetry.addLine("Vertical Slides: " + MotorYRight.getCurrentPosition());
            telemetry.addLine("Slide Stop State: " + slideStop.isPressed());
            telemetry.addLine("Extendo: " + extendo.getCurrentPosition());
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");

        }

    }
}
