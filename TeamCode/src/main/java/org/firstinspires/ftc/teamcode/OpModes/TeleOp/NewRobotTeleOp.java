package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.Hardware.Button;


@TeleOp(name="NewRobotTeleOp")
public class NewRobotTeleOp extends LinearOpMode {

    private DcMotor RightFrontMotor = null;
    private DcMotor LeftFrontMotor = null;
    private DcMotor RightBackMotor = null;
    private DcMotor LeftBackMotor = null;
    private Servo RotationServo = null;
    private Servo WristServo = null;
    private Servo ClawRotationServo = null;
    private Servo ClawServo = null;
    private DcMotor extendo = null;
    private DcMotor MotorY = null;


    public void runOpMode() {
        RightFrontMotor = hardwareMap.get(DcMotor.class, "frontRight");
        LeftFrontMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        RightBackMotor = hardwareMap.get(DcMotor.class, "backRight");
        LeftBackMotor = hardwareMap.get(DcMotor.class, "backLeft");
        RotationServo = hardwareMap.get(Servo.class, "Servo0");
        WristServo = hardwareMap.get(Servo.class, "Servo1");
        ClawRotationServo = hardwareMap.get(Servo.class, "Servo2");
        ClawServo = hardwareMap.get(Servo.class, "Servo3");
        extendo = hardwareMap.get(DcMotor.class, "Motor0");
        MotorY = hardwareMap.get(DcMotor.class, "Motor1");
        Button a = new Button();


//Both Left Motors are Set in Reverse
        LeftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;



            if (a.press(gamepad1.a)){

            }else{

            }


        }

    }
}
