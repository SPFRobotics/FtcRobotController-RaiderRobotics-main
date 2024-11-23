package org.firstinspires.ftc.teamcode.OpModes.Auto;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous
public class AutoApriltagDist extends LinearOpMode {
    //hi
    public double inToCm(double inches) { return inches * 2.54; }

    @Override
    public void runOpMode() throws InterruptedException{

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


        waitForStart();
        while (!isStopRequested() && opModeIsActive()){
            if (!tagProcessor.getDetections().isEmpty()){
                AprilTagDetection tag = tagProcessor.getDetections().get(0);

                //tag.ftcPose. this is how you can get distance from x y and z axis to the tag. x left right, y is straight out, and z is up and down. We need to use the pythagorean theorem for x and y to get the distance from the tag. YAY!

                telemetry.addData("X", inToCm(tag.ftcPose.x));
                telemetry.addData("Y", inToCm(tag.ftcPose.y));
                telemetry.addData("Z", inToCm(tag.ftcPose.z));
                //telemetry.addData("roll", tag.ftcPose.roll); if used make sure to add inToCm()
                //telemetry.addData("pitch", tag.ftcPose.pitch);
                //telemetry.addData("yaw", tag.ftcPose.yaw);
            }
            telemetry.update();
        }
    }
}
