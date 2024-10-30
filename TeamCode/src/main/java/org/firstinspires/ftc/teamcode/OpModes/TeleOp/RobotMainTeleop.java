package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Odometry;

import java.text.DecimalFormat;

@TeleOp(name="RobotMain")
public class RobotMainTeleop extends LinearOpMode {
    Odometry odometry = new Odometry(this);
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightFrontMotor = null;
    private DcMotor leftFrontMotor = null;
    private DcMotor rightBackMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor craneMotorY = null;
    private DcMotor extendoX = null;
    private Servo wristClawServo = null;
    private Servo rightClawServo = null;
    private Servo leftClawServo = null;



    public void runOpMode() {
        //Format Telemetry
        DecimalFormat df = new DecimalFormat("#.000");

        //CHECK PORTS!!!!!!!!!!
        //Configured from looking IN FRONT OF THE ROBOT!!!
        rightFrontMotor = hardwareMap.get(DcMotor.class, "Motor1");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "Motor0");
        rightBackMotor = hardwareMap.get(DcMotor.class, "Motor3");
        leftBackMotor = hardwareMap.get(DcMotor.class, "Motor2");
        craneMotorY = hardwareMap.get(DcMotor.class, "Motor5");
        extendoX = hardwareMap.get(DcMotor.class, "Motor4");
        wristClawServo = hardwareMap.get(Servo.class, "Servo0");
        rightClawServo = hardwareMap.get(Servo.class, "Servo1");
        leftClawServo = hardwareMap.get(Servo.class, "Servo2");

        //Motors to the right looking from BEHIND the robot must be reversed because the motors mirror each other.
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightClawServo.setDirection(Servo.Direction.REVERSE);

        //Defines servo position
        //w stands or write while r and l are for left and right
        double wClawPos = wristClawServo.getPosition();
        double rClawPos = rightClawServo.getPosition();
        double lClawPos = leftClawServo.getPosition();

        waitForStart();

        telemetry.setAutoClear(true);

        while (opModeIsActive()) {
            //Variables for wheels
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            //Speed Control
            if (!gamepad1.right_bumper) {
                y /= 2;
                x /= 2;
                rx /= 2;
            }

            //Math for Mecanum drive
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            rightFrontMotor.setPower((y - x - rx) / denominator);
            leftFrontMotor.setPower((y + x + rx) / denominator);
            rightBackMotor.setPower((y + x - rx) / denominator);
            leftBackMotor.setPower((y - x + rx) / denominator);

            //Crane Control
            craneMotorY.setPower(gamepad2.right_stick_y);
            extendoX.setPower(gamepad2.left_stick_y);

            //Claw Control
            if (gamepad2.a && wClawPos < 1){
                wClawPos += 0.01;
            }
            if (gamepad2.b && wClawPos > 0){
                wClawPos -= 0.01;
            }
            wristClawServo.setPosition(wClawPos);

            //TELEMETRY
        }          

    }
}

