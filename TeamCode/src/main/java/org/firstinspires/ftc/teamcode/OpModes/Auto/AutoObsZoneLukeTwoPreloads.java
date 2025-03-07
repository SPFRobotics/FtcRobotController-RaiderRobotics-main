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

import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Intake;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Lift;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Outtake;

// START WITH ROBOT ON A3 WITH RIGHT WHEELS ON COORDINATE LINE
@Autonomous
public class AutoObsZoneLukeTwoPreloads extends LinearOpMode {

    //Extendo extendo = new Extendo(this);
    private DcMotor extendo = null;
    Servo wristClawServo = null;
    // AprilTagDist AprilTagDistance = new AprilTagDist(this);
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Lift lift = new Lift(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        extendo = hardwareMap.get(DcMotor.class, "extendo");
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        // Movements (in order of execution):

        TrajectoryActionBuilder moveToRungs = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(30, 18),0);
        Action moveToRungsAction = moveToRungs.build();
        TrajectoryActionBuilder pushSamplesBack = moveToRungs.endTrajectory().fresh()
                .lineToX(28)
                .splineToConstantHeading(new Vector2d(29,-20),0)
                .splineToConstantHeading(new Vector2d(54,-26),0)
                .strafeTo(new Vector2d(8, -30));
        TrajectoryActionBuilder pushSamplesBack2 = pushSamplesBack.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(28,-28),0)
                .splineToConstantHeading(new Vector2d(48,-40),0)
                .strafeTo(new Vector2d(1.5, -41));
        TrajectoryActionBuilder moveToRungs2 = pushSamplesBack2.endTrajectory().fresh()
                .strafeTo(new Vector2d(30.3, 14));
        Action moveToRungs2Action = moveToRungs2.build();
        TrajectoryActionBuilder moveToCorner = moveToRungs2.endTrajectory().fresh()
                .waitSeconds(.2)
                .strafeTo(new Vector2d(1.5,-22));
        Action moveToCornerAction = moveToCorner.build();
        TrajectoryActionBuilder moveToRungs3 = moveToCorner.endTrajectory().fresh()
                .strafeTo(new Vector2d(30.3, 10));
        Action moveToRungs3Action = moveToRungs3.build();

        TrajectoryActionBuilder moveToCorner2 = moveToRungs3.endTrajectory().fresh()
                .strafeTo(new Vector2d(1.5,-22));
        Action moveToCorner2Action = moveToCorner2.build();
        TrajectoryActionBuilder moveToRungs4 = moveToCorner2.endTrajectory().fresh()
                .strafeTo(new Vector2d(30.3,6));
        Action moveToRungs4Action = moveToRungs4.build();
        TrajectoryActionBuilder moveToCorner3 = moveToRungs4.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(8,-45),0);
        Action moveToCorner3Action = moveToCorner3.build();

        Action pushSamplesBackAction = pushSamplesBack.build();
        Action pushsamplesback2Action  = pushSamplesBack2.build();
        // Necessary Actions:
        Action moveLiftTop = lift.moveUp(13.8);
        Action moveLiftPlace =lift.moveDown(12);
        Action moveLiftBottom = lift.moveDown(0);

        Action placeSpec = new SequentialAction(
                outtake.lowerSpec(),
                moveLiftPlace,
                drive.actionBuilder(beginPose).waitSeconds(0.03).build(),
                outtake.openClaw()
        );
        Action placeSpec2 = new SequentialAction(
                outtake.lowerSpec(),
                moveLiftPlace,
                drive.actionBuilder(beginPose).waitSeconds(0.03).build(),
                outtake.openClaw()
        );
        Action placeSpec3 = new SequentialAction(
                outtake.lowerSpec(),
                moveLiftPlace,
                drive.actionBuilder(beginPose).waitSeconds(0.03).build(),
                outtake.openClaw()
        );
        Action placeSpec4 = new SequentialAction(
                outtake.lowerSpec(),
                moveLiftPlace,
                drive.actionBuilder(beginPose).waitSeconds(0.03).build(),
                outtake.openClaw()
        );

        Action prepareIntake = new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.2).build(), new ParallelAction(outtake.prepareIntake(), intake.prepareIntake()));
        Action prepareIntake2 = new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.2).build(), new ParallelAction(outtake.prepareIntake(), intake.prepareIntake()));
        Action prepareIntake3 = new SequentialAction(drive.actionBuilder(beginPose).waitSeconds(0.2).build(), new ParallelAction(outtake.prepareIntake(), intake.prepareIntake()));
        Action completeTransfer = new SequentialAction(
                intake.closeClaw(),
                drive.actionBuilder(beginPose).waitSeconds(.5).build(),
                intake.prepareTransfer(),
                drive.actionBuilder(beginPose).waitSeconds(.7).build(),
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
                drive.actionBuilder(beginPose).waitSeconds(.7).build(),
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
                                outtake.prepareOuttake(),
                                pushSamplesBackAction
                        ),
                        new ParallelAction(
                                pushsamplesback2Action,
                                prepareIntake
                        ),

                        new ParallelAction(moveToRungs2Action, new SequentialAction(completeTransfer, moveLiftTop)),
                        placeSpec2,
                        new ParallelAction(moveLiftBottom, moveToCornerAction,  prepareIntake2),

                        new ParallelAction(moveToRungs3Action, new SequentialAction(completeTransfer2, moveLiftTop)),
                        placeSpec3,
                        new ParallelAction(moveToCorner2Action, moveLiftBottom, prepareIntake3),

                        new ParallelAction(moveToRungs4Action, new SequentialAction(completeTransfer3, moveLiftTop)),
                        placeSpec4,
                        new ParallelAction(moveLiftBottom, moveToCorner3Action)

                )
        );
    }
}
