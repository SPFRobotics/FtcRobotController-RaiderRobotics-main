package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class Chassis {

    private DistanceSensor sensorDistance;
    private DcMotor rightFrontMotor = null;
    private DcMotor leftFrontMotor =null;
    private DcMotor rightBackMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor wintch1 = null;
    private DcMotor wintch2 = null;
    private LinearOpMode opMode;

    public void init(LinearOpMode opMode) {
        this.opMode = opMode;
        sensorDistance = opMode.hardwareMap.get(DistanceSensor.class, "sensor_distance");
        rightFrontMotor = opMode.hardwareMap.get(DcMotor.class, "Motor1");
        leftFrontMotor = opMode.hardwareMap.get(DcMotor.class, "Motor0");
        leftBackMotor = opMode.hardwareMap.get(DcMotor.class, "Motor2");
        rightBackMotor = opMode.hardwareMap.get(DcMotor.class, "Motor3");
        wintch1 = opMode.hardwareMap.get(DcMotor.class, "Motor4");
        wintch2 = opMode.hardwareMap.get(DcMotor.class, "Motor5");

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);



        // Stop & Reset Encoders
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }


    public class SensorREV2mDistance extends LinearOpMode {

    public void runOpMode() {

        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");

        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor) sensorDistance;

        telemetry.addData(">>", "Press start to continue");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {
            // generic DistanceSensor methods.
            telemetry.addData("deviceName", sensorDistance.getDeviceName() );
            telemetry.addData("range", String.format("%.01f cm", sensorDistance.getDistance(DistanceUnit.CM)));


            // Rev2mDistanceSensor specific methods.
            telemetry.addData("ID", String.format("%x", sensorTimeOfFlight.getModelID()));
            telemetry.addData("did time out", Boolean.toString(sensorTimeOfFlight.didTimeoutOccur()));

            telemetry.update();
        }
    }



    public void move(double movePower, String moveDirection, double moveDistance) {

        if(moveDirection.equals("forward")) {
            rightFrontMotor.setPower(movePower);
            rightBackMotor.setPower(movePower);
            leftFrontMotor.setPower(movePower);
            leftBackMotor.setPower(movePower);

        }else if (moveDirection.equals("backward")) {
                rightBackMotor.setPower(-movePower);
                rightFrontMotor.setPower(-movePower);
                leftBackMotor.setPower(-movePower);
                leftFrontMotor.setPower(-movePower);
            } else if (moveDirection.equals("right")) {
                leftBackMotor.setPower(-movePower);
                rightBackMotor.setPower(movePower);
                leftFrontMotor.setPower(movePower);
                rightFrontMotor.setPower(-movePower);
            } else if (moveDirection.equals("left")) {
                leftBackMotor.setPower(movePower);
                rightBackMotor.setPower(-movePower);
                leftFrontMotor.setPower(-movePower);
                rightFrontMotor.setPower(movePower);



            }

        }


    }


}
