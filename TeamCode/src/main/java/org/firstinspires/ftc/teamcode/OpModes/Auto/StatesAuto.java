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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Intake;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Lift;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.NewExtendo;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Outtake;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.SamplePusher;

@Autonomous
public class StatesAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Lift lift = new Lift(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        SamplePusher samplePusher = new SamplePusher(hardwareMap);
        //NewExtendo extendo = new NewExtendo(hardwareMap);

        waitForStart();
        // Movements (in order of execution):
        TrajectoryActionBuilder moveToChamber1 = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(36.5, 19));
        TrajectoryActionBuilder pushSamplesBack1 = moveToChamber1.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(20, -5),0)
                .splineToConstantHeading(new Vector2d(40, -24),0);
        TrajectoryActionBuilder pushSamplesBack2 = pushSamplesBack1.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(12, -22),0)
                .turn(-Math.PI/4).turn(Math.PI/4);
        TrajectoryActionBuilder pushSamplesBack3 = pushSamplesBack2.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(40, -32),0);
        TrajectoryActionBuilder pushSamplesBack4 = pushSamplesBack3.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(12, -32),0)
                .turn(-Math.PI/4).turn(Math.PI/4);
        TrajectoryActionBuilder pushSamplesBack5 = pushSamplesBack4.endTrajectory().fresh()
                .setReversed(false).splineTo(new Vector2d(40, -40),0);
        TrajectoryActionBuilder pushSamplesBack6 = pushSamplesBack5.endTrajectory().fresh()
                .setReversed(true).splineTo(new Vector2d(2, -40),Math.PI);

        TrajectoryActionBuilder moveToChamber2 = pushSamplesBack4.endTrajectory().fresh()
                .strafeTo(new Vector2d(37, 15));
        TrajectoryActionBuilder moveToCorner1 = moveToChamber2.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(2, -24),0);

        TrajectoryActionBuilder moveToChamber3 = moveToCorner1.endTrajectory().fresh()
                .strafeTo(new Vector2d(36, 11));
        TrajectoryActionBuilder moveToCorner2 = moveToChamber3.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(2, -24),0);

        TrajectoryActionBuilder moveToChamber4 = moveToCorner2.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(36, 9),0);
        TrajectoryActionBuilder moveToCorner3 = moveToChamber4.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(2, -24),0);

        TrajectoryActionBuilder moveToChamber5 = moveToCorner3.endTrajectory().fresh()
                .setReversed(false).splineTo(new Vector2d(36, 7),0);
        TrajectoryActionBuilder moveToCorner4 = moveToChamber5.endTrajectory().fresh()
                .setReversed(true).splineTo(new Vector2d(2, -35),Math.PI);
        // RoadRunner Actions are weird. If the same action is ran twice, RoadRunner thinks "Oh I already ran this" and doesn't run
        // Make multiple identical actions instead
        Action placeSpec1 = new SequentialAction(
                new ParallelAction(
                        lift.moveUp(7),
                        moveToChamber1.build()),
                lift.moveUp(13.5),
                outtake.openClaw()
        );
        Action placeSpec2 = new SequentialAction(
                new ParallelAction(new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(),outtake.preparePlacement()),moveToChamber2.build()),

                lift.moveUp(12.5),
                outtake.openClaw()
        );
        Action placeSpec3 = new SequentialAction(
                new ParallelAction(new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(),outtake.preparePlacement()),moveToChamber3.build()),

                lift.moveUp(12.5),
                outtake.openClaw()
        );
        Action placeSpec4 = new SequentialAction(
                new ParallelAction(new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(),outtake.preparePlacement()),moveToChamber4.build()),

                lift.moveUp(12.5),
                outtake.openClaw()
        );
        Action placeSpec5 = new SequentialAction(
                new ParallelAction(new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(),outtake.preparePlacement()),moveToChamber5.build()),
                lift.moveUp(12.5),
                outtake.openClaw()
        );
        Action completeTransfer1 = new SequentialAction(
                outtake.prepareTransfer(),
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.transfer(),
                drive.actionBuilder(beginPose).waitSeconds(0.5).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.openClaw(),
                lift.moveUp(8));


        Action completeTransfer2 = new SequentialAction(
                outtake.prepareTransfer(),
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.transfer(),
                drive.actionBuilder(beginPose).waitSeconds(0.5).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.openClaw(),
                lift.moveUp(8));
        Action completeTransfer3 = new SequentialAction(
                outtake.prepareTransfer(),
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.transfer(),
                drive.actionBuilder(beginPose).waitSeconds(0.5).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.openClaw(),
                lift.moveUp(8));
        Action completeTransfer4 = new SequentialAction(
                intake.prepareIntake(), // Intake Wrists
                outtake.openClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                outtake.prepareTransfer(), // Swing Outtake down to transfer
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.transfer(), // Swing intake to transfer position
                drive.actionBuilder(beginPose).waitSeconds(0.5).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(0.25).build(),
                intake.openClaw(),
                lift.moveUp(8));
        Action pushSamplesBackAction = new SequentialAction(
                pushSamplesBack1.build(),
                samplePusher.lowerArm(),
                pushSamplesBack2.build(),
                samplePusher.raiseArm(),
                pushSamplesBack3.build(),
                samplePusher.lowerArm(),
                pushSamplesBack4.build(),
                samplePusher.raiseArm(),
                pushSamplesBack5.build(),
                samplePusher.lowerArm(),
                pushSamplesBack6.build(),
                samplePusher.raiseArm()
        );
        Action transferAndPlace1 = new SequentialAction(
                new ParallelAction(
                        completeTransfer1,
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(1).build(),placeSpec2)
                ),
                new ParallelAction(
                        moveToCorner1.build(),
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(), lift.moveDown(0),
                                intake.prepareIntake(),
                                intake.openClaw(),
                                outtake.openClaw())
                )
        );
        Action transferAndPlace2 = new SequentialAction(
                new ParallelAction(
                        completeTransfer2,
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(1).build(),placeSpec3)
                ),
                new ParallelAction(
                        moveToCorner2.build(),
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(),lift.moveDown(0))
                )
        );
        Action transferAndPlace3 = new SequentialAction(
                new ParallelAction(
                        completeTransfer3,
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(1).build(),placeSpec4)
                ),
                new ParallelAction(
                        moveToCorner3.build(),
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(), lift.moveDown(0))
                )
        );
        Action transferAndPlace4 = new SequentialAction(
                new ParallelAction(
                        completeTransfer4,
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(1).build(),placeSpec5)
                ),
                new ParallelAction(
                        moveToCorner4.build(),
                        new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.5).build(), lift.moveDown(0))
                )
        );



        // Necessary Actions:
        /*
        Action placeSpec = new SequentialAction(
                outtake.lowerSpec(),
                moveLiftPlace,
                outtake.openClaw()
        );*/


        Actions.runBlocking(
                new SequentialAction(
                        placeSpec1,
                        new ParallelAction(
                            pushSamplesBackAction,
                            new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(1).build(), lift.moveDown(0))
                        )//,
                        //transferAndPlace1
                )
        );
    }
}
