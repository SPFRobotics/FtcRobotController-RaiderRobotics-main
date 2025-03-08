package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunnerStuff.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunnerStuff.Outtake;
@Autonomous
public class OuttakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException
    {
        Pose2d beginPose = new Pose2d(new Vector2d(0,0),0);
        Outtake outtakeRef = new Outtake(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        Actions.runBlocking(
                new SequentialAction(
                        outtakeRef.openClaw(),
                        drive.actionBuilder(beginPose).waitSeconds(5).build(),
                        outtakeRef.closeClaw()
                )
        );
    }
}
