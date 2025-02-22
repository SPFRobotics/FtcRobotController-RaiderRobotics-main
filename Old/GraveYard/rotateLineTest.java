/* package org.firstinspires.ftc.teamcode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Disabled
/*
 * This is an example of a more complex path to really test the tuning.

@Autonomous
@Config
public class rotateLineTest extends LinearOpMode {
    public static double X = 0;
    public static double Y = 0;
    public static double ROTATE = 90;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        Trajectory traj = drive.trajectoryBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(new Vector2d(Y,-X),Math.toRadians(ROTATE)))
                .build();

        drive.followTrajectory(traj);

        //sleep(2000);

        /*drive.followTrajectory(
                drive.trajectoryBuilder(traj.end(), true)
                        .lineTo(new Vector2d(0, 0).rotated(180))
                        .build()
        );
    }
}*/
