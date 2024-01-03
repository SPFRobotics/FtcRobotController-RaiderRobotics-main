package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
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
public class AutoIntakeAidenRedClose extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    Intake intake = new Intake(this);
    //ColorCam color = new ColorCam(this);
    aprilTagDetectionMovement aTag = new aprilTagDetectionMovement(this);
    LinearSlide slide = new LinearSlide(this);
    private double timeToContinue = 5;
    private ElapsedTime continueTime = new ElapsedTime();

    public void placeOnSpikeMarkUpdated(String proximity){
        //Move to center of spike marks
        double power = -.3;
        if(proximity.toLowerCase().equals("close")) {
            //Aligned to the right
            //Check for left and center
            if(aTag.spikeLocation.equals("LEFT")) {
                //Move to the center
                chassis.move(.5, "forward", 23+10);
                //chassis.move(.5, "left", 0);
                chassis.move(.5,"backward",4);
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",6);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                //chassis.move(.5,"backward",2);
                //chassis.move(.5, "right", 6);
            } else if (aTag.spikeLocation.equals("CENTER")) {
                //Move to the right
                chassis.move(.5, "forward", 23+4);
                //chassis.move(.5, "right", 8);
                chassis.move(.5,"backward",4);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",6);
                intake.raiseLip();
                //chassis.move(.5, "backward", 24);
                //chassis.move(.5, "left", 0);
                chassis.move(.5,"forward",6);
            } else {
                //Move to the left
                chassis.move(.5, "forward", 23);
                //chassis.move(.5, "left", 4);
                chassis.rotate(90,.5);
                chassis.move(.5,"forward",0+8);
                chassis.move(.5,"backward",2);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",6);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                //chassis.move(.5, "right", 6);
                //chassis.move(.5,"forward",4);
                chassis.rotate(-90,.5);
            }
        }
        if(proximity.toLowerCase().equals("far")) {
            //Aligned to the left
            //Check for center and right
            if (aTag.spikeLocation.equals("CENTER")) {
                //Move to the left
                chassis.move(.5, "forward", 24);
                chassis.move(.5, "left", 8);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                intake.raiseLip();
                chassis.move(.5, "backward", 24);
                chassis.move(.5, "right", 8);
            } else if (aTag.spikeLocation.equals("RIGHT")) {
                //move to the center
                chassis.move(.5, "forward", 30);
                chassis.move(.5, "right", 6);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                chassis.move(.5, "left", 6);
            } else {
                //move to the right
                chassis.move(.5, "forward", 30);
                chassis.move(.5, "right", 18);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                chassis.move(.5, "left", 18);
            }
        }
    }
    public aprilTagDetectionMovement.backBoardAprilTags altAprilTag(String loc, String proximity, String teamColor){
        if(teamColor.equals("red")) {
            if (proximity.equals("close")) {
                if (loc.equals("LEFT")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceCenter;
                }
                if (loc.equals("CENTER")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceRight;
                } else {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceLeft;
                }
            }
            if (proximity.equals("far")) {
                if (loc.equals("RIGHT")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceCenter;
                }
                if (loc.equals("CENTER")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceLeft;
                } else {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceRight;
                }
            }
        }
        if(teamColor.equals("blue")) {
            if (proximity.equals("close")) {
                if (loc.equals("LEFT")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceCenter;
                }
                if (loc.equals("CENTER")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceRight;
                } else {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceLeft;
                }
            }
            if (proximity.equals("far")) {
                if (loc.equals("RIGHT")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceCenter;
                }
                if (loc.equals("CENTER")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceLeft;
                } else {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceRight;
                }
            }
        }
        return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceCenter;
    }


    public void runOpMode(){
        chassis.initializeMovement();
        intake.initIntake();
        slide.initSlides();
        aTag.initCam2();
        aTag.camOn();

        while(!isStarted()){
            aTag.updateSpikeLocation();
            telemetry.addData("Location", aTag.spikeLocation);
            telemetry.update();
        }

        waitForStart();
        final String location = aTag.spikeLocation;
        //chassis.rotate(-90,0.5);
        placeOnSpikeMarkUpdated("close");
        //aTag.camOff();
        //chassis.move(.5, "forward", 25);
        chassis.rotate(-90, .5);
        //chassis.move(.5, "forward", 24);
        //chassis.move(.5, "left", 6);
        chassis.move(.5,"left",8);
        //aTag.initCam2(); //Maybe reinitializing will fix the thing?
        //aTag.camOn();

        //aprilTagDetectionMovement.backBoardAprilTags[] array = {altAprilTag(location)};
        //aTag.moveToAprilTag(array[0]);

        //aTag.initCam2();
        continueTime.reset();
        while (aTag.getDetections().size() < 3) {
        //while (aTag.getDetections().size() < 3 && continueTime.seconds() <= timeToContinue) {
            telemetry.addData("%f",aTag.getDetections().size());
            telemetry.update();
            sleep(10);
        }
        telemetry.addData("%f",aTag.getDetections().size());
        telemetry.update();
        sleep(1000);
        aTag.moveToAprilTag(altAprilTag(location, "close", "red"));

        //aTag.camOff();
        chassis.rotate(180, .5);

        telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",aTag.outputInfo[0],aTag.outputInfo[1]));
        telemetry.update();
        chassis.move(.5, "left", aTag.outputInfo[0]);
        chassis.move(.5, "backward", aTag.outputInfo[1]);
        slide.slide(35,0.5);
        sleep(1000);
        slide.slide(0,0.5);
        aTag.camOff();
        //telemetry.addData("hooray","hooray");
        telemetry.update();
        //chassis.parkFarRed();
    }
}