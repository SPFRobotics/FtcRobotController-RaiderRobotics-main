package org.firstinspires.ftc.teamcode.OpModes.Auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Robot.AprilTagDist;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;

// START WITH ROBOT ON A3 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class AutoObsZoneLuke extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    LinearSlide slide = new LinearSlide(this);
    Extendo extendo = new Extendo(this);
    Claw claw = new Claw(this);
    Servo wristClawServo = null;
    // AprilTagDist AprilTagDistance = new AprilTagDist(this);
    @Override
    public void runOpMode() throws InterruptedException
    {
        wristClawServo = hardwareMap.get(Servo .class, "intakeWrist");
        slide.initSlides();
        extendo.initSlides();
        claw.init();
        wristClawServo.setPosition(1);
        waitForStart();
        /* PLACES 2 WITHOUT ROADRUNNER
        //telemetry.addData("X", AprilTagDistance.getDistX());
        //telemetry.addData("Y", AprilTagDistance.getDistY());
        chassis.move(.5,"left",30); // should move towards submersible
        chassis.moveMultitask(.5, "forward", 69.01,24, 1); // should move towards submersible
        //telemetry.addData("X", AprilTagDistance.getDistX());
        //telemetry.addData("Y", AprilTagDistance.getDistY());
        slide.slide(18.5,1); // lowers lift (specimen should attach by now)
        claw.open(); // let go of specimen
        // next 2 move commands move towards parking zone

        chassis.move(.5, "backward", 50);
        chassis.moveMultitask(.5, "right", 86, 0,1);


        chassis.rotate(-180, 0.5);
        chassis.move(.5, "forward", 19.01);
        claw.close();
        slide.slide(5,1);
        chassis.move(.5, "backward", 19.01);
        chassis.rotate(-180, 0.5);
        chassis.moveMultitask(.5, "left", 80,24, 1);
        chassis.move(.5, "forward", 55.5);
        slide.slide(18.5, 1);
        claw.open();
        chassis.move(.5, "backward", 60);
        chassis.moveMultitask(.5, "right", 130, 0, 1);
        */

    }
}
