package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Odometry;

import java.text.DecimalFormat;

@TeleOp(name="SwerveTestTeleOp")
public class Swervetest extends LinearOpMode {

    Odometry odometry = new Odometry(this);
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightFrontMotor = null;
    private DcMotor leftFrontMotor = null;

    //The wrist of the robot moves the call in the up and down direction.
    private Servo wristClawServo = null;
    private Servo rightClawServo = null;

    private IMU imu = null;



    public void runOpMode() {

        //Auto uses the classes Claw, Extendo, LinearSlide, MecanumChassis

        //Format Telemetry (Not in use)
        DecimalFormat df = new DecimalFormat("#.000");

        //CHECK PORTS!!!!!!!!!!
        //Configured from looking IN FRONT OF THE ROBOT!!!

        //Motors
        rightFrontMotor = hardwareMap.get(DcMotor.class, "Motor1");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "Motor0");

        //Servos
        wristClawServo = hardwareMap.get(Servo.class, "Servo2");
        rightClawServo = hardwareMap.get(Servo.class, "Servo1");


        //IMU
        imu = hardwareMap.get(IMU.class, "imu");


        //Motors to the right looking from BEHIND the robot must be reversed because the motors mirror each other.
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightClawServo.setDirection(Servo.Direction.REVERSE);


        //Set brake mode
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);






        //Defines servo position
        //w stands or write while r and l are for left and right
        double wClawPos = 0;
        double rClawPos = 0;
        double lClawPos = 0;

        //Initialize all servos to 0
        rightClawServo.setPosition(0);
        wristClawServo.setPosition(0);


        //Crane position
        double craneMotorYPos = 0;

        //Extendo Position
        double extendoXPos = 0;

        //Boolean conditions
        boolean isStillPressed = false;
        boolean fieldOri = false;

        telemetry.setAutoClear(true);

        waitForStart();
        while (opModeIsActive()) {
            //Variables for wheels
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * -1.1;
            double rx = gamepad1.right_stick_x;

            // Get's the robots start foward facing position
            double fowardDef = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            //Change between Robot Oriented and Field Oriented Drive using 1 button
            if (gamepad1.touchpad && !isStillPressed && !fieldOri) {
                gamepad1.rumble(500);
                fieldOri = true;
                isStillPressed = true;
            }

            if (gamepad1.touchpad && !isStillPressed && fieldOri){
                gamepad1.rumble(500);
                fieldOri = false;
                isStillPressed = true;
            }

            if (!gamepad1.touchpad && isStillPressed){
                isStillPressed = false;
            }

            if (fieldOri){
                x = x * Math.cos(fowardDef) - y * Math.sin(fowardDef);
                y = x * Math.sin(fowardDef) + y * Math.cos(fowardDef);
            }

            //Robot Speed Control Using the right_trigger
            if (gamepad1.right_trigger != 0) {
                y /= 2;
                x /= 2;
                rx /= 2;
            }

            //Math for Mecanum drive
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            rightFrontMotor.setPower((y - x - rx) / denominator);
            leftFrontMotor.setPower((y + x + rx) / denominator);


            //Using the d-pad to set the power of the motors



            //For Vertical slide



            //Claw Wrist Control
            if (gamepad2.right_bumper && wClawPos < 1 && wClawPos < 0.52){
                wClawPos += 0.01/2;
            }
            if (gamepad2.left_bumper && wClawPos > 0){
                wClawPos -= 0.01/2;
            }
            wristClawServo.setPosition(wClawPos);
            //Limit: 0.52


            //POTENTIAL NEW CODE that makes closing and opening the claw for both the top and bottom claws 1 button
            //Intake


            //testing swerve movement
            if (gamepad1.x){
                rightFrontMotor.setPower(1);
            }
            else {
                rightFrontMotor.setPower(0);
            }

            if (gamepad1.y){

            }

            //TELEMETRY
            //ALL NAMES CONFIGURED LOOKING AT THE FRONT OF THE ROBOT
            telemetry.update();
            //telemetry.addLine("Telemetry Provided By Mr.Spaceman ;)");
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("MOTOR PWR:");
            telemetry.addLine("FR Motor PWR: " + rightFrontMotor.getPower());
            telemetry.addLine("FL Motor PWR: " + leftFrontMotor.getPower());
            telemetry.addLine("Vertical Slide: " + craneMotorYPos);
            telemetry.addLine("Extendo: " + extendoXPos + "\n");
            telemetry.addLine("Pitch: " + imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES));
            telemetry.addLine("Roll: " + imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES));
            telemetry.addLine("Yaw: " + imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));


            //Driver Mode Logic
            if (fieldOri) {
                telemetry.addLine("Driver Mode: Field Oriented");
            }
            else{
                telemetry.addLine("Driver Mode: Robot Oriented");
            }
            //Servo positions and motor positions coming soon!!!!
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
        }

    }
}
