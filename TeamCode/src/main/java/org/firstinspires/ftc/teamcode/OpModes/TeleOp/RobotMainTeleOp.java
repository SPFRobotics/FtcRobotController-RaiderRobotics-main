//This is a class written for testing purposes only to control a new claw system
//This is not to be used for the actual competition
package org.firstinspires.ftc.teamcode.OpModes.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Hardware.Button;

@TeleOp(name = "RobotMainTeleOp")
public class RobotMainTeleOp extends LinearOpMode{
    private Servo FRotationServo = null;
    private Servo FWristServo = null;
    private Servo BWristServo = null;
    private Servo FClawRotationServo = null;
    private Servo FClawServo = null;
    private Servo BRClawServo = null;
    private Servo BLClawServo = null;
    private DcMotor extendo = null;
    private DcMotor MotorYRight = null;
    private DcMotor MotorYLeft = null;
    private DcMotor FRMotor = null;
    private DcMotor FLMotor = null;
    private DcMotor BRMotor = null;
    private DcMotor BLMotor = null;
    Button lTrigger = new Button();
    Button rTrigger = new Button();
    Button lBumper = new Button();


    public void runOpMode() {
        //Configured looking from the FRONT of the robot
        /*Hardware mapping is done based on the ports each hardware device is plugged into. The name of the hardware device is followed by the number port it is plugged into.
          If there are multiple expansion hubs the first digit represents the hub the device is plugged into. Ex: Servo10 means it is plugged into port 0 on the expansion hub.
          Ex: Motor1 means it is plugged into port 1 on the control hub.
         */

        //Servos
        FRotationServo = hardwareMap.get(Servo.class, "Servo1");
        FWristServo = hardwareMap.get(Servo.class, "Servo2");
        FClawRotationServo = hardwareMap.get(Servo.class, "Servo3");
        FClawServo = hardwareMap.get(Servo.class, "Servo4");
        BRClawServo = hardwareMap.get(Servo.class, "Servo12");
        BLClawServo = hardwareMap.get(Servo.class, "Servo11");
        BWristServo = hardwareMap.get(Servo.class, "Servo10");

        //Motors
        extendo = hardwareMap.get(DcMotor.class, "Motor12");
        MotorYRight = hardwareMap.get(DcMotor.class, "Motor1");
        MotorYLeft = hardwareMap.get(DcMotor.class, "Motor11");
        FRMotor = hardwareMap.get(DcMotor.class, "Motor0");
        FLMotor = hardwareMap.get(DcMotor.class, "Motor10");
        BRMotor = hardwareMap.get(DcMotor.class, "Motor3");
        BLMotor = hardwareMap.get(DcMotor.class, "Motor13");



        FWristServo.setDirection(Servo.Direction.REVERSE);
        MotorYRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BLClawServo.setDirection(Servo.Direction.REVERSE);
        extendo.setDirection(DcMotorSimple.Direction.REVERSE);
        BRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        MotorYRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorYLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Varibles
        double FRotationServoPos = 0.5024;
        double FWristServoPos = 0.5;

        double FClawRotationServoPos = 0;
        double BWristPos = 0;
        FClawServo.setPosition(0.3);


        waitForStart();
        while (opModeIsActive()){
            //Drive Train
            double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
            double x = gamepad1.left_stick_x * -1.1;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            FLMotor.setPower(y + x + rx);
            BLMotor.setPower(y - x + rx);
            FRMotor.setPower(y - x - rx);
            BRMotor.setPower(y + x - rx);

            //Reset Claw to default pos
            if (gamepad2.b){
                FRotationServoPos = 0.5221;
                FWristServoPos = 0.70526;
                FClawRotationServoPos = 0;
                BWristPos = 0.1131;
            }

            //Set claw to efficent position
            if (gamepad2.a){
                FRotationServoPos = 0.79;
                FWristServoPos = 0.273;
                FClawRotationServoPos = 0.65;
                BWristPos = 0.48435;
            }

            //Set claw to wall position
            if (gamepad2.y){
                FRotationServoPos = 0.79;
                FWristServoPos = 0.273;
                FClawRotationServoPos = 0.65;
                BWristPos = 0.48435;
            }

            //Extend Linear Slides

            if (gamepad1.right_bumper){
                extendo.setPower(1);
            }
            else if (gamepad1.left_bumper){
                extendo.setPower(-1);
            }
            else {
                extendo.setPower(0);
            }
            MotorYRight.setPower(gamepad2.right_stick_y);
            MotorYLeft.setPower(gamepad2.right_stick_y);
            //Rotate Arm
            //*************************************************************
            FRotationServoPos += gamepad2.left_stick_x*0.002;
            if (FRotationServoPos < 0){
                FRotationServoPos = 0;
            }
            if (FRotationServoPos > 1){
                FRotationServoPos = 1;
            }
            FRotationServo.setPosition(FRotationServoPos);

            //*************************************************************

            //Back wrist logic
            //*************************************************************
            //BWristPos += gamepad1.right_stick_y*0.002;
            BWristServo.setPosition(BWristPos);
            //*************************************************************

            //Move Wrist
            //*************************************************************

            FWristServoPos += -gamepad2.left_stick_y * 0.002;
            if (FWristServoPos < 0){
                FWristServoPos = 0;
            }
            if (FWristServoPos > 1){
                FWristServoPos = 1;
            }
            FWristServo.setPosition(FWristServoPos);
            //*************************************************************

            //Claw Open Close Logic
            //*************************************************************
            if (lTrigger.press((int)gamepad2.left_trigger)){
                FClawServo.setPosition(0.6);
            }
            else{
                FClawServo.setPosition(0.3);
            }

            if (rTrigger.press((int)gamepad2.right_trigger)){
                BRClawServo.setPosition(0.5);
                BLClawServo.setPosition(0.5);
            }
            else{
                BRClawServo.setPosition(0.28);
                BLClawServo.setPosition(0.28);
            }
            //****************************************************************



            FClawRotationServo.setPosition(FClawRotationServoPos);

            //Telemetry
            telemetry.update();
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("Rotation Servo Pos: " + FRotationServoPos);
            telemetry.addLine("Wrist Servo Pos: " + FWristServoPos);
            telemetry.addLine("Back Wrist Servo Pos: " + BWristPos);
            telemetry.addLine("Claw Rotation Servo Pos: " + FClawRotationServo.getPosition());
            telemetry.addLine("Claw Servo Pos: " + FClawServo.getPosition());
            telemetry.addLine("Vertical Slides: " + MotorYRight.getCurrentPosition());
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");

        }

    }
}
