package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Disabled
public class newAutoGroupCloseRed extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    //Intake intake = new Intake(this);
    ColorCam color = new ColorCam(this);
    AprilTagCam aTag = new AprilTagCam(this);
    //LinearSlide slide = new LinearSlide(this);

    public void placeOnSpikeMark(){
        //Move to center of spike marks
        double power = -.3;
        if(color.spikeLocation.equals("LEFT")) {
            chassis.move(.3, "forward", 18);
            chassis.move(.3, "left", 12);
            //intake.powerOnTimed(power, 3);
            chassis.move(.3, "right", 12);
            //chassis.move(.3, "backward", 18);
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
    public void altAprilTag(){

    }

    public void runOpMode(){
        chassis.initializeMovement();
        //intake.initIntake();
        color.initCam();
        color.camOn();
        aTag.initCam();

        while(!isStarted()){
            color.updateSpikeLocation();
            telemetry.addData("Location", color.spikeLocation);
            telemetry.update();
        }

        waitForStart();
        final String location = color.spikeLocation;
        placeOnSpikeMark();
        color.camOff();
        aTag.setId(location, "red");
        aTag.moveToAprilTag(aTag.id);
        chassis.rotate(180, .5);
        chassis.move(.5, "left", aTag.robotDistanceToAprilTag[0]);
        chassis.move(.5, "backward", aTag.robotDistanceToAprilTag[1]);
    }
}
