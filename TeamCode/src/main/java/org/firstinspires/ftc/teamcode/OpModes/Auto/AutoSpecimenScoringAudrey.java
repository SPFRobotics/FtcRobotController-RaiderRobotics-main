package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
//import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;
//hi
@Autonomous
public class AutoSpecimenScoringAudrey extends LinearOpMode{
    MecanumChassis chassis = new MecanumChassis(this);
    LinearSlide slide = new LinearSlide(this);
    Extendo extendo = new Extendo(this);
    Claw claw = new Claw(this);

    @Override
    public void runOpMode() throws InterruptedException
    {
        chassis.initializeMovement();
        extendo.initSlides();
        claw.init();
        slide.initSlides();
        waitForStart();

        chassis.rotate(90, .5);
        chassis.move(.5, "forward", 60);
        claw.open();
        //turned and placed loaded sample into observation zone for human player to attach a clip on it

        chassis.move(.5,"backward",60);
        chassis.move(.5,"left",60);
        chassis.move(.5,"forward",60);
        chassis.rotate(90,.5);
        //positioned outside of the zone facing the wall, waiting for the specimen to be clipped on the wall

        //use color detection here: detect that the clip is there, then move to pick it up off the wall
        // me & saanvi are tryna get that working, i'm not sure of how to code it tho once it does work

        chassis.move(.5,"forward",60);
        claw.close();
        slide.slide(4,.6);
        //picked specimen up off the wall

        chassis.move(.5,"backward",60);
        chassis.rotate(90,.5);
        chassis.move(.5,"forward",90);
        chassis.rotate(90,.5);
        //position chassis in front of rungs to score




    }

}
