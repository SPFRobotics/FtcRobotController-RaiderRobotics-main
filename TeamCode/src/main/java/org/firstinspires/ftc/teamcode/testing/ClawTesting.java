//This is a class written for testing purposes only to control a new claw system
//This is not to be used for the actual competition
package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TeleOp(name = "ClawTesting")
public class ClawTesting extends LinearOpMode{
    //A method that makes toggling a mode easier to accomplish without making 50 new variables and 100 if statements just to toggle something on and off
    //Accepts a boolean value and uses that expression to set a mode on or off
    private boolean buttonToggle(boolean button){
        boolean toggleMode = false;
        boolean stillPressed = false;

        if (!button){
            stillPressed = false;
        }
        if (button && !toggleMode && !stillPressed) {
            toggleMode = true;
            stillPressed = true;
        }
        if (button && toggleMode && !stillPressed){
            toggleMode = false;
            stillPressed = true;
        }
        if (toggleMode){
            return true;
        }
        else{
            return false;
        }
    }
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

            if (buttonToggle(gamepad1.y)){
                WristServoPos = 0;
            }
            else{
                WristServoPos = 0;
            }

            WristServo.setPosition(WristServoPos);

            if (gamepad1.b){
                ClawRotationServo.setPosition(0.25);
            }
            else{
                ClawRotationServo.setPosition(0);
            }

            if (buttonToggle(gamepad1.a)){
                ClawServo.setPosition(0.6);
            }
            else{
                ClawServo.setPosition(0);
            }

            //Telemetry
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("Rotation Servo Pos: " + RotationServo.getPosition());
            telemetry.addLine("Wrist Servo Pos: " + WristServo.getPosition());
            telemetry.addLine("Claw Rotation Servo Pos: " + ClawRotationServo.getPosition());
            telemetry.addLine("Claw Servo Pos: " + ClawServo.getPosition());
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");

        }

    }
}
