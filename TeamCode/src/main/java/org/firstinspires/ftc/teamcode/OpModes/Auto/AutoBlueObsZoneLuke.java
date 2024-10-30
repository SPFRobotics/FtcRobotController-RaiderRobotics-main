package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;

@Autonomous
public class AutoBlueObsZoneLuke extends LinearOpMode {
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
        slide.slide(19.5, .6);
        chassis.move(.5, "forward", 81.28);
        chassis.move(.5,"right",58.42);
        slide.LiftHold();
        slide.slide(-3,.6);
        claw.open();
        chassis.move(.5, "backward", 75);
        chassis.move(.5, "left", 154.94);
        slide.slide(-16.5, .6);
    }

}
