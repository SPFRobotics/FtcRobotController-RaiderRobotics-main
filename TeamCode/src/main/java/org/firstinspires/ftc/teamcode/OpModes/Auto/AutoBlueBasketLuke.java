package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;

@Autonomous
public class AutoBlueBasketLuke extends LinearOpMode {
    MecanumChassis chassis = new MecanumChassis(this);
    LinearSlide slide = new LinearSlide(this);
    aprilTagDetectionMovement aTag = new aprilTagDetectionMovement(this);
    Extendo extendo = new Extendo(this);

    public void runOpMode(){
        chassis.initializeMovement();
        aTag.initCam2();
        aTag.camOn();
        slide.initSlides();
        extendo.initSlides();
    }

}
