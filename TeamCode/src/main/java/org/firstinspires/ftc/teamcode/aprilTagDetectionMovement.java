package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.Vector;

public class aprilTagDetectionMovement {
    public LinearOpMode opmode = null;
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private IMU imu = null;
    private static final double strafeMult = 1.1;
    public int id = 0;

    public enum backBoardAprilTags {
        RedAllianceLeft,
        RedAllianceCenter,
        RedAllianceRight,
        BlueAllianceLeft,
        BlueAllianceCenter,
        BlueAllianceRight
    }
    private double[] cameraOffset = new double[] {3.5,5.5}; // x offset (left: positive, right: negative), y(distance) offset; (units: inches from center)
    public double[] robotDistanceToAprilTag = new double[] {0,0};
    private double moveOffsetY = 20;
    public double[] outputInfo = new double[] {};

    /*@Override
    public void runOpMode(){
        initCam();
        while (!isStarted()) {
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            opmode.telemetry.addData("%f",currentDetections.size());
            opmode.telemetry.update();
            //wait(1);
        }
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                //opmode.telemetryAprilTag();
                moveToAprilTag(backBoardAprilTags.RedAllianceLeft);

                // Push opmode.telemetry to the Driver Station.
                opmode.telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }
    }*/

    public aprilTagDetectionMovement(LinearOpMode lom) {opmode = lom;}
    public void initCam(){
        aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(opmode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }
    public void camOn() {
        visionPortal.resumeStreaming();
    }
    public void camOff() {
        visionPortal.stopStreaming();
    }
    public void moveToAprilTag(backBoardAprilTags tagName) {
        outputInfo = new double[] {};
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        boolean foundAprilTag = false;
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.metadata.name == tagName.toString()) {
                    robotDistanceToAprilTag[0] = detection.ftcPose.x + cameraOffset[0];
                    robotDistanceToAprilTag[1] = detection.ftcPose.y + cameraOffset[1];
                    foundAprilTag = true;
                }
            } else { //add to move on to next step or align to a position
                break;
            }
        }
        //opmode.opmode.telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",robotDistanceToAprilTag[0],robotDistanceToAprilTag[1]));
        /*if (foundAprilTag) {
            move(0.5,"right",robotDistanceToAprilTag[0]);
            rotate(180,0.5);
            move(0.5,"backward",(robotDistanceToAprilTag[1]-20));
        }*/
        outputInfo = new double[] {robotDistanceToAprilTag[0],(robotDistanceToAprilTag[1] - moveOffsetY)};
    }
    public void moveToAprilTag(int id) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        boolean foundAprilTag = false;
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.id == id) {
                    robotDistanceToAprilTag[0] = detection.ftcPose.x + cameraOffset[0];
                    robotDistanceToAprilTag[1] = detection.ftcPose.y + cameraOffset[1];
                    foundAprilTag = true;
                }
            } else { //add to move on to next step or align to a position
                break;
            }
        }
        //opmode.opmode.telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",robotDistanceToAprilTag[0],robotDistanceToAprilTag[1]));
        /*if (foundAprilTag) {
            move(0.5,"right",robotDistanceToAprilTag[0]);
            rotate(180,0.5);
            move(0.5,"backward",(robotDistanceToAprilTag[1]-20));
        }*/
        outputInfo = new double[] {robotDistanceToAprilTag[0],(robotDistanceToAprilTag[1] - moveOffsetY)}; //[xPos, yPos]
    }
    public void setId(String spikeLocation, String team){
        if(spikeLocation.equals("left")){
            id = 1;
        } else if(spikeLocation.equals("center")){
            id = 2;
        } else if(spikeLocation.equals("right")){
            id = 3;
        }
        if(team.equals("red")){
            id += 3;
        }
    }
    /*public void stop_and_reset_encoders_all() {
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
    public double inch_convert(double inch) { return inch * (537.7 / (3.78 * Math.PI)); }
    public void move(double movePower, String moveDirection, double moveDistance) {
        stop_and_reset_encoders_all(); //Sets encoder count to 0
        if (moveDirection.equals("forward")) {
            backLeft.setTargetPosition((int) inch_convert(moveDistance));
            backRight.setTargetPosition((int) inch_convert(moveDistance));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance));
            frontRight.setTargetPosition((int) inch_convert(moveDistance));
            run_to_position_all();
            //opmode.telemetry.addData("Power", movePower);
            //opmode.telemetry.update();
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
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else if (moveDirection.equals("right")) {
            backLeft.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            backRight.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            frontRight.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            run_to_position_all();
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else if (moveDirection.equals("left")) {
            backLeft.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            backRight.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            frontRight.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            run_to_position_all();
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else {
            opmode.telemetry.addData("Error", "move direction must be forward,backward,left, or right.");
            //opmode.telemetry.update();
            terminateOpModeNow();
        }
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            opmode.telemetry.addData("test", "attempting to move...");
            //opmode.telemetry.addData("power back right", backRight.getPower());
            //opmode.telemetry.addData("power back left", backLeft.getPower());
            //opmode.telemetry.addData("power front right", frontRight.getPower());
            //opmode.telemetry.addData("power front left", frontLeft.getPower());
            opmode.telemetry.update();
        }
        powerZero();
    }
    public void rotate(double angle, double power) {
        double Kp = 0.5; //this is for porposanal control (ie. the closer you are the target angle the slower you will go)
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
        while (opModeIsActive() && Math.abs(error) > 5) {
            //powerZero();
            error = AngleUnit.normalizeDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            // the closer the robot is to the target angle, the slower it rotates
            //power = Range.clip(Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle) / 90, 0.1, 0.5);
            power1 = power * error * Kp;
            power1 = Range.clip(power1,-0.5,0.5); //"Range.clip(value, minium, maxium)" takes the first term and puts it in range of the min and max provided
            opmode.telemetry.addData("power1",power1);
            //System.out.printf("%f power = ",power1);
            opmode.telemetry.addData("error",error);
            opmode.telemetry.addData("power", power);
            opmode.telemetry.addData("Kp",Kp);

            backLeft.setPower(power1);
            backRight.setPower(-power1);
            frontLeft.setPower(power1);
            frontRight.setPower(-power1);
            if (Math.abs(error) <= 1) {
                powerZero();
            }
            opmode.telemetry.addData("angle", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            opmode.telemetry.addData("target", targetAngle);
            opmode.telemetry.update();
            //double angleDifference = Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            //rotate(angleDifference, power);
        }
        powerZero();
    }*/
    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        opmode.telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                opmode.telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                opmode.telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                opmode.telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                opmode.telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                opmode.telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                opmode.telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to opmode.telemetry
        opmode.telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        opmode.telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        opmode.telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method opmode.telemetryAprilTag()
}
