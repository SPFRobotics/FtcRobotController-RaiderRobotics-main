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

// START WITH ROBOT ON A3 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class StatesAuto extends LinearOpMode {

    // AprilTagDist AprilTagDistance = new AprilTagDist(this);
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Lift lift = new Lift(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        NewExtendo extendo = new NewExtendo(hardwareMap);

        waitForStart();
        // Movements (in order of execution):

        TrajectoryActionBuilder moveToRungs = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(30, 18),0);
        Action moveToRungsAction = moveToRungs.build();
        TrajectoryActionBuilder moveBackToPlace = moveToRungs.endTrajectory().fresh().lineToX(28);
        Action moveBackToPlaceAction = moveBackToPlace.build();
        Action moveToSpikeMarks = moveBackToPlace.endTrajectory().fresh()
                .waitSeconds(.2)
                .splineToConstantHeading(new Vector2d(29,-20),0)
                .turn(-135)
                .build();


        // Necessary Actions:
        Action moveLiftTop = lift.moveUp(13.8);
        Action moveLiftPlace =lift.moveDown(12.6);
        Action moveLiftBottom = lift.moveDown(0);
        Action placeSpec = new SequentialAction(
                outtake.lowerSpec(),
                moveBackToPlaceAction,
                moveLiftPlace,
                outtake.openClaw()
        );
        /*Action placeSpec2 = new SequentialAction(
                outtake.lowerSpec(),
                moveBackToPlace2Action,
                moveLiftPlace,
                outtake.openClaw()
        );
        Action placeSpec3 = new SequentialAction(
                outtake.lowerSpec(),
                moveBackToPlace3Action,
                moveLiftPlace,
                outtake.openClaw()
        );
        Action placeSpec4 = new SequentialAction(
                outtake.lowerSpec(),
                moveBackToPlace4Action,
                moveLiftPlace,
                outtake.openClaw()
        );
        Action placeSpec5 = new SequentialAction(
                outtake.lowerSpec(),
                moveBackToPlace5Action,
                moveLiftPlace,
                outtake.openClaw()
        );*/

        Action prepareIntake =  new SequentialAction(intake.prepareIntake(), outtake.prepareIntake());
        Action prepareIntake2 = new SequentialAction(intake.prepareIntake(), outtake.prepareIntake());
        Action prepareIntake3 = new SequentialAction(intake.prepareIntake(), outtake.prepareIntake());
        Action completeTransfer = new SequentialAction(
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.5).build(),
                intake.prepareTransfer(),
                drive.actionBuilder(beginPose).waitSeconds(7).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.2).build(),
                intake.openClaw());
        Action completeTransfer2 = new SequentialAction(
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.5).build(),
                intake.prepareTransfer(),
                drive.actionBuilder(beginPose).waitSeconds(.7).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.2).build(),
                intake.openClaw());
        Action completeTransfer3 = new SequentialAction(
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.5).build(),
                intake.prepareTransfer(),
                drive.actionBuilder(beginPose).waitSeconds(7).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.2).build(),
                intake.openClaw());
        Action completeTransfer4 = new SequentialAction(
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.5).build(),
                intake.prepareTransfer(),
                drive.actionBuilder(beginPose).waitSeconds(7).build(),
                outtake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.2).build(),
                intake.openClaw());
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                moveToRungsAction,
                                moveLiftTop
                        ),
                        placeSpec,

                        new ParallelAction(
                                moveLiftBottom,
                                moveToSpikeMarks
                        ),
                        new ParallelAction(
                                intake.prepareGroundIntake(),
                                extendo.moveOut(15)
                        )
                )
        );
    }
}
