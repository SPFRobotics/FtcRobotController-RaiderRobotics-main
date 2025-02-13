package org.firstinspires.ftc.teamcode.OpModes.Auto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Intake;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Outtake;

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
        //Lift lift = new Lift(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        waitForStart();
        // Movements:

        TrajectoryActionBuilder moveToRungs = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(30, 11.81));
        TrajectoryActionBuilder pushSamplesBack = moveToRungs.endTrajectory().fresh()
                .lineToX(25)
                .strafeTo(new Vector2d(24, -18))
                .strafeTo(new Vector2d(34,-21))
                .lineToY(-29)
                .strafeTo(new Vector2d(5, -30));
        TrajectoryActionBuilder pushMoreSamplesBack = pushSamplesBack.endTrajectory().fresh()
                .strafeTo(new Vector2d(42,-31))
                .strafeTo(new Vector2d(48,-40))
                .strafeTo(new Vector2d(5, -41));
        Action moveToRungsAction = moveToRungs.build();
        Action moveBackToPlace = moveToRungs.endTrajectory().fresh().lineToX(28).waitSeconds(0.25).build();
        Action pushSamplesBackAction = pushSamplesBack.build();
        Action pushMoreSamplesBackAction  = pushMoreSamplesBack.build();
        // Necessary Actions:
        /*Action moveLiftTop = lift.moveUp(15);
        Action moveLiftBottom = lift.moveDown(0);*/
        Action placeSpec = new SequentialAction(
                outtake.lowerSpec(),
                moveBackToPlace,
                outtake.openClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.1).build()
        );
        Action prepareIntake = new ParallelAction(intake.prepareIntake(), outtake.prepareIntake());
        Action completeTransfer = new SequentialAction(
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.25).build(),
                intake.prepareTransfer(),
                drive.actionBuilder(beginPose).waitSeconds(0.7).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.2).build(),
                intake.openClaw());
        Actions.runBlocking(
                new SequentialAction(
                        /*new ParallelAction(
                                moveToRungsAction
                                //moveLiftTop
                        ),
                        placeSpec,

                        new ParallelAction(
                                //moveLiftBottom,
                                pushSamplesBackAction
                        ),*/
                        new ParallelAction(
                                //pushMoreSamplesBackAction,
                                prepareIntake
                        ),
                        completeTransfer
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
