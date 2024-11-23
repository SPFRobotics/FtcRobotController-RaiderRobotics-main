package org.firstinspires.ftc.teamcode.Hardware.Robot;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


public class AprilTagDist {
    public double inToCm(double inches) { return inches * 2.54; }
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .build();
    public double getDist(){
        double dist = 0;
            if (!tagProcessor.getDetections().isEmpty()){
                AprilTagDetection tag = tagProcessor.getDetections().get(0);
                double x = tag.ftcPose.x;
                double y = tag.ftcPose.y;
                dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            }
        return dist;
    }
    public double getDistX(){
        double dist = 0;
        if (!tagProcessor.getDetections().isEmpty()){
            AprilTagDetection tag = tagProcessor.getDetections().get(0);
            dist = tag.ftcPose.x;
        }
        return dist;
    }
    public double getDistY(){
        double dist = 0;
        if (!tagProcessor.getDetections().isEmpty()){
            AprilTagDetection tag = tagProcessor.getDetections().get(0);
            dist = tag.ftcPose.y;
        }
        return dist;
    }
}



