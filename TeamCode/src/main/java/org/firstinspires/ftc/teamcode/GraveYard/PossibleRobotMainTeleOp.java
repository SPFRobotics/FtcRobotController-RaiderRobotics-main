
package org.firstinspires.ftc.teamcode.GraveYard;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.Button;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.text.DecimalFormat;

@Disabled


@TeleOp(name="PossibleRobotMainTeleOp")
public class PossibleRobotMainTeleOp extends LinearOpMode {

    private DcMotor RightFrontMotor = null;
    private DcMotor LeftFrontMotor = null;
    private DcMotor RightBackMotor = null;
    private DcMotor LeftBackMotor = null;
    private Servo RotationServo = null;
    private Servo WristServo = null;
    private Servo ClawRotationServo = null;
    private Servo ClawServo = null;
    private DcMotor extendo = null;
    private DcMotor MotorY = null;
    private IMU imu = null;
    private DcMotor craneMotorY = null;
    private int sliderotation;
    Button LTrigger= new Button();
    Button LBumper= new Button();
    DecimalFormat df = new DecimalFormat("#.000");

    public void runOpMode() {
        RightFrontMotor = hardwareMap.get(DcMotor.class, "frontRight");
        LeftFrontMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        RightBackMotor = hardwareMap.get(DcMotor.class, "backRight");
        LeftBackMotor = hardwareMap.get(DcMotor.class, "backLeft");
        RotationServo = hardwareMap.get(Servo.class, "Servo0");
        WristServo = hardwareMap.get(Servo.class, "Servo1");
        ClawRotationServo = hardwareMap.get(Servo.class, "Servo2");
        ClawServo = hardwareMap.get(Servo.class, "Servo3");
        extendo = hardwareMap.get(DcMotor.class, "Motor0");
        MotorY = hardwareMap.get(DcMotor.class, "Motor1");
        sliderotation = 537;


//Both Left Motors are Set in Reverse
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//Setting default if no power is brake
        RightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        double RotationServoPos = 0.5276;
        double WristServoPos = 1;

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);

        if (isStopRequested()) return;
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.options) {
                imu.resetYaw();
            }
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            // Rotate the movement direction counter to the bot's rotation

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * -1.1;
            double rx = gamepad1.right_stick_x;

            LeftBackMotor.setPower(y + x + rx);
            LeftBackMotor.setPower(y - x + rx);
            RightFrontMotor.setPower(y - x - rx);
            RightBackMotor.setPower(y + x - rx);
//Open Claw
            if (LTrigger.press((int) gamepad1.left_trigger)) {
                ClawServo.setPosition(0.6);
            } else {
                ClawServo.setPosition(0.3);
            }
//Reset Claw to default pos
            if (gamepad1.b) {
                RotationServoPos = 0.5276;
                WristServoPos = 0.5;
                ClawRotationServo.setPosition(0.47);
                ClawServo.setPosition(0.3);
            }

            //Set claw to efficent position
            if (gamepad1.a) {
                RotationServoPos = 0.79;
                WristServoPos = 0.273;
            }
// ara likes to touch little matt gregovs
            //Extend Extendo
            extendo.setPower(gamepad2.left_stick_y);
            MotorY.setPower(gamepad2.right_stick_y);

            //Rotate Arm
            //*************************************************************
            if (RotationServoPos >= 0 && RotationServoPos <= 1) {
                RotationServoPos += gamepad1.left_stick_x * 0.001;
            } else if (RotationServoPos > 1) {
                RotationServoPos = 1;
            } else if (WristServoPos < 0) {
                RotationServoPos = 0;
            }
            RotationServo.setPosition(RotationServoPos);
            //*************************************************************

            //Move Wrist
            //*************************************************************
            if (WristServoPos >= 0 && WristServoPos <= 1) {
                WristServoPos += gamepad1.right_stick_y * 0.001;
            } else if (WristServoPos > 1) {
                WristServoPos = 1;
            } else if (WristServoPos < 0) {
                WristServoPos = 0;
            }
            WristServo.setPosition(WristServoPos);
            //*************************************************************
            //Rotate Claw Logic
            //*************************************
            if (LBumper.press(gamepad1.left_bumper)) {
                ClawRotationServo.setPosition(0.15);
            } else {
                ClawRotationServo.setPosition(0.47);
            }

            //***********************************************************
            // Slide Logic
            //*************************************

            if(gamepad2.dpad_up){
                MotorY.setPower(1);
            }

            else if (!gamepad2.dpad_up){
                MotorY.setPower(0);

            }

            if (gamepad2.dpad_up){
                MotorY.setTargetPosition(sliderotation * 10);

            }

            else if (!gamepad2.dpad_up){
                MotorY.setTargetPosition(0);
            }

            if(gamepad2.dpad_down){
                MotorY.setPower(-1);
            }

            else if(gamepad2.dpad_down){
                MotorY.setPower(0);
            }

            if (gamepad2.dpad_down){
                MotorY.setTargetPosition(sliderotation * -1);

            }

            if (gamepad2.dpad_down){
                MotorY.setTargetPosition(0);

            }
            telemetry.update();
            //telemetry.addLine("Telemetry Provided By Mr.Spaceman ;)");
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("MOTOR PWR:");
            telemetry.addLine("FR Motor PWR: " + RightFrontMotor.getPower());
            telemetry.addLine("FL Motor PWR: " + LeftFrontMotor.getPower());
            telemetry.addLine("BR Motor PWR: " + RightBackMotor.getPower());
            telemetry.addLine("BL Motor PWR: " + LeftBackMotor.getPower());
            telemetry.addLine("Vertical Slide: " + craneMotorY.getPower());
            telemetry.addLine("Wrist Pos: " + WristServo.getPosition());
            telemetry.addLine("Extendo: " + extendo.getPower() + "\n");
            telemetry.addLine("Vertical Slide Pos: " + craneMotorY);
            //telemetry.addLine("Extendo Pos: " + extendoXPos + "\n");
            telemetry.addLine("Pitch: " + imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES));
            telemetry.addLine("Roll: " + imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES));
            telemetry.addLine("Yaw: " + imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));

            //Reading on odometry
            telemetry.addLine("\n\nOdometry Info: ");
            //telemetry.addLine("X (CM): " + odometry.getX());
            //telemetry.addLine("X (Ticks): " + odometry.getX()*1600/(Math.PI*3.2));
            //telemetry.addLine("Y (CM): " + odometry.getY());
            //telemetry.addLine("Y (Ticks): " + odometry.getY()*1600/(Math.PI*3.2));
            //telemetry.addLine("Heading: " + odometry.getHeading());
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");

        }

    }


}







