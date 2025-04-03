
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
@TeleOp(name="NewRobotTeleOp")
public class NewRobotTeleOp extends LinearOpMode {

    private DcMotor FRMotor = null;
    private DcMotor FLMotor = null;
    private DcMotor BRMotor = null;
    private DcMotor BLMotor = null;
    private Servo FRotationServo = null;
    private Servo FWristServo = null;
    private Servo FClawRotationServo = null;
    private Servo FClawServo = null;
    private DcMotor extendo = null;
    private DcMotor MotorYLeft = null;
    private DcMotor MotorYRight = null;
    private IMU imu = null;
    private int sliderotation;
    Button LTrigger= new Button();
    Button LBumper= new Button();
    DecimalFormat df = new DecimalFormat("#.000");

    public void runOpMode() {
        extendo = hardwareMap.get(DcMotor.class, "extendo");
        MotorYLeft = hardwareMap.get(DcMotor.class, "liftRight");
        MotorYRight = hardwareMap.get(DcMotor.class, "liftLeft");
        FLMotor = hardwareMap.get(DcMotor.class, "backRight");
        FRMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        BLMotor = hardwareMap.get(DcMotor.class, "frontRight");
        BRMotor = hardwareMap.get(DcMotor.class, "backLeft");


        FRotationServo = hardwareMap.get(Servo.class, "Servo0");
        FWristServo = hardwareMap.get(Servo.class, "Servo3");
        FClawRotationServo = hardwareMap.get(Servo.class, "Servo2");
        FClawServo = hardwareMap.get(Servo.class, "Servo3");
        sliderotation = 537;


//Both Left Motors are Set in Reverse
        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        MotorYLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorYRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//Setting default if no power is brake
        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorYLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorYRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * -1.1;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.options) {
                imu.resetYaw();
            }
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double LeftFrontPower = (y + x + rx) / denominator;
            double LeftBackPower = (y - x + rx) / denominator;
            double RightFrontPower = (y - x - rx) / denominator;
            double RightBackPower = (y + x - rx) / denominator;
            FRMotor.setPower(RightFrontPower);
            FLMotor.setPower(LeftFrontPower);
            BRMotor.setPower(RightBackPower);
            BLMotor.setPower(LeftBackPower);
//Open Claw
            if (LTrigger.toggle((int) gamepad1.left_trigger)) {
                FClawServo.setPosition(0.6);
            } else {
                FClawServo.setPosition(0.3);
            }
//Reset Claw to default pos
            if (gamepad1.b) {
                RotationServoPos = 0.5276;
                WristServoPos = 0.5;
                FClawRotationServo.setPosition(0.47);
                FClawServo.setPosition(0.3);
            }

            //Set claw to efficent position
            if (gamepad1.a) {
                RotationServoPos = 0.79;
                WristServoPos = 0.273;
            }

            //Extend Extendo
            extendo.setPower(gamepad2.left_stick_y);
            MotorYLeft.setPower(gamepad2.right_stick_y);
            MotorYRight.setPower(gamepad2.right_stick_y);

            //Rotate Arm
            //*************************************************************
            if (RotationServoPos >= 0 && RotationServoPos <= 1) {
                RotationServoPos += gamepad1.left_stick_x * 0.001;
            } else if (RotationServoPos > 1) {
                RotationServoPos = 1;
            } else if (WristServoPos < 0) {
                RotationServoPos = 0;
            }
            FRotationServo.setPosition(RotationServoPos);
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
            FWristServo.setPosition(WristServoPos);
            //*************************************************************
            //Rotate Claw Logic
            //*************************************
            if (LBumper.toggle(gamepad1.left_bumper)) {
                FClawRotationServo.setPosition(0.15);
            } else {
                FClawRotationServo.setPosition(0.47);
            }

            //***********************************************************
            // Slide Logic
            //*************************************

            if(gamepad2.dpad_up){
                MotorYLeft.setPower(1);
            }

            else if (!gamepad2.dpad_up){
                MotorYLeft.setPower(0);

            }

            if (gamepad2.dpad_up){
                MotorYLeft.setTargetPosition(sliderotation * 10);

            }

            else if (!gamepad2.dpad_up){
                MotorYLeft.setTargetPosition(0);
            }

            if(gamepad2.dpad_down){
                MotorYLeft.setPower(-1);
            }

            else if(gamepad2.dpad_down){
                MotorYLeft.setPower(0);
            }

            if (gamepad2.dpad_down){
                MotorYLeft.setTargetPosition(sliderotation * -1);

            }

            if (gamepad2.dpad_down){
                MotorYLeft.setTargetPosition(0);

            }
            telemetry.update();
            //telemetry.addLine("Telemetry Provided By Mr.Spaceman ;)");
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("MOTOR PWR:");
            telemetry.addLine("FR Motor PWR: " + FRMotor.getPower());
            telemetry.addLine("FL Motor PWR: " + FLMotor.getPower());
            telemetry.addLine("BR Motor PWR: " + BRMotor.getPower());
            telemetry.addLine("BL Motor PWR: " + BLMotor.getPower());
            telemetry.addLine("Wrist Pos: " + FWristServo.getPosition());
            telemetry.addLine("Extendo: " + extendo.getPower() + "\n");
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







