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
import org.firstinspires.ftc.teamcode.cameraDetectColorTest2;

import java.util.List;
import java.util.Vector;

public class aprilTagDetectionMovement {
    public LinearOpMode opmode = null;
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    cameraDetectColorTest2 visionProcessor;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private IMU imu = null;
    private static final double strafeMult = 1.1;
    public int id = 0;
    public String spikeLocation = null;

    public enum backBoardAprilTags {
        RedAllianceLeft,
        RedAllianceCenter,
        RedAllianceRight,
        BlueAllianceLeft,
        BlueAllianceCenter,
        BlueAllianceRight
    }
    private double[] cameraOffset = new double[] {7.5,3.5}; // x offset (left: positive, right: negative), y(distance) offset; (units: inches from center)
    public double[] robotDistanceToAprilTag = new double[] {0,0};
    private double moveOffsetY = 10;
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
    public void initCam2(){
        visionProcessor = new cameraDetectColorTest2();
        aprilTag = new AprilTagProcessor.Builder().build();
        aprilTag.setDecimation(2);
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(opmode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.addProcessors(aprilTag,visionProcessor);
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
        if (foundAprilTag) {
            outputInfo = new double[] {robotDistanceToAprilTag[0], (robotDistanceToAprilTag[1] - moveOffsetY)};
        } else {
            outputInfo = new double[] {0,28};
        }
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
        if (foundAprilTag) {
            outputInfo = new double[] {robotDistanceToAprilTag[0],(robotDistanceToAprilTag[1] - moveOffsetY)}; //[xPos, yPos]
        }

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

    public List<AprilTagDetection> getDetections(){
        return aprilTag.getDetections();
    }
    public void updateSpikeLocation(){
        spikeLocation = visionProcessor.getPosition().toString();
    }
}
