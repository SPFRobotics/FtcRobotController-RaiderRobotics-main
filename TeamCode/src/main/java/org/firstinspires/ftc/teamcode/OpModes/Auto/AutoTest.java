package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="autoTest")
public class AutoTest extends LinearOpMode {
    private DcMotor frontRightMotor = null;
    private DcMotor frontLeftMotor = null;
    private DcMotor backRightMotor = null;
    private DcMotor backLeftMotor = null;

    private DistanceSensor dSensor = null;
    double motorPower = 0.2;
    int targetPos = 752;
    @Override
    public void runOpMode() throws InterruptedException{
        //Configured from looking in front the of the robot
        frontRightMotor = hardwareMap.get(DcMotor.class, "Motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "Motor1");
        backRightMotor = hardwareMap.get(DcMotor.class, "Motor2");
        backLeftMotor = hardwareMap.get(DcMotor.class, "Motor3");
        dSensor = hardwareMap.get(DistanceSensor.class, "Distance0");

        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()) {
            frontRightMotor.setTargetPosition(targetPos);
            frontLeftMotor.setTargetPosition(targetPos);
            backRightMotor.setTargetPosition(targetPos);
            backLeftMotor.setTargetPosition(targetPos);

            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            frontRightMotor.setPower(motorPower);
            frontLeftMotor.setPower(motorPower);
            backRightMotor.setPower(motorPower);
            backLeftMotor.setPower(motorPower);

            if (dSensor.getDistance(DistanceUnit.CM) <= 50){
                motorPower = 0;
            }

            if (frontRightMotor.getCurrentPosition() == targetPos){
                targetPos = 0;
            }

            telemetry.update();
            telemetry.addLine("Distance: " + dSensor.getDistance(DistanceUnit.CM));

        }


    }
}
