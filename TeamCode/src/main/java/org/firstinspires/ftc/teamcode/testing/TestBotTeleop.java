package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Odometry;

import java.text.DecimalFormat;

@Disabled
@TeleOp(name="RobotTestTeleop")
public class TestBotTeleop extends LinearOpMode {
    Odometry odometry = new Odometry(this);

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightFrontMotor = null;
    private DcMotor leftFrontMotor = null;
    private DcMotor rightBackMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor wintch1 = null;
    //Extendo was a crane that moved on the horizontal axis
    private DcMotor wintch2 = null;


    public void runOpMode() {
        waitForStart();
        //Format Telemetry
        DecimalFormat df = new DecimalFormat("#.000");


        telemetry.setAutoClear(true);
        //CHECK PORTS!!!!!!!!!!
        //Configured from looking BEHIND THE ROBOT!!!
        rightFrontMotor = hardwareMap.get(DcMotor.class, "Motor1");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "Motor0");
        rightBackMotor = hardwareMap.get(DcMotor.class, "Motor3");
        leftBackMotor = hardwareMap.get(DcMotor.class, "Motor2");
        wintch1 = hardwareMap.get(DcMotor.class, "Motor4");
        wintch2 = hardwareMap.get(DcMotor.class, "Motor5");

        //Motors to the right looking from BEHIND the robot must be reversed because the motors mirror each other.
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        wintch2.setDirection(DcMotorSimple.Direction.REVERSE);

        while (opModeIsActive()) {
            //Variables for wheels
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            //Speed Control
            if (!gamepad1.right_bumper) {
                y /= 2;
                x /= 2;
                rx /= 2;
            }

            wintch1.setPower(gamepad1.right_trigger);
            wintch2.setPower(gamepad1.right_trigger);
            wintch1.setPower(-gamepad1.left_trigger);
            wintch2.setPower(-gamepad1.left_trigger);

            //Math for Mecanum drive
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            rightFrontMotor.setPower((y - x - rx) / denominator);
            leftFrontMotor.setPower((y + x + rx) / denominator);
            rightBackMotor.setPower((y + x - rx) / denominator);
            leftBackMotor.setPower((y - x + rx) / denominator);


            //TELEMETRY
            telemetry.addData("X: ", odometry.getX());
            telemetry.addData("Y: ", odometry.getY());
            telemetry.addData("Theta: ", odometry.getHeading());
            telemetry.update();
        }

    }
}

