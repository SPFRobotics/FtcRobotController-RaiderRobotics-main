/*package org.firstinspires.ftc.teamcode.OpModes2324.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlideOutdated;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;


@Autonomous(preselectTeleOp = "teleOpCombinedDrivesComp2")
public class AutoIntakeAidenBlueClose extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    Intake intake = new Intake(this);
    //ColorCam color = new ColorCam(this);
    aprilTagDetectionMovement aTag = new aprilTagDetectionMovement(this);
    LinearSlideOutdated slide = new LinearSlideOutdated(this);
    private double timeToContinue = 5;
    private ElapsedTime continueTime = new ElapsedTime();


    public void placeOnSpikeMarkUpdated(String proximity){
        //Move to center of spike marks
        double power = -.3;
        if(proximity.toLowerCase().equals("close")) {
            //Aligned to the right
            //Check for left and center
            if(aTag.spikeLocation.equals("RIGHT")) {
                //Move to the right
                chassis.move(.5, "forward", 26);
                //chassis.move(.5, "left", 4);
                chassis.rotate(-90,.5);

                chassis.move(.5,"forward",0+8);
                chassis.move(.5,"backward",4);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",4);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                //chassis.move(.5, "right", 6);
                //chassis.move(.5,"forward",4);
                chassis.rotate(90,.5);
            } else if (aTag.spikeLocation.equals("CENTER")) {
                //Move to the center
                chassis.move(.5, "forward", 23+10);
                chassis.move(.5, "right", 2);
                chassis.move(.5,"backward",6);
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",6);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                //chassis.move(.5,"backward",2);
                //chassis.move(.5, "left", 2);
            } else {
                //Move to the left
                chassis.move(.5, "forward", 23+4);
                chassis.move(.5, "left", 8);
                chassis.move(.5,"backward",4);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",6);
                intake.raiseLip();
                chassis.move(.5, "right", 10);
                chassis.move(.5,"forward",6);
            }
        }
        if(proximity.toLowerCase().equals("far")) {
            //Aligned to the left
            //Check for center and right
            if(aTag.spikeLocation.equals("LEFT")) {
                //Move to the center
                chassis.move(.5, "forward", 23+10);
                chassis.move(.5, "left", 4);
                chassis.move(.5,"backward",6);
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",6);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                //chassis.move(.5,"backward",2);
                chassis.move(.5, "right", 4);
            } else if (aTag.spikeLocation.equals("CENTER")) {
                //Move to the right
                chassis.move(.5, "forward", 23+4);
                chassis.move(.5, "left", 8);
                chassis.move(.5,"backward",4);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",6);
                intake.raiseLip();
                //chassis.move(.5, "backward", 24);
                chassis.move(.5, "right", 12);
                chassis.move(.5,"forward",6);
            } else {
                //Move to the left
                chassis.move(.5, "forward", 26);
                //chassis.move(.5, "left", 4);
                chassis.rotate(90,.5);
                chassis.move(.5,"forward",0+8);
                chassis.move(.5,"backward",4);
                //Do Intake Servo
                intake.lowerLip();
                sleep(1000);
                chassis.move(.5,"backward",4);
                intake.raiseLip();
                //chassis.move(.5, "backward", 30);
                //chassis.move(.5, "right", 6);
                //chassis.move(.5,"forward",4);
                chassis.rotate(-90,.5);
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
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceRight;
                }
                if (loc.equals("CENTER")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceCenter;
                } else {
                    return aprilTagDetectionMovement.backBoardAprilTags.RedAllianceLeft;
                }
            }
        }
        if(teamColor.equals("blue")) {
            if (proximity.equals("close")) {
                if (loc.equals("RIGHT")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceRight;
                }
                if (loc.equals("CENTER")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceCenter;
                } else {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceLeft;
                }
            }
            if (proximity.equals("far")) {
                if (loc.equals("LEFT")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceCenter;
                }
                if (loc.equals("CENTER")) {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceRight;
                } else {
                    return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceLeft;
                }
            }
        }
        return aprilTagDetectionMovement.backBoardAprilTags.BlueAllianceRight;
    }


    public void runOpMode(){
        chassis.initializeMovement();
        intake.initIntake();
        slide.initSlides();
        aTag.initCam2();
        aTag.camOn();

        while(!isStarted()){
            aTag.updateSpikeLocation();
            switch (aTag.spikeLocation) {
                case "NONE":
                    telemetry.addData("Location", "RIGHT");
                    break;
                default:
                    telemetry.addData("Location", aTag.spikeLocation);
                    break;
            }
            //telemetry.addData("Location", aTag.spikeLocation);
            telemetry.update();
        }

        waitForStart();
        final String location = aTag.spikeLocation;
        //chassis.rotate(-90,0.5);
        placeOnSpikeMarkUpdated("close");
        //aTag.camOff();
        //chassis.move(.5, "forward", 25);
        chassis.rotate(90, .5);
        chassis.move(.5,"right",8);
        //aTag.initCam2(); //Maybe reinitializing will fix the detection?
        //aTag.camOn();

        //aprilTagDetectionMovement.backBoardAprilTags[] array = {altAprilTag(location)};
        //aTag.moveToAprilTag(array[0]);
        continueTime.reset();
        //while (aTag.getDetections().size() < 3 && opModeIsActive()) {
        while (aTag.getDetections().size() < 3 && continueTime.seconds() <= timeToContinue && opModeIsActive()) {
            telemetry.addData("%f",aTag.getDetections().size());
            telemetry.update();
            sleep(10);
        }
        //telemetry.addData("%f",aTag.getDetections().size());
        //telemetry.update();
        //sleep(5000);
        aTag.moveToAprilTag(altAprilTag(location, "close", "blue"));

        aTag.camOff();
        chassis.move(.5,"left",15);
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
        terminateOpModeNow();
    }
}*/