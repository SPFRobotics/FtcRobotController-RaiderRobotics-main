package org.firstinspires.ftc.teamcode.OpModes.Auto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Robot.AprilTagDist;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Claw;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Lift;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.TankDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.tuning.TuningOpModes;

// START WITH ROBOT ON A3 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class AutoObsZoneLuke extends LinearOpMode {

    Extendo extendo = new Extendo(this);
    Servo wristClawServo = null;
    // AprilTagDist AprilTagDistance = new AprilTagDist(this);
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Lift lift = new Lift(hardwareMap);
        Claw clawRef = new Claw(hardwareMap);

        wristClawServo = hardwareMap.get(Servo .class, "intakeWrist");
        extendo.initSlides();
        wristClawServo.setPosition(1);
        waitForStart();
        Action moveToRungs = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(69.01/2.54, 30/2.54))
                .build();
        Action moveToCorner = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(0, -36))
                .build();
        Action moveLiftTop = lift.moveLift(22);
        Action moveLiftPlace = lift.moveLift(18.5);
        Action moveLiftBottom = lift.moveLift(0);
        Action openClaw = clawRef.openClaw();
        Action closeClaw = clawRef.closeClaw();
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                moveToRungs,
                                moveLiftTop
                        ),
                        moveLiftPlace,
                        openClaw,
                        new ParallelAction(
                                moveToCorner,
                                moveLiftBottom
                        )
                )
        );
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
