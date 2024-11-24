package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
//import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;
// START WITH ROBOT ON F4 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class AutoRedObsZoneLuke extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    LinearSlide slide = new LinearSlide(this);
    Extendo extendo = new Extendo(this);
    Claw claw = new Claw(this);

    @Override
    public void runOpMode() throws InterruptedException
    {
        chassis.initializeMovement();
        slide.initSlides();
        extendo.initSlides();
        claw.init();
        waitForStart();
        slide.slide(19.5, .6); // raises lift high enough to place specimen on chamber
        chassis.move(.5, "forward", 81.28); // should move towards submersible
        chassis.move(.5,"left",30); // should move towards submersible
        slide.slide(-3,.6); // lowers lift (specimen should attach by now)
        claw.open(); // let go of specimen
        // next 2 move commands move towards parking zone
        chassis.move(.5, "backward", 75);
        chassis.move(.5, "right", 100);
        slide.slide(-16.5, .6);
        // measurements past here are guesswork, they have to be tested (SO DOES THE CLAW)
        // once all measurements are finetuned this should place a pre-loaded specimen and park in the observation zone (13pts)
    }
}
