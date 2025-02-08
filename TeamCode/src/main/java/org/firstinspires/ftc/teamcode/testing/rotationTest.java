package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Disabled
@Autonomous
public class rotationTest extends LinearOpMode {
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private IMU imu = null;

    private double inch_convert(double inch) {
        return inch * (537.7 / (3.78 * Math.PI));
    }

    private double inToCm(int inches) {
        return inches * 2.54;
    }
    private double cm_convert(double cm) {
        return cm * (537.7 / (9.6012 / Math.PI));
    }
    public void runOpMode(){
        initialize();
        waitForStart();


        if (opModeIsActive()){
            //while(opModeIsActive()){
                otherRotateMethod(-90.0, .5);
                telemetry.addData("Rotate Function","Ended");
                telemetry.update();
                stop();
            //}
        }

    }
    private void alternateRotate(double angle, double power){
        YawPitchRollAngles robotOrientation = null;
        robotOrientation = imu.getRobotYawPitchRollAngles();
        double startAngle = robotOrientation.getYaw(AngleUnit.DEGREES);
        double targetAngle = startAngle + angle;

        //int neededTicks = (int)((angle*61*537.6)/3600);
        int neededTicks = 200;

        backLeft.setTargetPosition(-1 * neededTicks);
        backRight.setTargetPosition(neededTicks);
        frontLeft.setTargetPosition(-1 * neededTicks);
        frontRight.setTargetPosition(neededTicks);

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backLeft.setPower(-1 * power);
        backRight.setPower(power);
        frontLeft.setPower(-1 * power);
        frontRight.setPower(power);

        while(opModeIsActive() && backLeft.isBusy()){  }

        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);

    }
    private void otherRotateMethod(double angle, double power){
        double minPower = 0.3;
        double Kp = 0.04; //this is for proportional control (ie. the closer you are the target angle the slower you will go)
        double startAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double targetAngle = startAngle + angle;
        double error = (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
        double power1 = 0;
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // rotate until the target angle is reached
        //System.out.printf("%f start angle = ",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        //System.out.printf("%f error = ", error);
        while (opModeIsActive() && Math.abs(error) > 5) {
            //powerZero();
            error = AngleUnit.normalizeDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            // the closer the robot is to the target angle, the slower it rotates
            //power = Range.clip(Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle) / 90, 0.1, 0.5);
            telemetry.addData("real power", power*(error*Kp));
            power1 = Range.clip((power*(error*Kp)),-0.8,0.8); //"Range.clip(value, minium, maxium)" takes the first term and puts it in range of the min and max provided
            if (Math.abs(power1) < minPower) {
                power1 = minPower * (power1/Math.abs(power1));
            }
            telemetry.addData("power",power1);
            System.out.printf("%f power = ",power1);
            telemetry.addData("error",error);

            backLeft.setPower(power1);
            backRight.setPower(-power1);
            frontLeft.setPower(power1);
            frontRight.setPower(-power1);
            if (Math.abs(error) <= 5) {
                backLeft.setPower(0);
                backRight.setPower(0);
                frontLeft.setPower(0);
                frontRight.setPower(0);
            }
            telemetry.addData("angle", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("target", targetAngle);
            telemetry.update();
            //double angleDifference = Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            //rotate(angleDifference, power);
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    private void rotate(double angle, double power) {
        YawPitchRollAngles robotOrientation = null;
        robotOrientation = imu.getRobotYawPitchRollAngles();
        double startAngle = robotOrientation.getYaw(AngleUnit.DEGREES);
        double targetAngle = startAngle + angle;

        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double angleDifference = Math.abs(robotOrientation.getYaw(AngleUnit.DEGREES) - targetAngle);

        // rotate until the target angle is reached
        while (opModeIsActive() && angleDifference > 5) {
            // the closer the robot is to the target angle, the slower it rotates
            power = Range.clip(angleDifference / 90, 0.1, 0.5);
            //power = .3;

            if (robotOrientation.getYaw(AngleUnit.DEGREES) > targetAngle) {
                backLeft.setPower(-1 * power);
                backRight.setPower(power);
                frontLeft.setPower(-1 * power);
                frontRight.setPower(power);
            } else {
                backLeft.setPower(power);
                backRight.setPower(-1 * power);
                frontLeft.setPower(power);
                frontRight.setPower(-1 * power);
            }
            backLeft.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(0);

            angleDifference = Math.abs(robotOrientation.getYaw(AngleUnit.DEGREES) - targetAngle);
            if(angleDifference<5){
                backLeft.setPower(0);
                backRight.setPower(0);
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;

            }
            telemetry.addData("Angle Diff", angleDifference);
            telemetry.update();
        }
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    public void initialize(){
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameters;
        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        imu.initialize(myIMUparameters);
        imu.resetYaw();
    }
}
