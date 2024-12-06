package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import com.qualcomm.robotcore.hardware.IMU;

@Autonomous
public class PushBot extends LinearOpMode {
    public void runOpMode() throws InterruptedException{
        MecanumChassis chassis = new MecanumChassis(this);
        LinearSlide slide = new LinearSlide(this);
        Claw claw = new Claw(this);
        IMU imu = null;


        chassis.initializeMovement();
        slide.initSlides();
        claw.init();
        imu = hardwareMap.get(IMU.class, "imu");
        waitForStart();

        chassis.move(.5, "forward", 6.62);
        chassis.rotate(90, .5);
        chassis.move(.3, "left", 11.24);
        slide.slide(30, .5);
        chassis.move(.5, "forward", 48.52);
        claw.open();
        chassis.move(.5, "left", 1.20);
        chassis.move(.5, "backward", 43.52);
        slide.slide(-30, .5);
        chassis.move(.5, "right", 122.02);
        chassis.move(.5, "forward", 22.94);
        chassis.move(.5, "left", 120.02);
        chassis.move(.5, "right", 120.02);
        chassis.move(.5, "forward", 20.4);
        chassis.move(.5, "left", 112.02);
        chassis.move(.5, "right", 120.02);
        chassis.move(.5, "forward", 20.62);
        chassis.move(.5, "left", 100.02);


        telemetry.addLine("Yaw: " + imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));






    }
}
