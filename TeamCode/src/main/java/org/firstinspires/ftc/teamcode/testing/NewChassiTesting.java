//This is a class written for testing purposes only to control a new claw system
//This is not to be used for the actual competition
package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Hardware.Button;

@TeleOp(name = "NewChassiTesting")
public class NewChassiTesting extends LinearOpMode{
    private Servo RotationServo = null;
    private Servo WristServo = null;
    private Servo ClawRotationServo = null;
    private Servo ClawServo = null;
    private DcMotor extendo = null;
    private DcMotor MotorYRight = null;
    private DcMotor MotorYLeft = null;
    Button lTrigger = new Button();
    Button lBumper = new Button();


    public void runOpMode() {
        RotationServo = hardwareMap.get(Servo.class, "Servo0");
        WristServo = hardwareMap.get(Servo.class, "Servo1");
        ClawRotationServo = hardwareMap.get(Servo.class, "Servo2");
        ClawServo = hardwareMap.get(Servo.class, "Servo3");
        extendo = hardwareMap.get(DcMotor.class, "Motor0");
        MotorYRight = hardwareMap.get(DcMotor.class, "Motor1");
        MotorYLeft = hardwareMap.get(DcMotor.class, "Motor3");

        WristServo.setDirection(Servo.Direction.REVERSE);
        MotorYRight.setDirection(DcMotorSimple.Direction.REVERSE);


        //Varibles
        double RotationServoPos = 0.75;
        double WristServoPos = 0.74;
        //double ClawRotationServoPos = 0.47;

        waitForStart();
        while (opModeIsActive()){

            //Reset Claw to default pos
            if (gamepad1.b){
                RotationServoPos = 0.507;
                WristServoPos = 0.8207;
                ClawRotationServo.setPosition(0);
                ClawServo.setPosition(0.3);
            }

            //Set claw to efficent position
            if (gamepad1.a){
                RotationServoPos = 0.79;
                WristServoPos = 0.273;
                ClawRotationServo.setPosition(0.65);
            }

            //Extend Extendo
            //extendo.setPower(gamepad2.left_stick_y);
            //MotorYRight.setPower(gamepad2.right_stick_y);
            //MotorYLeft.setPower(gamepad2.right_stick_y);
            //Rotate Arm
            //*************************************************************
            RotationServoPos += gamepad1.left_stick_x*0.002;
            if (RotationServoPos < 0){
                RotationServoPos = 0;
            }
            if (RotationServoPos > 1){
                RotationServoPos = 1;
            }
            RotationServo.setPosition(RotationServoPos);
            //*************************************************************

            //Move Wrist
            //*************************************************************

            WristServoPos += -gamepad1.left_stick_y * 0.002;
            if (WristServoPos < 0){
                WristServoPos = 0;
            }
            if (WristServoPos > 1){
                WristServoPos = 1;
            }
            WristServo.setPosition(WristServoPos);
            //*************************************************************

            //Claw Open Close Logic
            //*************************************************************
            if (lTrigger.press((int)gamepad1.left_trigger)){
                ClawServo.setPosition(0.6);
            }
            else{
                ClawServo.setPosition(0.3);
            }
            //****************************************************************

            //Rotate Claw Logic
            //*************************************
            /*if (lBumper.press(gamepad1.left_bumper)){
                ClawRotationServo.setPosition(0.15);
            }
            else{
                ClawRotationServo.setPosition(0);
            }*/


            //Telemetry
            telemetry.update();
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("Rotation Servo Pos: " + RotationServoPos);
            telemetry.addLine("Wrist Servo Pos: " + WristServoPos);
            telemetry.addLine("Claw Rotation Servo Pos: " + ClawRotationServo.getPosition());
            telemetry.addLine("Claw Servo Pos: " + ClawServo.getPosition());
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");

        }

    }
}
