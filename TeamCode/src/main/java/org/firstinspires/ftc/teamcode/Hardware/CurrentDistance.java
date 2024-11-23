package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Distance OpMode", group = "Sensor")
public class CurrentDistance extends OpMode {

    private DistanceSensor distanceSensor; // Declare the distance sensor
    private DcMotor extendoMotor;         // Declare the motor

    @Override
    public void init() {
        // Initialize hardware
        distanceSensor = hardwareMap.get(DistanceSensor.class, "DistanceSensor1");
        extendoMotor = hardwareMap.get(DcMotor.class, "ExtendoMotor");

        // Ensure motor starts at rest
        extendoMotor.setPower(0);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Measure the distance
        double distanceCm = distanceSensor.getDistance(DistanceUnit.CM);

        // Display distance on the telemetry
        telemetry.addData("Distance (cm)", String.format("%.01f cm", distanceCm));

        // Control motor based on distance
        if (distanceCm < 10) {
            extendoMotor.setPower(0); // Stop the motor
        } else {
            extendoMotor.setPower(1); // Extend the motor
        }

        telemetry.update();
    }
}
