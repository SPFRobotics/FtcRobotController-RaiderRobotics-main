package org.firstinspires.ftc.teamcode.OpModes.TeleOp;
import android.renderscript.Sampler;
import android.text.method.Touch;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.configuration.ServoHubConfiguration;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ServoChannelParameters;
import org.firstinspires.ftc.teamcode.Hardware.Button;
import org.firstinspires.ftc.teamcode.OpModes.Values;

import java.io.ObjectOutput;

@TeleOp(name = "RobotMainTeleOp")
public class RobotMainTeleOp extends LinearOpMode{

    private DcMotor extendo = null;
    private DcMotor MotorYRight = null;
    private DcMotor MotorYLeft = null;
    private DcMotor FRMotor = null;
    private DcMotor FLMotor = null;
    private DcMotor BRMotor = null;
    private DcMotor BLMotor = null;
    private Servo outtakeClaw = null;
    private Servo outtakeWrist = null;
    private Servo intakeClaw = null;
    private Servo rIntakeWrist = null;
    private Servo lIntakeWrist = null;
    private Servo intakeRotation = null;
    private Servo sweeper = null;
    private Button rTrigger = new Button();
    private Button lTrigger = new Button();
    private Button a = new Button();
    private Button b = new Button();
    private Button yButton = new Button();
    //private Button lBumper = new Button();
    private  Button rBumper = new Button();
    private Button leftThumbStick = new Button();
    private static ElapsedTime transferTime = new ElapsedTime();
    private static ElapsedTime masterClock = new ElapsedTime();

    private double start = 0;
    private double end = 0;

    //Classes to organize parts
    private static class Outtake{
        private static double wristPos = 0.38;
    }
    private static class Intake{
        private static double wristPos = 0;
        private static double clawRotationPos = 0;
        private static double wristTransfer = 0;
        private static double wall = 0.161;
        private static double ground = 0.360;
    }

    public void runOpMode() {
        masterClock.reset();
        //Configured looking from BEHIND of the robot

        //Servos
        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClaw");
        outtakeWrist = hardwareMap.get(Servo.class, "outtakeWrist");
        //Motors
        extendo = hardwareMap.get(DcMotor.class, "extendo");
        MotorYLeft = hardwareMap.get(DcMotor.class, "liftRight");
        MotorYRight = hardwareMap.get(DcMotor.class, "liftLeft");
        FLMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        FRMotor = hardwareMap.get(DcMotor.class, "frontRight");
        BLMotor = hardwareMap.get(DcMotor.class, "backLeft");
        BRMotor = hardwareMap.get(DcMotor.class, "backRight");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClaw");
        rIntakeWrist = hardwareMap.get(Servo.class, "intakeRightWrist");
        lIntakeWrist = hardwareMap.get(Servo.class, "intakeLeftWrist");
        intakeRotation = hardwareMap.get(Servo.class, "intakeClawRotation");
        sweeper = hardwareMap.get(Servo.class, "sweeper");


        //Enable encoders
        extendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorYLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        extendo.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeClaw.setDirection(Servo.Direction.REVERSE);
        //outtakeWrist.setDirection(Servo.Direction.REVERSE);
        //intakeClaw.setDirection(Servo.Direction.REVERSE);
        BRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //intakeClaw.setDirection(Servo.Direction.REVERSE);
        lIntakeWrist.setDirection(Servo.Direction.REVERSE);

        MotorYLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorYRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorYLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorYRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Boolean Expressions
        boolean wasPressed1 = false;

        waitForStart();
        while (opModeIsActive()){
            start = masterClock.milliseconds();

            //Drive Train
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            FLMotor.setPower((y + x + rx) / denominator);
            BLMotor.setPower((y - x + rx) / denominator);
            FRMotor.setPower((y - x - rx) / denominator);
            BRMotor.setPower((y + x - rx) / denominator);

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

            //Outtake==================================================
            if (rTrigger.toggle((int)gamepad2.right_trigger)){
                outtakeClaw.setPosition(Values.Outtake.ClawOpenPos);
            }
            else{
                outtakeClaw.setPosition(Values.Outtake.ClawClosedPos);
            }

            if (Outtake.wristPos < 0.38){
                Outtake.wristPos = 0.38;
            }
            else if (Outtake.wristPos > 1){
                Outtake.wristPos = 1;
            }
            //=========================================================

           //Intake====================================================
           if (lTrigger.toggle((int)gamepad2.left_trigger)){
               intakeClaw.setPosition(Values.Intake.ClawClosedPos);
           }
           else{
               intakeClaw.setPosition(Values.Intake.ClawOpenPos);
           }

            if (Intake.wristPos > 0.537){
                Intake.wristPos = 0.537;
            }
            else if (Intake.wristPos < 0){
                Intake.wristPos = 0;
            }
           //==========================================================

            if (gamepad2.dpad_up){
                MotorYLeft.setPower(Values.verticalSlide.power);
                MotorYRight.setPower(Values.verticalSlide.power);
            }
            if (gamepad2.dpad_down){
                MotorYLeft.setPower(-Values.verticalSlide.power);
                MotorYRight.setPower(-Values.verticalSlide.power);
            }
            if (!gamepad2.dpad_down && !gamepad2.dpad_up){
                MotorYLeft.setPower(0);
                MotorYRight.setPower(0);
            }

            if (rBumper.toggle(gamepad2.right_bumper)){
                Intake.clawRotationPos = 0.50;
            }
            else{
                Intake.clawRotationPos = 0;
            }

            //Special Function Buttons
            if (a.press(gamepad2.a)){
                transferTime.reset();
                wasPressed1 = true;
                rTrigger.changeState(false);
                Outtake.wristPos = 0.6866;
            }
            if (wasPressed1 && transferTime.milliseconds() >= 250){
                Intake.wristPos = Intake.wristTransfer;
                rBumper.changeState(false);
            }
            if (wasPressed1 && transferTime.milliseconds() >= 650){
                rTrigger.changeState(true);
            }
            if (wasPressed1 && transferTime.milliseconds() >= 1000){
                lTrigger.changeState(false);
            }
            if (wasPressed1 && transferTime.milliseconds() >= 1250){
                Outtake.wristPos = 0.38;
                wasPressed1 = false;
            }

            if (b.press(gamepad2.b) && !wasPressed1){
                Intake.wristPos = Intake.wall;
            }

            if (yButton.press(gamepad2.y) && !wasPressed1){
                Intake.wristPos = Intake.ground;
            }

            if (leftThumbStick.toggle(gamepad2.left_stick_button) && !wasPressed1){
                sweeper.setPosition(0.975);
            }
            else{
                sweeper.setPosition(0.5);
            }

            if (!wasPressed1) {
                Outtake.wristPos += gamepad2.right_stick_y * Values.Outtake.wristSpeedMultiplyer;
                Intake.wristPos += gamepad2.left_stick_y * Values.Intake.wristSpeedMultiplyer;
            }
            outtakeWrist.setPosition(Outtake.wristPos);
            lIntakeWrist.setPosition(Intake.wristPos);
            rIntakeWrist.setPosition(Intake.wristPos);
            intakeRotation.setPosition(Intake.clawRotationPos);

            //Telemetry
            telemetry.update();
            telemetry.addLine("Remember, after all of this, we are the first Raider Robotics team to make it to states in 3 entire years... GO KICK THEIR ASS!!!!\n\n");
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
            telemetry.addLine("Outtake Claw: " + outtakeClaw.getPosition());
            telemetry.addLine("Outtake Wrist Pos: " + Outtake.wristPos);
            telemetry.addLine("Intake Wrist Pos: " + Intake.wristPos);
            telemetry.addLine("Intake Claw: " + intakeClaw.getPosition());
            telemetry.addLine("Intake Claw Rotation Pos: " + intakeRotation.getPosition());

            //States
            //telemetry.addLine("States: ");


            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            end = masterClock.milliseconds();
            telemetry.addLine("Cycle Time: " + String.valueOf(end - start) + " milliseconds");
        }

    }
}