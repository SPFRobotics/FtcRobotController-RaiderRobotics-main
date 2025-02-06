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
    private Servo FRotationServo = null;
    private Servo FWristServo = null;
    private Servo BWristServo = null;
    private Servo FClawFRotationServo = null;
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
    Button lBumper = new Button();


    public void runOpMode() {
        //Configured looking from the FRONT of the robot
        /*Hardware mapping is done based on the ports each hardware device is plugged into. The name of the hardware device is followed by the number port it is plugged into.
          If there are multiple expansion hubs the first digit represents the hub the device is plugged into. Ex: Servo10 means it is plugged into port 0 on the expansion hub.
          Ex: Motor1 means it is plugged into port 1 on the control hub.
         */

        //Servos
        FRotationServo = hardwareMap.get(Servo.class, "Servo?");
        FWristServo = hardwareMap.get(Servo.class, "Servo?");
        FClawFRotationServo = hardwareMap.get(Servo.class, "Servo?");
        FClawServo = hardwareMap.get(Servo.class, "Servo?");
        BRClawServo = hardwareMap.get(Servo.class, "Servo?");
        BLClawServo = hardwareMap.get(Servo.class, "Servo?");
        BWristServo = hardwareMap.get(Servo.class, "Servo?");

        //Motors
        extendo = hardwareMap.get(DcMotor.class, "Motor11");
        MotorYRight = hardwareMap.get(DcMotor.class, "Motor1");
        MotorYLeft = hardwareMap.get(DcMotor.class, "Motor12");
        FRMotor = hardwareMap.get(DcMotor.class, "Motor0");
        FLMotor = hardwareMap.get(DcMotor.class, "Motor10");
        BRMotor = hardwareMap.get(DcMotor.class, "Motor3");
        BLMotor = hardwareMap.get(DcMotor.class, "Motor13");



        FWristServo.setDirection(Servo.Direction.REVERSE);
        MotorYRight.setDirection(DcMotorSimple.Direction.REVERSE);


        //Varibles
        double FRotationServoPos = 0.75;
        double FWristServoPos = 0.74;
        //double FClawFRotationServoPos = 0.47;

        waitForStart();
        while (opModeIsActive()){

            //Reset Claw to default pos
            if (gamepad1.b){
                FRotationServoPos = 0.507;
                FWristServoPos = 0.8207;
                FClawFRotationServo.setPosition(0);
                FClawServo.setPosition(0.3);
            }

            //Set claw to efficent position
            if (gamepad1.a){
                FRotationServoPos = 0.79;
                FWristServoPos = 0.273;
                FClawFRotationServo.setPosition(0.65);
            }

            //Extend Extendo
            extendo.setPower(gamepad2.left_stick_y);
            MotorYRight.setPower(gamepad2.right_stick_y);
            MotorYLeft.setPower(gamepad2.right_stick_y);
            //Rotate Arm
            //*************************************************************
            FRotationServoPos += gamepad1.left_stick_x*0.002;
            if (FRotationServoPos < 0){
                FRotationServoPos = 0;
            }
            if (FRotationServoPos > 1){
                FRotationServoPos = 1;
            }
            FRotationServo.setPosition(FRotationServoPos);
            //*************************************************************

            //Move Wrist
            //*************************************************************

            FWristServoPos += -gamepad1.left_stick_y * 0.002;
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
            if (lTrigger.press((int)gamepad1.left_trigger)){
                FClawServo.setPosition(0.6);
            }
            else{
                FClawServo.setPosition(0.3);
            }
            //****************************************************************

            //Telemetry
            telemetry.update();
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");
            telemetry.addLine("Rotation Servo Pos: " + FRotationServoPos);
            telemetry.addLine("Wrist Servo Pos: " + FWristServoPos);
            telemetry.addLine("Claw Rotation Servo Pos: " + FClawFRotationServo.getPosition());
            telemetry.addLine("Claw Servo Pos: " + FClawServo.getPosition());
            telemetry.addLine("==========================================");
            telemetry.addLine(String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)) + String.valueOf((int)(Math.random() * 2)));
            telemetry.addLine("==========================================");

        }

    }
}
