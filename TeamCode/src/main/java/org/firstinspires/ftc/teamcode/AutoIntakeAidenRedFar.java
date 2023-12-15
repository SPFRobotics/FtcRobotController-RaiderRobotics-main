package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.openftc.easyopencv.OpenCvCameraFactory;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;


@Autonomous
public class AutoIntakeAidenRedFar extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    //Intake intake = new Intake(this);
    ColorCam color = new ColorCam(this);
    aprilTagDetectionMovement aTag = new aprilTagDetectionMovement(this);
    //LinearSlide slide = new LinearSlide(this);

    public void placeOnSpikeMark(){
        //Move to center of spike marks
        double power = -.3;
        if(color.spikeLocation.equals("LEFT")) {
            chassis.move(.3, "forward", 18);
            chassis.move(.3, "left", 12);
            //intake.powerOnTimed(power, 3);
            chassis.move(.3, "right", 12);
            chassis.move(.3, "backward", 18);
        } else if(color.spikeLocation.equals("RIGHT")){
            chassis.move(.3, "forward", 18);
            chassis.move(.3, "right", 12);
            //intake.powerOnTimed(power, 3);
            chassis.move(.3, "left", 12);
            //chassis.move(.3, "backward", 18);
        } else if(color.spikeLocation.equals("CENTER")){
            chassis.move(.3, "forward", 25);
            //intake.powerOnTimed(power, 3);
            chassis.move(.3, "backward", 25);
        } else {
            telemetry.addData("Team Element", "Not Found");
            telemetry.update();
        }
    }
    public aprilTagDetectionMovement.backBoardAprilTags altAprilTag(String loc){
        if(loc.equals("left")){
            return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceLeft;
        }
        if(loc.equals("center")){
            return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceCenter;
        }
        if(loc.equals("right")){
            return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceRight;
        }
        return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceLeft;
    }


    public void runOpMode(){
        chassis.initializeMovement();
        //intake.initIntake();
        color.initCam();
        color.camOn();

        while(!isStarted()){
            color.updateSpikeLocation();
            telemetry.addData("Location", color.spikeLocation);
            telemetry.update();
        }

        waitForStart();
        final String location = color.spikeLocation;
        placeOnSpikeMark();
        color.camOff();
        chassis.move(.5, "forward", 25);
        chassis.rotate(-90, .5);
        aTag.initCam();
        aTag.camOn();

        aprilTagDetectionMovement.backBoardAprilTags[] array = {altAprilTag(location)};
        aTag.moveToAprilTag(array[0]);

        //aTag.camOff();
        chassis.rotate(180, .5);

        chassis.move(.5, "left", aTag.robotDistanceToAprilTag[0]);
        chassis.move(.5, "backward", aTag.robotDistanceToAprilTag[1]);
        telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",aTag.outputInfo[0],aTag.outputInfo[1]));
        //telemetry.addData("hooray","hooray");
        telemetry.update();
        //chassis.parkFarRed();
    }
}