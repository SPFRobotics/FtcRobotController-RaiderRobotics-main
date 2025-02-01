package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot.NewClaw;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Extendo;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
//import org.firstinspires.ftc.teamcode.Hardware.Robot.VirtualForebar;


@Autonomous(name = "Auto Extendo Sample", group = "Sample")
public class AutoExtendoSample extends LinearOpMode {

    MecanumChassis chassis = new MecanumChassis(this);
    LinearSlide slide = new LinearSlide(this);
    Extendo extendo = new Extendo(this);
    NewClaw claw = new NewClaw(this);
    private Servo leftArm = null;
    private Servo rightArm = null;
    private final double LEFT_ARM_OPEN_POS = 0;
    private final double RIGHT_ARM_OPEN_POS = 0;
    private final double LEFT_ARM_CLOSED_POS = .12;
    private final double RIGHT_ARM_CLOSED_POS = .12;

    // Initialize servos
    public void initialize() {
        leftArm = hardwareMap.servo.get("Servo4");
        rightArm = hardwareMap.servo.get("Servo3");
        leftArm.setDirection(Servo.Direction.REVERSE);
        leftArm.setPosition(0.05);
        rightArm.setPosition(0.05);
    }

    public void drop(){

    }

    public void close() {
        leftArm.setPosition(LEFT_ARM_CLOSED_POS);
        rightArm.setPosition(RIGHT_ARM_CLOSED_POS);
    }

    public void open() {
        leftArm.setPosition(LEFT_ARM_OPEN_POS);
        rightArm.setPosition(RIGHT_ARM_OPEN_POS);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize hardware
        chassis.initializeMovement();
        slide.initSlides();
        extendo.initSlides();
        //claw.init();
        initialize();  // Initialize the servo positions

        // Wait for the start button
        waitForStart();

        // Move chassis forward
        chassis.move(.5,"left",30); // should move towards submersible
        chassis.move(.5, "forward", 69.01);

        // Move Extendo forward
        extendo.slide(19, 1);

        // Get the distance sensor
        DistanceSensor DS = hardwareMap.get(DistanceSensor.class, "distanceSensor");  // Initialize the distance sensor


        // Check if the distance is less than 10 cm
        if (DS.getDistance(DistanceUnit.CM) < 10) {
            extendo.slide(0, 0);  // Stop Extendo if too close
            close();  // Close the claw when too close
        } else {
            extendo.slide(1, 1);  // Extend if distance is sufficient
        }

        // Display the distance on the driver station
        telemetry.addData("Distance: ", DS.getDistance(DistanceUnit.CM));
        telemetry.update();

        // Sleep for 500 milliseconds to keep the program running for a short while
        sleep(500);
    }
}

