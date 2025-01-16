package org.firstinspires.ftc.teamcode.Hardware.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class NewClaw {
    private LinearOpMode opmode;
    private Servo ClawOpen = null;
    private Servo ClawRotate = null;

    // State variables to track claw and rotation positions
    private boolean clawOpen = true; // Start with the claw in the open position
    private boolean clawRotated = false; // Tracks whether the claw is rotated

    public NewClaw(LinearOpMode opmode) {
        this.opmode = opmode;
    }

    public void init(HardwareMap hardwareMap) {
        opmode.telemetry.addData("Status", "NewClaw is initializing...");
        opmode.telemetry.update();

        // Initialize servos
        ClawOpen = hardwareMap.get(Servo.class, "ClawOpen");
        ClawRotate = hardwareMap.get(Servo.class, "ClawRotate");

        if (ClawOpen != null && ClawRotate != null) {
            // Set initial positions
            ClawOpen.setPosition(1.0); // Start with the claw open
            ClawRotate.setPosition(0.0); // Start with the claw in the default rotation
            opmode.telemetry.addData("Status", "NewClaw initialized successfully");
            opmode.telemetry.addData("Claw", "Opened at initialization");
        } else {
            opmode.telemetry.addData("Error", "One or more servos are not found!");
        }
        opmode.telemetry.update();
    }

    // Method to handle controls
    public void controlWithGamepad() {
        // Claw open/close controlled by gamepad x button
        if (opmode.gamepad1.x) {
            if (!clawOpen) {
                ClawOpen.setPosition(1.0); // Fully open
                clawOpen = true;
                opmode.telemetry.addData("Claw", "Opened");
            }

            if (opmode.gamepad1.y)
                ClawOpen.setPosition(0.0); // Fully closed
            clawOpen = false;
            opmode.telemetry.addData("Claw", "Closed");
        }
        opmode.telemetry.update();

        // Debounce to prevent rapid toggling
        opmode.sleep(150);
    }


    // Claw rotation controlled by gamepad right bumper
    {
        if (opmode.gamepad1.right_bumper) {
            if (!clawRotated) {
                ClawRotate.setPosition(0.25); // Rotate to position 1
                clawRotated = true;
                opmode.telemetry.addData("Claw Rotate", "Rotated to Position 1");
            } else {
                ClawRotate.setPosition(0.0); // Rotate back to position 0
                clawRotated = false;
                opmode.telemetry.addData("Claw Rotate", "Rotated to Position 0");
            }
            opmode.telemetry.update();

            // Debounce to prevent rapid toggling
            opmode.sleep(150);
        }
    }
}




