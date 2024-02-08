package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Disabled
public class AprilTagCam{
    public LinearOpMode opmode = null;
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    double[] cameraOffset = new double[] {-3.5,0}; // x offset (left: negative, right: positive), y(distance) offset; (units: inches from center)
    double[] robotDistanceToAprilTag = new double[] {0,0};
    public enum backBoardAprilTags {
        RedAllianceLeft,
        RedAllianceCenter,
        RedAllianceRight,
        BlueAllianceLeft,
        BlueAllianceCenter,
        BlueAllianceRight
    }
    public MecanumChassis chassis;
    public int id = 0;



    public AprilTagCam(LinearOpMode lom){
        opmode = lom;
    }
    public void initCam(){
        aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(opmode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();

        chassis = new MecanumChassis(opmode);
    }
    public void moveToAprilTag(aprilTagDetectionMovement.backBoardAprilTags tagName) {
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
        opmode.telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",robotDistanceToAprilTag[0],robotDistanceToAprilTag[1]));
        if (foundAprilTag) {
            chassis.move(0.5,"right",robotDistanceToAprilTag[0]);
            chassis.rotate(180,0.5);
            chassis.move(0.5,"backward",(robotDistanceToAprilTag[1]-5));
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
        opmode.telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",robotDistanceToAprilTag[0],robotDistanceToAprilTag[1]));
        if (foundAprilTag) {
            chassis.move(0.5,"right",robotDistanceToAprilTag[0]);
            chassis.rotate(180,0.5);
            chassis.move(0.5,"backward",(robotDistanceToAprilTag[1]-5));
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
}
