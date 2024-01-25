package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class launcherTesting extends LinearOpMode {
    private Servo droneLauncher;
    private Servo droneServo;
    private static final double minLauncherPos = 0;
    private static final double maxLauncherPos = 0.5;
    private static final double launchAngle = 0.17;
    private Gamepad currentGamepad1;
    private Gamepad previousGamepad1;
    private Gamepad currentGamepad2;
    private Gamepad previousGamepad2;
    double fingerStartX = 0;
    double fingerStartY = 0;
    boolean fingerTouching = false;
    double changeInY = 1;
    boolean launchRequested = false;
    private ElapsedTime endGameTimer = new ElapsedTime();
    private double timeToEndGame = 120;
    @Override
    public void runOpMode() throws InterruptedException {

        Initialization();

        if (isStopRequested()) return;

        /** when code starts these 4 lines assign an empty virtual gamepad (otherwise know as a controller) to each **/
        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();
        currentGamepad2 = new Gamepad();
        previousGamepad2 = new Gamepad();

        endGameTimer.reset();

        while (opModeIsActive()) {
            //currentIMUAngle = imu.getRobotOrientationAsQuaternion();
            previousGamepad1.copy(currentGamepad1); /** copies the previous loop's gamepad1 state **/
            currentGamepad1.copy(gamepad1); /** copies the current gamepad1 state **/
            previousGamepad2.copy(currentGamepad2); /** copies the previous loop's gamepad2 state **/
            currentGamepad2.copy(gamepad2); /** copies the current gamepad2 state **/
            Drone();
            //telemetry.addData("touchpad X: ", currentGamepad2.touchpad_finger_1_x);
            //telemetry.addData("touchpad Y: ", currentGamepad2.touchpad_finger_1_y);
            telemetry.update();
        }
    }
    private void Initialization() {
        // Declare our motors
        // Make sure your ID's match your configuration
        droneLauncher = hardwareMap.servo.get("droneLauncher"); /** Port: ControlHub ServoPort 3 **/
        droneServo = hardwareMap.servo.get("droneServo"); /** Port: ExpansionHub ServoPort **/

        waitForStart();
    }
    private void Drone() {
        if (endGameTimer.seconds() >= 3) {
            if (gamepad2.left_bumper && gamepad2.right_bumper) {
                if (previousGamepad2.touchpad_finger_1 && !currentGamepad2.touchpad_finger_1) {
                    fingerTouching = false;
                }
                if (!previousGamepad2.touchpad_finger_1 && currentGamepad2.touchpad_finger_1) {
                    fingerStartY = currentGamepad2.touchpad_finger_1_y;
                    fingerTouching = true;
                }
                if (fingerTouching) {
                    if (currentGamepad2.touchpad_finger_1_y > (fingerStartY + changeInY)) {
                        launchRequested = true;
                    }
                }
                if (launchRequested) {
                    telemetry.addData("works","");
                    droneServo.setPosition(launchAngle);
                    sleep(1000);
                    droneLauncher.setPosition(0.48);
                }
            }
        } else {
            droneServo.setPosition(0.33);
            droneLauncher.setPosition(0.42);
        }
        /*if (gamepad2.dpad_up) {
            droneServo.setPosition(launchAngle);
        }*/
        telemetry.addData("launch Requested", launchRequested);
    }
}
