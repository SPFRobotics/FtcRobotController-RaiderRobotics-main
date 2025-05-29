package org.firstinspires.ftc.teamcode.Outreach;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Outreach2 extends LinearOpMode {
    private DcMotor FRMotor = null;
    private DcMotor FLMotor = null;
    private DcMotor BRMotor = null;
    private DcMotor BLMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {

        FLMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        FRMotor = hardwareMap.get(DcMotor.class, "frontRight");
        BLMotor = hardwareMap.get(DcMotor.class, "backLeft");
        BRMotor = hardwareMap.get(DcMotor.class, "backRight");

        BRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        while (opModeIsActive()){
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            FLMotor.setPower((y + x + rx) / denominator);
            BLMotor.setPower((y - x + rx) / denominator);
            FRMotor.setPower((y - x - rx) / denominator);
            BRMotor.setPower((y + x - rx) / denominator);
        }
    }
}
