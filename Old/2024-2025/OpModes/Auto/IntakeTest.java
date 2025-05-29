package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Intake;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Outtake;
@Autonomous
public class IntakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(new Vector2d(0,0),0);
        Intake intake = new Intake(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        Actions.runBlocking(
                new SequentialAction(
                        intake.openClaw(),
                        drive.actionBuilder(beginPose).waitSeconds(0.5).build(),
                        intake.closeClaw(),
                        drive.actionBuilder(beginPose).waitSeconds(0.5).build(),
                        intake.prepareIntake(),
                        drive.actionBuilder(beginPose).waitSeconds(3).build(),
                        intake.transfer(),
                        drive.actionBuilder(beginPose).waitSeconds(3).build()
                )
        );
    }
}
