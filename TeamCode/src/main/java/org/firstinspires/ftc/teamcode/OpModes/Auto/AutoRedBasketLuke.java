package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;
// START WITH ROBOT ON F2 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class AutoRedBasketLuke extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    LinearSlide slide = new LinearSlide(this);
    aprilTagDetectionMovement aTag = new aprilTagDetectionMovement(this);
    Extendo extendo = new Extendo(this);
    Claw claw = new Claw(this);

    @Override
    public void runOpMode() throws InterruptedException
    {
        chassis.initializeMovement();
        aTag.initCam2();
        aTag.camOn();
        slide.initSlides();
        extendo.initSlides();
        claw.init();
        slide.slide(19.5, .6); // raises lift high enough to place specimen on chamber
        chassis.move(.5, "forward", 81.28); // should move towards submersible
        chassis.move(.5,"right",58.42); // should move towards submersible
        slide.slide(-3,.6); // lowers lift (specimen should attach by now)
        claw.open(); // let go of specimen
        slide.slide(-6, .6); // lower lift THIS MEASUREMENT NEEDS TESTING, it should be aligned with lower rung
        claw.close(); // have claw closed so it can contact a rung for a level 1 ascent
        // measurements past here are guesswork, they have to be tested (SO DOES THE CLAW)
        chassis.move(.5, "left", 65);
        chassis.rotate(-90, .5);
        chassis.move(.5, "left", 70);
        chassis.move(.5, "forward", 37);
        // once all measurements are finetuned this should place a pre-loaded specimen and achieve a level 1 ascent (13pts)
    }
}
