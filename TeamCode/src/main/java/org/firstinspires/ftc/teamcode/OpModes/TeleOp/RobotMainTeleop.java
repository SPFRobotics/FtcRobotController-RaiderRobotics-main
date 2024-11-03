package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Odometry;

import java.text.DecimalFormat;

@TeleOp(name="RobotMainTeleOp")
public class RobotMainTeleop extends LinearOpMode {
    Odometry odometry = new Odometry(this);
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightFrontMotor = null;
    private DcMotor leftFrontMotor = null;
    private DcMotor rightBackMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor craneMotorY = null;
    private DcMotor extendoX = null;
    //The wrist of the robot moves the call in the up and down direction.
    private Servo wristClawServo = null;
    private Servo rightClawServo = null;
    private Servo leftClawServo = null;
    private Servo topRightClaw = null;
    private Servo topLeftClaw = null;



    public void runOpMode() {

        //Auto uses the classes Claw, Extendo, LinearSlide, MecanumChassis

        //Format Telemetry (Not in use)
        DecimalFormat df = new DecimalFormat("#.000");

        //CHECK PORTS!!!!!!!!!!
        //Configured from looking IN FRONT OF THE ROBOT!!!

        //Motors
        rightFrontMotor = hardwareMap.get(DcMotor.class, "Motor1");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "Motor0");
        rightBackMotor = hardwareMap.get(DcMotor.class, "Motor3");
        leftBackMotor = hardwareMap.get(DcMotor.class, "Motor2");
        craneMotorY = hardwareMap.get(DcMotor.class, "Motor10");
        extendoX = hardwareMap.get(DcMotor.class, "Motor11");
        //Servos
        wristClawServo = hardwareMap.get(Servo.class, "Servo2");
        rightClawServo = hardwareMap.get(Servo.class, "Servo1");
        leftClawServo = hardwareMap.get(Servo.class, "Servo0");
        topRightClaw = hardwareMap.get(Servo.class, "Servo3");
        topLeftClaw = hardwareMap.get(Servo.class, "Servo4");


        //Motors to the right looking from BEHIND the robot must be reversed because the motors mirror each other.
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightClawServo.setDirection(Servo.Direction.REVERSE);
        topLeftClaw.setDirection(Servo.Direction.REVERSE);

        //Set brake mode
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        craneMotorY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendoX.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        craneMotorY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendoX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        craneMotorY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendoX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //Defines servo position
        //w stands or write while r and l are for left and right
        double wClawPos = 0;
        double rClawPos = 0;
        double lClawPos = 0;

        //Initialize all servos to 0
        topRightClaw.setPosition(0.12);
        topLeftClaw.setPosition(0.12);
        rightClawServo.setPosition(0);
        leftClawServo.setPosition(0);
        wristClawServo.setPosition(0);


        //Crane position
        craneMotorY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double craneMotorYPos = 0;

        //Extendo Position
        extendoX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double extendoXPos = 0;

        telemetry.setAutoClear(true);

        waitForStart();
        while (opModeIsActive()) {
            //Variables for wheels
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * -1.1;
            double rx = gamepad1.right_stick_x;

            //Speed Control
            if (!gamepad1.a) {
                y /= 2;
                x /= 2;
                rx /= 2;
            }

            //Math for Mecanum drive
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            rightFrontMotor.setPower((y - x - rx) / denominator);
            leftFrontMotor.setPower((y + x + rx) / denominator);
            rightBackMotor.setPower((y + x - rx) / denominator);
            leftBackMotor.setPower((y - x + rx) / denominator);

            //Crane Control
            //Old controls using the thumbstick value.
            //craneMotorY.setPower(gamepad2.right_stick_y);

            //-3300*/

            //Old code for the extendo uses encoders
            /*if (gamepad1.dpad_up){
                extendoXPos += -15;
            }
            else if(gamepad1.dpad_down){
                extendoXPos += 15;
            }
            extendoX.setTargetPosition((int)extendoXPos);
            extendoX.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extendoX.setPower(0.5);*/

            //Using the d-pad to set the power of the motors
            if (gamepad1.right_bumper){
                extendoX.setPower(-1);
            }
            else if (gamepad1.left_bumper){
                extendoX.setPower(1);
            }
            else{
                extendoX.setPower(0);
            }

            //For Vertical slide
            if (gamepad2.dpad_up && craneMotorYPos > -3300){
                craneMotorYPos -= 5;
            }
            else if(gamepad2.dpad_down && craneMotorYPos < 0){
                craneMotorYPos += 5;
            }
            craneMotorY.setTargetPosition((int)craneMotorYPos);
            craneMotorY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            craneMotorY.setPower(1);


            //Claw Wrist Control
            //Uses the a and b buttons to control the claw but needs to be changed to right and left bumpers.
            if (gamepad2.right_bumper && wClawPos < 1){
                wClawPos += 0.01;
            }
            if (gamepad2.left_bumper && wClawPos > 0){
                wClawPos -= 0.01;
            }
            wristClawServo.setPosition(wClawPos);


            //The trigger controls the claw as of now but needs to be changed to buttons for ease of usage.
            if (gamepad2.a){
                rightClawServo.setPosition(0);
                leftClawServo.setPosition(0);
            }
            else if(gamepad2.b){
                rightClawServo.setPosition(0.2);
                leftClawServo.setPosition(0.2);
            }

            if (gamepad2.y){
                topRightClaw.setPosition(0);
                topLeftClaw.setPosition(0);
            }
            else if(gamepad2.x){
                topRightClaw.setPosition(0.2);
                topLeftClaw.setPosition(0.2);
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
            telemetry.addLine("BR Motor PWR: " + rightBackMotor.getPower());
            telemetry.addLine("BL Motor PWR: " + leftBackMotor.getPower());
            telemetry.addLine("Vertical Slide: " + craneMotorY.getPower());
            telemetry.addLine("Extendo: " + extendoX.getPower() + "\n");
            telemetry.addLine("Slide Pos:");
            telemetry.addLine("Vertical Slide: " + craneMotorYPos);
            telemetry.addLine("Extendo: " + extendoXPos);
            //Servo positions and motor positions coming soon!!!!
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
        }

    }
}

