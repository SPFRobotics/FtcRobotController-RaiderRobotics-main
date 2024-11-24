package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
//import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;

@Autonomous
public class PushBot extends LinearOpMode {
    public void runOpMode() throws InterruptedException{
        MecanumChassis chassis = new MecanumChassis(this);
        chassis.initializeMovement();

        waitForStart();
        while (opModeIsActive()){
            chassis.move(.5, "forward", 152.52);
            chassis.move(.5, "left", 15.24);
            chassis.move(.5, "backward", 113.02);
            chassis.move(.5, "forward", 113.02);
            chassis.move(.5, "left", 25.4);
            chassis.move(.5, "backward", 113.02);
            chassis.move(.5, "forward", 113.02);
            chassis.move(.5, "left", 20.32);
            chassis.move(.5, "backward", 113.02);
        }
    }
}
