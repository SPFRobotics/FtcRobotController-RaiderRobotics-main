package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;

import com.qualcomm.robotcore.hardware.IMU;

@Autonomous(name = "PushBot Autonomous", group = "Autonomous")
public class PushBot extends LinearOpMode {

    private MecanumChassis chassis;
    private LinearSlide lift;
    private Claw claw;
    private IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        chassis = new MecanumChassis(this);
        lift = new LinearSlide(this);
        claw = new Claw(this);
        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();
        chassis.initializeMovement();
        claw.init();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        if (opModeIsActive()) {


            chassis.move(0.5, "forward", 6);


            // Rotate 90 degrees
            imu.resetYaw();
            chassis.rotate(90, 0.5);

            // Move backward 6 inches
            chassis.move(0.5, "backward", 6);

            // Test linear slide movement
            lift.slide(30, 0.5);
            sleep(500);
            lift.slide(-30, 0.5);

            // Open claw
            claw.open();

            // Display final yaw angle
            YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
            telemetry.addData("Final Yaw", angles.getYaw(AngleUnit.DEGREES));
            telemetry.addData("Status", "Autonomous Complete");
            telemetry.update();
        }
    }
}


