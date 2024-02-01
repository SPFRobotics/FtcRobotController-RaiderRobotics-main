package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous
public class rotationMovementTesting extends LinearOpMode {
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private IMU imu;

    FtcDashboard dashboard = FtcDashboard.getInstance();

    public static double XPos = 0;
    public static double YPos = 0;
    public static double ROTATION = 90;
    public static double POWER = 1; //1
    public static double X = 1; // 1
    public static double Y = 0;
    public static double BOTROT = -1; // -1

    @Override
    public void runOpMode() throws InterruptedException {
        //SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Initializtion();

        waitForStart();

        if (isStopRequested()) return;


        while (opModeIsActive()) {
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double error = Math.toDegrees(botHeading) - ROTATION;
            if (error < 0) {
                // Rotate the movement direction counter to the bot's rotation
                double rotX = X * Math.cos(-botHeading) - Y * Math.sin(-botHeading);
                double rotY = X * Math.sin(-botHeading) + Y * Math.cos(-botHeading);
                double rxField = BOTROT;

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
                packet.put("Error", error);

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
    }

    private void Initializtion() {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

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
}
