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
    //A static method that makes toggling a mode easy

    private boolean ButtonToggle(Method button){
        boolean toggleMode = false;
        boolean stillPressed = false;
        try{
            if ((boolean) button.invoke(gamepad1) && !toggleMode && !stillPressed){
                toggleMode = true;
            }
            if (toggleMode){
                return true;
            }
            else{
                return false;
            }
        }
        catch (IllegalAccessException e){

        }
        catch (IllegalArgumentException e){

        }
        catch (Exception e){

        }
        return false;
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
