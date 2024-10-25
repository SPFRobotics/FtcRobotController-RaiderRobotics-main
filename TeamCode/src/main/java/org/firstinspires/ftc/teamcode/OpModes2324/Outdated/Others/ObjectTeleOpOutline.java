/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

import java.util.Arrays;

@TeleOp
public class ObjectTeleOpOutline extends LinearOpMode {
    private int iterationsPressed1 = 0;
    private int iterationsPressed2 = 0;
    private Gamepad currentGamepad1;
    private Gamepad previousGamepad1;
    private Gamepad currentGamepad2;
    private Gamepad previousGamepad2;
    @Override
    public void runOpMode() throws InterruptedException {

        if (isStopRequested()) return;

        /** when code starts these 4 lines assign an empty virtual gamepad (otherwise know as a controller) to each **/
/*        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();
        currentGamepad2 = new Gamepad();
        previousGamepad2 = new Gamepad();

        while (opModeIsActive()) {
            previousGamepad1.copy(currentGamepad1); /** copies the previous loop's gamepad1 state **/
  /*          currentGamepad1.copy(gamepad1); /** copies the current gamepad1 state **/
    /*      previousGamepad2.copy(currentGamepad2); /** copies the previous loop's gamepad2 state **/
 /*    currentGamepad2.copy(gamepad2); /** copies the current gamepad2 state **/


  /*          Intake();

            telemetry.update();
        }
    }
    public void robotOriented(double xRobot, double yRobot, double rxRobot){
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(yRobot) + Math.abs(xRobot) + Math.abs(rxRobot), 1);
        double frontLeftPower = (yRobot + xRobot + rxRobot) / denominator;
        double backLeftPower = (yRobot - xRobot + rxRobot) / denominator;
        double frontRightPower = (yRobot - xRobot - rxRobot) / denominator;
        double backRightPower = (yRobot + xRobot - rxRobot) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }
    private void Intake() {
        //If the cross button is pressed,
        //
    }
}*/