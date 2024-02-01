package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TRACK_WIDTH;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.WHEEL_RADIUS;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TICKS_PER_REV;

@Config
@Autonomous
public class rotationMovementTesting extends LinearOpMode {
    /*private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;*/
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private IMU imu;

    FtcDashboard dashboard = FtcDashboard.getInstance();

    public static double XPos = 0;
    public static double YPos = 0;
    public static double ROTATION = 90;
    public static double POWER = 1; //1
    public static double X = 0; // 1
    public static double Y = 0; //1
    public static double BOTROT = 1; // -1
    public static double kP = 0.01;
    public static double dT = 5;

    private double totalMotorCounts = 0;
    private double countsEstimate = 0;
    private double botXEstimate = 0;
    private double botXEstimatePrev = 0;
    private double botYEstimate = 0;
    private double botYEstimatePrev = 0;
    private double botRotateEstimate = 0;
    private double botRotateEstimatePrev = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Initializtion();

        botXEstimate = 0;
        botYEstimate = 0;
        botXEstimatePrev = 0;
        botYEstimatePrev = 0;
        botRotateEstimate = 0;
        botRotateEstimatePrev = 0;

        waitForStart();

        if (isStopRequested()) return;


        while (opModeIsActive()) {
            double frontLeftVel = rpmToVelocity(frontLeft.getVelocity() / TICKS_PER_REV);
            double frontRightVel = rpmToVelocity(frontRight.getVelocity() / TICKS_PER_REV);
            double backLeftVel = rpmToVelocity(backLeft.getVelocity() / TICKS_PER_REV);
            double backRightVel = rpmToVelocity(backRight.getVelocity() / TICKS_PER_REV);

            double arcLength = (ROTATION / 360) * (TRACK_WIDTH * Math.PI);
            double rotationCounts = (ROTATION / 360) * (TRACK_WIDTH * 0.5 * Math.PI) * TICKS_PER_REV;

            double velForward = (frontLeftVel + frontRightVel + backRightVel + backLeftVel) / 4;
            double velStrafe = (backLeftVel + frontRightVel - frontLeftVel - backRightVel) / 4;
            double velRotate = (backRightVel + frontRightVel - frontLeftVel - backLeftVel) / (4 * TRACK_WIDTH);

            botYEstimate = botYEstimatePrev + velForward * (dT * 0.001);
            botYEstimatePrev = botYEstimate;
            botXEstimate = botXEstimatePrev + velStrafe * (dT * 0.001);
            botXEstimatePrev = botXEstimate;
            botRotateEstimate = botRotateEstimatePrev + velRotate * (dT * 0.001);
            botRotateEstimatePrev = botRotateEstimate;

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double error = Math.toDegrees(botHeading) - ROTATION;
            if (Math.abs(error) > 0.5) {
                // Rotate the movement direction counter to the bot's rotation
                double rotX = X * Math.cos(-botHeading) - Y * Math.sin(-botHeading);
                double rotY = X * Math.sin(-botHeading) + Y * Math.cos(-botHeading);
                double rxField = BOTROT * error * kP;
                rxField = Range.clip(rxField, -1, 1);

                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rxField), 1.0);
                double frontLeftPower = (rotY + rotX + rxField) / denominator;
                double backLeftPower = (rotY - rotX + rxField) / denominator;
                double frontRightPower = (rotY - rotX - rxField) / denominator;
                double backRightPower = (rotY + rotX - rxField) / denominator;

                TelemetryPacket packet = new TelemetryPacket();

                packet.put("Left Front Power", frontLeftPower);
                packet.put("Left Back Power", backLeftPower);
                packet.put("Right Front Power", frontRightPower);
                packet.put("Right Back Power", backRightPower);
                packet.put("Robot Orientation", Math.toDegrees(botHeading));
                packet.put("rxField", rxField);
                packet.put("Error", error);

                packet.addLine("");
                packet.put("botYEstimate", botYEstimate);
                packet.put("botXEstimate", botYEstimate);
                packet.put("botRotationEstimate", botRotateEstimate);

                packet.addLine("");
                packet.put("frontRightVelocity",frontRightVel);
                packet.put("frontLeftVelocity",frontLeftVel);
                packet.put("backRightVelocity",backRightVel);
                packet.put("backLeftVelocity",backLeftVel);

                dashboard.sendTelemetryPacket(packet);

                frontLeft.setPower(POWER * frontLeftPower);
                backLeft.setPower(POWER * backLeftPower);
                frontRight.setPower(POWER * frontRightPower);
                backRight.setPower(POWER * backRightPower);
            } else {
                frontLeft.setPower(0);
                backLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);
            }
        }
        sleep((long)dT);
    }

    private void Initializtion() {
        // Declare our motors
        // Make sure your ID's match your configuration
        /*frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");*/

        frontLeft = hardwareMap.get(DcMotorEx.class,"frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class,"backLeft");
        frontRight = hardwareMap.get(DcMotorEx.class,"frontRight");
        backRight = hardwareMap.get(DcMotorEx.class,"backRight");

        //frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
        waitForStart();
    }
    public static double rpmToVelocity(double rpm) {
        return rpm * 1 * 2 * Math.PI * WHEEL_RADIUS / 60.0;
    }
}
