package org.firstinspires.ftc.teamcode.GraveYard;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Robot.Odometry;

import java.text.DecimalFormat;

@Disabled

@TeleOp(name="RobotMainTeleOpSingle")
public class RobotMainTeleopSingle extends LinearOpMode {
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
    private IMU imu = null;



    public void runOpMode() {

        //Auto uses the classes Claw, Extendo, LinearSlide, MecanumChassis

        //Format Telemetry (Not in use)
        DecimalFormat df = new DecimalFormat("#.000");

        //CHECK PORTS!!!!!!!!!!
        //Configured from looking IN FRONT OF THE ROBOT!!!

        //Motors
        rightFrontMotor = hardwareMap.get(DcMotor.class, "frontRight");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        rightBackMotor = hardwareMap.get(DcMotor.class, "backRight");
        leftBackMotor = hardwareMap.get(DcMotor.class, "backLeft");
        craneMotorY = hardwareMap.get(DcMotor.class, "lift");
        craneMotorY.setDirection(DcMotorSimple.Direction.REVERSE);
        extendoX = hardwareMap.get(DcMotor.class, "extendo");
        //Servos
        wristClawServo = hardwareMap.get(Servo.class, "intakeWrist");
        rightClawServo = hardwareMap.get(Servo.class, "intakeRightClaw");
        leftClawServo = hardwareMap.get(Servo.class, "intakeLeftClaw");
        topRightClaw = hardwareMap.get(Servo.class, "outtakeRightClaw");
        topLeftClaw = hardwareMap.get(Servo.class, "outtakeLeftClaw");


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

        //craneMotorY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //extendoX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //Defines servo position
        //w stands or write while r and l are for left and right
        double wClawPos = 0.7;
        double rClawPos = 0.18;
        double lClawPos = 0.18;

        //Initialize all servos to 0
        //topRightClaw.setPosition(0.0);
        //topLeftClaw.setPosition(0.0);
        rightClawServo.setPosition(rClawPos);
        leftClawServo.setPosition(lClawPos);
        wristClawServo.setPosition(wClawPos);


        //Crane position
        LinearSlide verticalSlide = new LinearSlide(this);
        craneMotorY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double craneMotorYPos = 0;

        //Extendo Position
        extendoX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double extendoXPos = 0;

        //Boolean conditions
        boolean isStillPressed1 = false;
        boolean isStillPressed2 = false;
        boolean fieldOri = false;
        boolean automatedPlacement = false;

        telemetry.setAutoClear(true);

        //IMU
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        ));

        double rotX = 0;
        double rotY = 0;

        imu.initialize(parameters);
        imu.resetYaw();

        waitForStart();
        while (opModeIsActive()) {
            //Variables for wheels
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * -1.1;
            double rx = gamepad1.right_stick_x;

            //Robot Speed Control Using the right_trigger
            if (gamepad1.right_trigger != 0) {
                y /= 2;
                x /= 2;
                rx /= 2;
            }


            // Get's the robots start foward facing position
            double fowardDef = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            //Change between Robot Oriented and Field Oriented Drive using 1 button
            if (gamepad1.touchpad && !isStillPressed1 && !fieldOri) {
                gamepad1.rumble(500);
                fieldOri = true;
                isStillPressed1 = true;
            }

            if (gamepad1.touchpad && !isStillPressed1 && fieldOri){
                gamepad1.rumble(500);
                fieldOri = false;
                isStillPressed1 = true;
            }

            if (!gamepad1.touchpad && isStillPressed1){
                isStillPressed1 = false;
            }

            if (fieldOri){
                rotX = x * Math.cos(-fowardDef) - y * Math.sin(-fowardDef);
                rotY = x * Math.sin(-fowardDef) + y * Math.cos(-fowardDef);

                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
                rightFrontMotor.setPower((rotY - rotX - rx) / denominator);
                leftFrontMotor.setPower((rotY + rotX + rx) / denominator);
                rightBackMotor.setPower((rotY + rotX - rx) / denominator);
                leftBackMotor.setPower((rotY - rotX + rx) / denominator);
            }
            else{
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                rightFrontMotor.setPower((y - x - rx) / denominator);
                leftFrontMotor.setPower((y + x + rx) / denominator);
                rightBackMotor.setPower((y + x - rx) / denominator);
                leftBackMotor.setPower((y - x + rx) / denominator);
            }

            //Math for Mecanum drive

            //Extendo will extend to a negative position
            extendoXPos = extendoX.getCurrentPosition();
            if (extendoXPos > -1700 && gamepad1.dpad_right){
                extendoX.setPower(-1);
            }
            else if (extendoXPos < 0 && gamepad1.dpad_left){
                extendoX.setPower(1);
            }
            else{
                extendoX.setPower(0);
            }

            //For Vertical slide
            /*
            if (gamepad2.dpad_up && craneMotorYPos > 3300){
                craneMotorYPos += 50;
            }
            else if(gamepad2.dpad_down && craneMotorYPos > 5){
                craneMotorYPos -= 50;
            }
            if(craneMotorYPos < 5){
                craneMotorYPos = 5;
            }
            else if(craneMotorYPos >3295){
                craneMotorYPos = 3295;
            }
            */

            //Calibrate where 0 is
            if (gamepad1.share){
                craneMotorY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                craneMotorY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if (gamepad1.right_trigger > 0){
                    craneMotorY.setPower(gamepad1.right_trigger);
                }
                if (gamepad1.left_trigger > 0){
                    craneMotorY.setPower(-gamepad1.left_trigger);
                }
            }

            craneMotorYPos = craneMotorY.getCurrentPosition();
            //Automated rising
            //verticalSlide.slideToPosition(35, 1);

            //Using the left-thumbstick on the y-axis to set the power of the motors

            if(craneMotorYPos < 3100 && gamepad1.right_trigger > 0 && !gamepad1.share){
                craneMotorY.setPower(gamepad1.right_trigger);
            }
            else if(craneMotorYPos > 150 && gamepad1.left_trigger > 0 && !gamepad1.share){
                craneMotorY.setPower(-gamepad1.left_trigger);
            }
            else if(!gamepad1.share){
                craneMotorY.setPower(0);
            }

            /*if(craneMotorY.getCurrentPosition() >= 3300&&craneMotorY.getPower()>0){
                craneMotorY.setPower(0);
            }
            if(craneMotorY.getCurrentPosition()<= 0 &&craneMotorY.getPower()<0){
                craneMotorY.setPower(0);
            }/*


            //Limit is: 3300
            /*craneMotorY.setTargetPosition((int)craneMotorYPos);
            craneMotorY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            craneMotorY.setPower(1);*/


            //Claw Wrist Control
            if (gamepad1.right_bumper && wClawPos > (0.03999999) + 0.02){
                wClawPos -= 0.05;
            }
            if (gamepad1.left_bumper && wClawPos < 0.71){
                wClawPos += 0.05;
            }
            wristClawServo.setPosition(wClawPos);
            //Limit: 0.52


            //POTENTIAL NEW CODE that makes closing and opening the claw for both the top and bottom claws 1 button
            //Intake
            if (gamepad1.y){
                rightClawServo.setPosition(0.1);
                leftClawServo.setPosition(0.1);
            }
            else{
                rightClawServo.setPosition(0.18);
                leftClawServo.setPosition(0.18);
            }
            //Outtake
            if (gamepad1.a){
                topRightClaw.setPosition(0.15);
                topLeftClaw.setPosition(0.15);

            }
            else{
                topRightClaw.setPosition(0);
                topLeftClaw.setPosition(0);
            }


            //TELEMETRY
            //ALL NAMES CONFIGURED LOOKING AT THE FRONT OF THE ROBOT
            telemetry.update();
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("MOTOR PWR:");
            telemetry.addLine("FR Motor PWR: " + rightFrontMotor.getPower());
            telemetry.addLine("FL Motor PWR: " + leftFrontMotor.getPower());
            telemetry.addLine("BR Motor PWR: " + rightBackMotor.getPower());
            telemetry.addLine("BL Motor PWR: " + leftBackMotor.getPower());
            telemetry.addLine("Vertical Slide: " + craneMotorY.getPower());
            telemetry.addLine("Wrist Pos: " + wristClawServo.getPosition());
            telemetry.addLine("Extendo: " + extendoX.getPower() + "\n");
            telemetry.addLine("Vertical Slide Pos: " + craneMotorYPos);
            telemetry.addLine("Extendo Pos: " + extendoXPos + "\n");
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

