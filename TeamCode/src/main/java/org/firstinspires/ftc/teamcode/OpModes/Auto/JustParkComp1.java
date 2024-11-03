package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;
// START WITH ROBOT ON F4 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class JustParkComp1 extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);

    @Override
    public void runOpMode() throws InterruptedException
    {
        chassis.initializeMovement();
        waitForStart();
        chassis.move(.5, "right", 65);
        // 3 Points practically guaranteed
    }
}
