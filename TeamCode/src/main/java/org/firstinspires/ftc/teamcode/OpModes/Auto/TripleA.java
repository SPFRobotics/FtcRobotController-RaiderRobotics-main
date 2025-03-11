/*package org.firstinspires.ftc.teamcode.OpModes.Auto;


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
public class TripleA extends LinearOpMode {

    //Extendo extendo = new Extendo(this);
    Servo wristClawServo = null;
    private DcMotor extendo = null;
    // AprilTagDist AprilTagDistance = new AprilTagDist(this);
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Lift lift = new Lift(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        extendo = hardwareMap.get(DcMotor.class, "extendo");
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        //in order I think

        TrajectoryActionBuilder lmoveToRungs = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(30, -26),0);
        Action lmoveToRungsAction = lmoveToRungs.build();
        TrajectoryActionBuilder lpushSamplesBack = lmoveToRungs.endTrajectory().fresh()
                .waitSeconds(.2)
                .splineToConstantHeading(new Vector2d(52,3),0)
                .splineToConstantHeading(new Vector2d(53,11),0)
                .splineToConstantHeading(new Vector2d(4,11),0);
        TrajectoryActionBuilder lpushSamplesBack2 = lpushSamplesBack.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(53,6),0)
                .splineToConstantHeading(new Vector2d(4,6),0);
        TrajectoryActionBuilder lpushSamplesBack3 = lpushSamplesBack2.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(53,11),0)
                .splineToConstantHeading(new Vector2d(9,11),0);
        TrajectoryActionBuilder ascensionPark = lpushSamplesBack3.endTrajectory().fresh()
                .splineToConstantHeading(new Vector2d(57,-3),0);


        Action lpushSamplesBackAction = lpushSamplesBack.build();
        Action lpushSamplesBack2Action = lpushSamplesBack2.build();
        Action lpushSamplesBack3Action = lpushSamplesBack3.build();
        Action ascensionParkAction = ascensionPark.build();
        // Necessary Actions:
        Action lmoveLiftTop = lift.moveUp(13.8);
        Action lmoveLiftBottom = lift.moveDown(0);
        Action lmoveLiftPlace = lift.moveDown(12);

        Action placeSpec = new SequentialAction(
                //outtake.lowerSpec(),
                lmoveLiftPlace,
                outtake.openClaw()
        );

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                lmoveToRungsAction,
                                lmoveLiftTop
                        ),
                        placeSpec,
                        new ParallelAction(
                                lpushSamplesBackAction,
                                lpushSamplesBack2Action,
                                lpushSamplesBack3Action,
                                ascensionParkAction
                        )
                )
        );




    }
}*/

