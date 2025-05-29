package org.firstinspires.ftc.teamcode.OpModes.TeleOp;
import android.renderscript.Sampler;
import android.text.method.Touch;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.configuration.ServoHubConfiguration;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ServoChannelParameters;
import org.firstinspires.ftc.teamcode.Hardware.Button;
import org.firstinspires.ftc.teamcode.OpModes.Values;

@TeleOp(name="ManavTELEOP", group="Linear Opmode")



public class ManavTELEOP extends LinearOpMode {
    // FL = Front Left
    // BL= Back Left
    // FR = Front Right
    // BR = Back Right
    private DcMotor motorFL = null;
    private DcMotor motorFR = null;
    private DcMotor motorBL = null;
    private DcMotor motorBR = null;

    @Override
    public void waitForStart() {

    }

    public void runOpMode() {

        DcMotor motorFL = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor motorFR = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor motorBL = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor motorBR = hardwareMap.get(DcMotor.class, "backRight");

        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;//counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double motorFLPower = (y + x + rx) / denominator;
            double motorBLPower = (y - x + rx) / denominator;
            double motorFRPower = (y - x - rx) / denominator;
            double motorBRPower = (y + x - rx) / denominator;

            motorFL.setPower(motorFLPower);
            motorFR.setPower(motorFRPower);
            motorBL.setPower(motorBLPower);
            motorBR.setPower(motorBRPower);


            telemetry.update();
            telemetry.addLine("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            telemetry.addLine("FL: " + motorFLPower);
            telemetry.addLine("FR: " + motorFRPower);
            telemetry.addLine("BL: " + motorBLPower);
            telemetry.addLine("BR: " + motorBRPower);



        }
    }

}
