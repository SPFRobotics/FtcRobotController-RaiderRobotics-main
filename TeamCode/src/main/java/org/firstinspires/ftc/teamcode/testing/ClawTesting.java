//This is a class written for testing purposes only to control a new claw system
//This is not to be used for the actual competition
package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TeleOp(name = "ClawTesting")
public class ClawTesting extends LinearOpMode{

    //Boolean
    /*public void buttonToggle(boolean button, boolean toggleMode, boolean stillPressed){
        if (!button){
            stillPressed = false;
        }
        if (button && !stillPressed && !toggleMode){
            toggleMode = true;
            stillPressed = true;
        }
        method = toggleMode;
        if (button && !stillPressed && toggleMode){
            stillPressed = true;
            toggleMode = false;
        }
    }*/

    //A method that makes toggling a mode easier to accomplish without making 50 new variables and 100 if statements just to toggle something on and off
    //Accepts a boolean value and uses that expression to set a mode on or off
    private Servo RotationServo = null;
    private Servo WristServo = null;
    private Servo ClawRotationServo = null;
    private Servo ClawServo = null;
    private DcMotor extendo = null;
    private DcMotor MotorY = null;

    public void runOpMode() {
        RotationServo = hardwareMap.get(Servo.class, "Servo0");
        WristServo = hardwareMap.get(Servo.class, "Servo1");
        ClawRotationServo = hardwareMap.get(Servo.class, "Servo2");
        ClawServo = hardwareMap.get(Servo.class, "Servo3");
        extendo = hardwareMap.get(DcMotor.class, "Motor0");
        MotorY = hardwareMap.get(DcMotor.class, "Motor1");


        //Varibles
        double RoationServoPos = 0.5276;
        double WristServoPos = 0.5;
        //double ClawRotationServoPos = 0.47;

        //Boolean Expressions
        boolean toggleMode = false;
        boolean stillPressed = false;
        boolean toggleMode1 = false;
        boolean stillPressed1 = false;

        waitForStart();
        while (opModeIsActive()){

            //Reset Claw to default pos
            if (gamepad1.b){
                RoationServoPos = 0.5276;
                WristServoPos = 0.5;
                ClawRotationServo.setPosition(0.47);
                ClawServo.setPosition(0.3);
            }

            //Set claw to efficent position
            if (gamepad1.a){
                RoationServoPos = 0.79;
                WristServoPos = 0.273;
            }

            //Extend Extendo
            extendo.setPower(gamepad2.left_stick_y);
            MotorY.setPower(gamepad2.right_stick_y);

            //Rotate Arm
            //*************************************************************
            RoationServoPos += gamepad1.left_stick_x*0.001;
            RotationServo.setPosition(RoationServoPos);
            //*************************************************************

            //Move Wrist
            //*************************************************************
            WristServoPos += gamepad1.right_stick_y*0.001;
            WristServo.setPosition(WristServoPos);
            //*************************************************************

            //Claw Open Close Logic
            //*************************************************************
            if (gamepad1.left_trigger != 1){
                stillPressed = false;
            }
            if (gamepad1.left_trigger == 1 && !stillPressed && !toggleMode){
                toggleMode = true;
                stillPressed = true;
            }
            if (toggleMode){
                ClawServo.setPosition(0.6);
            }
            else{
                ClawServo.setPosition(0.3);
            }
            if (gamepad1.left_trigger == 1 && !stillPressed && toggleMode){
                stillPressed = true;
                toggleMode = false;
            }
            //****************************************************************

            //Rotate Claw Logic
            //*************************************8
            if (!gamepad1.left_bumper){
                stillPressed1 = false;
            }
            if (gamepad1.left_bumper && !stillPressed1 && !toggleMode1){
                toggleMode1 = true;
                stillPressed1 = true;
            }
            if (toggleMode1){
                ClawRotationServo.setPosition(0.15);
            }
            else{
                ClawRotationServo.setPosition(0.47);
            }
            if (gamepad1.left_bumper && !stillPressed1 && toggleMode1){
                stillPressed1 = true;
                toggleMode1 = false;
            }

            //Telemetry
            telemetry.update();
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
