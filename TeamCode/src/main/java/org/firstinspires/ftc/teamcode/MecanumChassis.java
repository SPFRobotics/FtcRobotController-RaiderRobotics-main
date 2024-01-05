package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumChassis {
    public LinearOpMode opmode = null;
    public static final double strafeMult = 1.1;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;
    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor liftLeft = null;
    public DcMotor liftRight = null;
    public IMU imu = null;
    public MecanumChassis(LinearOpMode lom){
        opmode = lom;
    }
    public double inch_convert(double inch) { return inch * (537.7 / (3.78 * Math.PI)); }
    public double inToCm(int inches) { return inches * 2.54; }
    public double cm_convert(double cm) { return cm * (537.7 / (9.6012 / Math.PI)); }
    public void initializeMovement() {
        backLeft = opmode.hardwareMap.dcMotor.get("backLeft");
        backRight = opmode.hardwareMap.dcMotor.get("backRight");
        frontLeft = opmode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opmode.hardwareMap.dcMotor.get("frontRight");

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        stop_and_reset_encoders_all();

        imu = opmode.hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
        //waitForStart();
    }
    public void stop_and_reset_encoders_all() {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void run_to_position_all() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void powerZero() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }

    public void move(double movePower, String moveDirection, double moveDistance){
        stop_and_reset_encoders_all(); //Sets encoder count to 0
        if (moveDirection.equals("forward")) {
            //Tell each wheel to move a certain amount
            backLeft.setTargetPosition((int) inch_convert(moveDistance)); //Converts the
            backRight.setTargetPosition((int) inch_convert(moveDistance));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance));
            frontRight.setTargetPosition((int) inch_convert(moveDistance));
            run_to_position_all();
            opmode.telemetry.addData("Power", movePower);
            opmode.telemetry.update();
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else if (moveDirection.equals("backward")) {
            backLeft.setTargetPosition((int) inch_convert(-moveDistance));
            backRight.setTargetPosition((int) inch_convert(-moveDistance));
            frontLeft.setTargetPosition((int) inch_convert(-moveDistance));
            frontRight.setTargetPosition((int) inch_convert(-moveDistance));
            run_to_position_all();
            backLeft.setPower(-movePower);
            backRight.setPower(-movePower);
            frontLeft.setPower(-movePower);
            frontRight.setPower(-movePower);
        } else if (moveDirection.equals("right")) {
            backLeft.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            backRight.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            frontRight.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            run_to_position_all();
            backLeft.setPower(-movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(-movePower);
        } else if (moveDirection.equals("left")) {
            backLeft.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            backRight.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            frontRight.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            run_to_position_all();
            backLeft.setPower(movePower);
            backRight.setPower(-movePower);
            frontLeft.setPower(-movePower);
            frontRight.setPower(movePower);
        } else {
            opmode.telemetry.addData("Error", "move direction must be forward,backward,left, or right.");
            opmode.telemetry.update();
            opmode.terminateOpModeNow();
        }
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            opmode.telemetry.addData("test", "attempting to move...");
            opmode.telemetry.addData("power back right", backRight.getPower());
            opmode.telemetry.addData("power back left", backLeft.getPower());
            opmode.telemetry.addData("power front right", frontRight.getPower());
            opmode.telemetry.addData("power front left", frontLeft.getPower());
            opmode.telemetry.update();
        }
        powerZero();
        opmode.telemetry.addData("test", "done!");
        opmode.telemetry.update();
    }
    public void rotate(double angle, double power) {
        double stopError = 0.5;
        double Kp = 0.5; //this is for proportional control (ie. the closer you are the target angle the slower you will go)
        double startAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double targetAngle = startAngle + angle;
        double error = (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
        double power1 = power;
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // rotate until the target angle is reached
        System.out.printf("%f start angle = ",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        System.out.printf("%f error = ", error);
        while (opmode.opModeIsActive() && Math.abs(error) > stopError) {
            //powerZero();
            error = AngleUnit.normalizeDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            // the closer the robot is to the target angle, the slower it rotates
            //power = Range.clip(Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle) / 90, 0.1, 0.5);
            power1 = power * error * Kp;
            power1 = Range.clip(power1,-0.5,0.5); //"Range.clip(value, minium, maxium)" takes the first term and puts it in range of the min and max provided
            opmode.telemetry.addData("power1",power1);
            System.out.printf("%f power = ",power1);
            opmode.telemetry.addData("error",error);
            opmode.telemetry.addData("power", power);
            opmode.telemetry.addData("Kp",Kp);

            backLeft.setPower(power1);
            backRight.setPower(-power1);
            frontLeft.setPower(power1);
            frontRight.setPower(-power1);
            if (Math.abs(error) <= stopError) {
                powerZero();
            }
            opmode.telemetry.addData("angle", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            opmode.telemetry.addData("target", targetAngle);
            opmode.telemetry.update();
            //double angleDifference = Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            //rotate(angleDifference, power);
        }
        powerZero();
    }
    public void parkFarRed(){
        move(.3, "forward", 5.5);
        move(.3, "right", 96);
    }
    public void parkCloseRed(){
        move(.3, "forward", 3);
        move(.3, "right", 46);
    }
    public void parkFarBlue(){
        move(.3, "forward", 5.5);
        move(.3, "left", 96);
    }
    public void parkCloseBlue(){
        move(.3, "forward", 3);
        move(.3, "left", 46);
    }
    public void goToPixel(int placement){
        if (placement == 1){
            move(.3, "forward", 40);
            rotate(-90, .3);
            move(.3, "forward", 60);
        } else if (placement == 2){
            move(.3, "forward", 40);
            move(.3, "left", 48);
            move(.3, "forward", 12);
            rotate(-90, .3);
            move(.3, "forward", 24);
        } else if (placement == 3){
            move(.3, "forward", 40);
            move(.3, "left", 48);
            move(.3, "forward", 24); rotate(-90, .3); move(.3, "forward", 24);
        } else if (placement == 4){
            move(.3, "forward", 40);
            move(.3, "left", 48);
            move(.3, "forward", 48);
            rotate(-90, .3);
            move(.3, "forward", 24);
        } else if (placement == 5){
            move(.3, "forward", 40);
            move(.3, "left", 48);
            move(.3, "forward", 60);
            rotate(-90, .3);
            move(.3, "forward", 24);
        } else if (placement == 6){
            move(.3, "forward", 40);
            move(.3, "left", 50);
            move(.3, "forward", 72);
            rotate(-90, .3);
            move(.3, "forward", 24);
        }
    }
    public void adjust(String direction, double power){
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (direction.equals("forward")){
            backLeft.setPower(power);
            backRight.setPower(power);
            frontLeft.setPower(power);
            frontRight.setPower(power);
        }
        if (direction.equals("backward")){
            backLeft.setPower(-power);
            backRight.setPower(-power);
            frontLeft.setPower(-power);
            frontRight.setPower(-power);
        }
        if (direction.equals("left")){
            backLeft.setPower(power);
            backRight.setPower(-power);
            frontLeft.setPower(-power);
            frontRight.setPower(power);
        }
        if (direction.equals("right")){
            backLeft.setPower(-power);
            backRight.setPower(power);
            frontLeft.setPower(power);
            frontRight.setPower(-power);
        }
        if (direction.equals("backLeft")){
            backLeft.setPower(0);
            backRight.setPower(-power);
            frontLeft.setPower(-power);
            frontRight.setPower(0);
        }
        if (direction.equals("backRight")){
            backLeft.setPower(-power);
            backRight.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(-power);
        }
        if (direction.equals("frontLeft")){
            backLeft.setPower(power);
            backRight.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(power);
        }
        if (direction.equals("frontRight")){
            backLeft.setPower(0);
            backRight.setPower(power);
            frontLeft.setPower(power);
            frontRight.setPower(0);
        }
    }
}

