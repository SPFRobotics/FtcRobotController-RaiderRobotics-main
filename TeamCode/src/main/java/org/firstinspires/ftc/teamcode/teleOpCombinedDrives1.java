package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class teleOpCombinedDrives1 extends LinearOpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor liftLeft;
    private DcMotor liftRight;
    private IMU imu;
    private double speed = 0.5;
    private double previousSpeed;
    private int iterationsPressed = 0;
    private Gamepad currentGamepad1;
    private Gamepad previousGamepad1;
    private Gamepad.RumbleEffect maxSpeedStartUpRumbleEffect = new Gamepad.RumbleEffect.Builder()
            .addStep(0.1,0.1,10)
            .addStep(0.2,0.2,10)
            .addStep(0.3,0.3,10)
            .addStep(0.4,0.4,10)
            .addStep(0.5,0.5,10)
            .addStep(0.6,0.6,10)
            .addStep(0.7,0.7,10)
            .addStep(0.8,0.8,10)
            .addStep(0.9,0.9,10)
            .addStep(1.0,1.0,20)
            .build();
    private Gamepad.LedEffect maxSpeedStartUpLEDEffect = new Gamepad.LedEffect.Builder()
            .addStep(0.1,0,0,10)
            .addStep(0.2,0,0,10)
            .addStep(0.3,0,0,10)
            .addStep(0.4,0,0,10)
            .addStep(0.5,0,0,10)
            .addStep(0.6,0,0,10)
            .addStep(0.7,0,0,10)
            .addStep(0.8,0,0,10)
            .addStep(0.9,0,0,10)
            .addStep(1.0,0,0,20)
            .build();

    @Override
    public void runOpMode() throws InterruptedException {
        Initializtion();

        if (isStopRequested()) return;

        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();

        while (opModeIsActive()) {
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            Speed();
            fieldOriented();
            robotOriented();
        }
    }

    private void Initializtion() {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
        waitForStart();
    }
    private void Speed() {
        if (gamepad1.left_trigger > 0) {speed+=0.01;}
        if (gamepad1.right_trigger > 0) {speed-=0.01;}
        if (gamepad1.left_trigger > 0 && gamepad1.right_trigger > 0) {iterationsPressed+=1;} else {iterationsPressed=0;}
        if (iterationsPressed >= 10) {speed=0.5; iterationsPressed=0; gamepad1.rumble(50);}
        if (gamepad1.left_bumper) {if(currentGamepad1.left_bumper && !previousGamepad1.left_bumper){previousSpeed = speed; gamepad1.runRumbleEffect(maxSpeedStartUpRumbleEffect); gamepad1.runLedEffect(maxSpeedStartUpLEDEffect);} speed = 1; gamepad1.setLedColor(1,0,0,5);} else if (!currentGamepad1.left_bumper && previousGamepad1.left_bumper) {speed=previousSpeed;}
        if (gamepad1.right_bumper) {speed=0.25;}
        speed = Range.clip(speed,0,1);
    }
    private void fieldOriented() {
        /*double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = -gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;*/
        double yField = 0;
        if (gamepad1.dpad_up) { yField += (1*speed);}
        if (gamepad1.dpad_down) { yField -= (1*speed);}
        double xField = 0;
        if (gamepad1.dpad_right) { xField += (1*speed);}
        if (gamepad1.dpad_left) { xField -= (1*speed);}
        double rxField = gamepad1.right_stick_x;
        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = xField * Math.cos(-botHeading) - yField * Math.sin(-botHeading);
        double rotY = xField * Math.sin(-botHeading) + yField * Math.cos(-botHeading);

        rotX = rotX * 1.2;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rxField), 1);
        double frontLeftPower = (rotY + rotX + rxField) / denominator;
        double backLeftPower = (rotY - rotX + rxField) / denominator;
        double frontRightPower = (rotY - rotX - rxField) / denominator;
        double backRightPower = (rotY + rotX - rxField) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }
    private void robotOriented() {
        double yRobot = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double xRobot = -gamepad1.left_stick_x * 1.2; // Counteract imperfect strafing
        double rxRobot = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(yRobot) + Math.abs(xRobot) + Math.abs(rxRobot), 1);
        double frontLeftPower = (yRobot + xRobot + rxRobot) / denominator;
        double backLeftPower = (yRobot - xRobot + rxRobot) / denominator;
        double frontRightPower = (yRobot - xRobot - rxRobot) / denominator;
        double backRightPower = (yRobot + xRobot - rxRobot) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }
}