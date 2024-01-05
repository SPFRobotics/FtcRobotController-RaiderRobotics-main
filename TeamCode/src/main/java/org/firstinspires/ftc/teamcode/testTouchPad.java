package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class testTouchPad extends LinearOpMode {
    private Gamepad currentGamepad1;
    private Gamepad previousGamepad1;
    private Gamepad currentGamepad2;
    private Gamepad previousGamepad2;
    double fingerStartX = 0;
    double fingerStartY = 0;
    boolean fingerTouching = false;
    double changeInX = 1;
    boolean launchRequested = false;

    @Override
    public void runOpMode() throws InterruptedException {
        fingerTouching = false;
        currentGamepad1 = new Gamepad();
        previousGamepad1 = new Gamepad();
        currentGamepad2 = new Gamepad();
        previousGamepad2 = new Gamepad();

        waitForStart();

        while (opModeIsActive()) {
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            telemetry.addData("touchpad X: ", currentGamepad2.touchpad_finger_1_x);
            telemetry.addData("touchpad Y: ", currentGamepad2.touchpad_finger_1_y);
            telemetry.addData("launch Requested: ",launchRequested);
            telemetry.update();

            if (previousGamepad2.touchpad_finger_1 && !currentGamepad2.touchpad_finger_1) {
                fingerTouching = false;
            }
            if (!previousGamepad2.touchpad_finger_1 && currentGamepad2.touchpad_finger_1) {
                fingerStartX = currentGamepad2.touchpad_finger_1_x;
                fingerTouching = true;
            }
            if (fingerTouching) {
                if (currentGamepad2.touchpad_finger_1_x > (fingerStartX + changeInX)) {
                    launchRequested = true;
                }
            }
        }
    }
}
