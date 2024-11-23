package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;

// START WITH ROBOT ON A3 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class AutoBlueObsZoneLukeTeammatePreloads extends LinearOpMode {
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
        slide.slide(24, .6); // raises lift high enough to place specimen on chamber
        chassis.move(.5,"left",30); // should move towards submersible
        chassis.move(.5, "forward", 69.01); // should move towards submersible
        slide.slide(18.5,.6); // lowers lift (specimen should attach by now)
        claw.open(); // let go of specimen


        chassis.move(.5, "backward", 5);
        chassis.move(.5, "right", 110);
        chassis.rotate(-90, .6);


    }
}
