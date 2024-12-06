package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;

// START WITH ROBOT ON A3 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class AutoObsZoneLukeTeammatePreloads extends LinearOpMode {
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
        chassis.rotate(-90+Math.toDegrees(Math.atan(2)),.6);
        chassis.move(.7, "forward",137); // Pythagorean Theorem, no need to calculate distance
        chassis.rotate(90-Math.toDegrees(Math.atan(2)),.6);
        chassis.move(.7, "backward", 122);
        chassis.move(.7, "forward", 122);
        chassis.move(.7, "right", 30);
        chassis.move(.7, "backward", 122); // 18 Seconds Left after here

        slide.slide(24,1);
        chassis.move(.7, "left", 90);



    }
}
