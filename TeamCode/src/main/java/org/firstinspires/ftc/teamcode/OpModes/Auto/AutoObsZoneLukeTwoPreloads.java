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
        Pose2d beginPose = new Pose2d(-.75, 0, 0);
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
        TrajectoryActionBuilder moveBackToPlace = moveToRungs.endTrajectory().fresh().lineToX(28);
        Action moveBackToPlaceAction = moveBackToPlace.build();
        TrajectoryActionBuilder pushSamplesBack = moveBackToPlace.endTrajectory().fresh()
                .waitSeconds(.2)
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
        TrajectoryActionBuilder moveBackToPlace2 = moveToRungs2.endTrajectory().fresh().lineToX(28);
        Action moveBackToPlace2Action = moveBackToPlace2.build();
        TrajectoryActionBuilder moveToCorner = moveBackToPlace2.endTrajectory().fresh()
                .waitSeconds(.2)
                .strafeTo(new Vector2d(1.5,-22));
        Action moveToCornerAction = moveToCorner.build();
        TrajectoryActionBuilder moveToRungs3 = moveToCorner.endTrajectory().fresh()
                .strafeTo(new Vector2d(30.3, 10));
        Action moveToRungs3Action = moveToRungs3.build();
        TrajectoryActionBuilder moveBackToPlace3 = moveToRungs3.endTrajectory().fresh().lineToX(28);
        Action moveBackToPlace3Action = moveBackToPlace3.build();
        TrajectoryActionBuilder moveToCorner2 = moveBackToPlace3.endTrajectory().fresh()
                .strafeTo(new Vector2d(1.5,-22));
        Action moveToCorner2Action = moveToCorner2.build();
        TrajectoryActionBuilder moveToRungs4 = moveToCorner2.endTrajectory().fresh()
                .strafeTo(new Vector2d(30.3,6));
        Action moveToRungs4Action = moveToRungs4.build();
        TrajectoryActionBuilder moveBackToPlace4 = moveToRungs4.endTrajectory().fresh().lineToX(28);
        Action moveBackToPlace4Action = moveBackToPlace4.build();
        TrajectoryActionBuilder moveToCorner3 = moveBackToPlace4.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(8,-45),0);
        Action moveToCorner3Action = moveToCorner3.build();

        Action pushSamplesBackAction = pushSamplesBack.build();
        Action pushsamplesback2Action  = pushSamplesBack2.build();
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
        Action placeSpec2 = new SequentialAction(
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

        Action prepareIntake =  new ParallelAction(drive.actionBuilder(beginPose).waitSeconds(0.35).build(), intake.prepareIntake(), outtake.prepareIntake());
        Action prepareIntake2 = new ParallelAction(drive.actionBuilder(beginPose).waitSeconds(0.35).build(), intake.prepareIntake(), outtake.prepareIntake());
        Action prepareIntake3 = new ParallelAction(drive.actionBuilder(beginPose).waitSeconds(0.35).build(), intake.prepareIntake(), outtake.prepareIntake());
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
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                moveToRungsAction,
                                moveLiftTop
                        ),
                        placeSpec,

                        new ParallelAction(
                                moveLiftBottom,
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
//        /* PLACES 2 WITHOUT ROADRUNNER
//        //telemetry.addData("X", AprilTagDistance.getDistX());
//        //telemetry.addData("Y", AprilTagDistance.getDistY());
//        chassis.move(.5,"left",30); // should move towards submersible
//        chassis.moveMultitask(.5, "forward", 69.01,24, 1); // should move towards submersible
//        //telemetry.addData("X", AprilTagDistance.getDistX());
//        //telemetry.addData("Y", AprilTagDistance.getDistY());
//        slide.slide(18.5,1); // lowers lift (specimen should attach by now)
//        claw.open(); // let go of specimen
//        // next 2 move commands move towards parking zone
//
//        chassis.move(.5, "backward", 50);
//        chassis.moveMultitask(.5, "right", 86, 0,1);
//
//
//        chassis.rotate(-180, 0.5);
//        chassis.move(.5, "forward", 19.01);
//        claw.close();
//        slide.slide(5,1);
//        chassis.move(.5, "backward", 19.01);
//        chassis.rotate(-180, 0.5);
//        chassis.moveMultitask(.5, "left", 80,24, 1);
//        chassis.move(.5, "forward", 55.5);
//        slide.slide(18.5, 1);
//        claw.open();
//        chassis.move(.5, "backward", 60);
//        chassis.moveMultitask(.5, "right", 130, 0, 1);
//        */

    }
}
