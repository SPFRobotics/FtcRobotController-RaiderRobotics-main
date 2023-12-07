package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class newAutoGroup extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    Intake intake = new Intake(this);
    ColorCam color = new ColorCam(this);
    AprilTagCam aTag = new AprilTagCam(this);

    public void placeOnSpikeMark(){
        //Move to center of spike marks
        double power = -.3;
        if(color.spikeLocation.equals("LEFT")) {
            chassis.move(.3, "forward", 18);
            chassis.move(.3, "left", 12);
            intake.powerOnTimed(power, 3);
            chassis.move(.3, "right", 12);
            chassis.move(.3, "backward", 18);
        } else if(color.spikeLocation.equals("RIGHT")){
            chassis.move(.3, "forward", 18);
            chassis.move(.3, "right", 12);
            intake.powerOnTimed(power, 3);
            chassis.move(.3, "left", 12);
            chassis.move(.3, "backward", 18);
        } else if(color.spikeLocation.equals("CENTER")){
            chassis.move(.3, "forward", 25);
            intake.powerOnTimed(power, 3);
            chassis.move(.3, "backward", 25);
        } else {
            telemetry.addData("Team Element", "Not Found");
            telemetry.update();
        }
    }

    public void runOpMode(){
        chassis.initializeMovement();
        intake.initIntake();
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

    }

}
