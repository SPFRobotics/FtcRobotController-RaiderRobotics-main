package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ClawTesting")
public class ClawTesting extends LinearOpMode{
    private Servo RotationServo = null;
    private Servo WristServo = null;
    private Servo ClawRotationServo = null;
    private Servo ClawServo = null;

    public void runOpMode() {
        RotationServo = hardwareMap.get(Servo.class, "Motor0");
        WristServo = hardwareMap.get(Servo.class, "Motor1");
        ClawRotationServo = hardwareMap.get(Servo.class, "Motor2");
        ClawServo = hardwareMap.get(Servo.class, "Motor3");

        //Varibles
        double RoationServoPos = 0;
        double WristServoPos = 0.5;
        double ClawRotationServoPos = 0;

        //Boolean Expressions
        boolean toggleClaw = false;
        boolean stillPressed = false;

        ClawServo.setPosition(0.4);

        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.left_stick_x < 0 && RoationServoPos < 1){
                RoationServoPos += 0.001;
            }
            if (gamepad1.left_stick_x > 0 && RoationServoPos > 0){
                RoationServoPos -= 0.001;
            }
            RotationServo.setPosition(RoationServoPos);

            if (gamepad1.left_bumper && WristServoPos < 1){
                WristServoPos += 0.001;
            }
            if (gamepad1.right_bumper && WristServoPos > 0){
                WristServoPos -= 0.001;
            }
            WristServo.setPosition(WristServoPos);

            if (gamepad1.b){
                ClawRotationServo.setPosition(0.25);
            }
            else{
                ClawRotationServo.setPosition(0);
            }

            if (!gamepad1.a){
                stillPressed = false;
            }
            if (gamepad1.a && !stillPressed && !toggleClaw){
                toggleClaw = true;
                stillPressed = true;
            }
            if (toggleClaw){
                ClawServo.setPosition(0.6);
            }
            else{
                ClawServo.setPosition(0);
            }
            if (gamepad1.a && !stillPressed && toggleClaw){
                toggleClaw = false;
                stillPressed = true;
            }

        }

    }
}
