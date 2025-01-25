package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.AprilTagDist;
import org.firstinspires.ftc.teamcode.Hardware.Robot.NewClaw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;

@Autonomous
public class AutoSampleToObv extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    LinearSlide slide = new LinearSlide(this);
    Extendo extendo = new Extendo(this);
    NewClaw claw = new NewClaw(this);
    AprilTagDist aprilTagDist = new AprilTagDist(this);

    @Override
    public void runOpMode() throws InterruptedException {
        double x = aprilTagDist.getDistX();
        double y = aprilTagDist.getDistY();

        if ((x > -1) && (x < 1)) {
            while (y > 61) {
                chassis.move(.2,"right",y-61); //hi
                y = aprilTagDist.getDistY();
            }

            while (y < 61) {
                chassis.move(.2,"left",61-y);
                y = aprilTagDist.getDistY();
            }
        }

    }
}
