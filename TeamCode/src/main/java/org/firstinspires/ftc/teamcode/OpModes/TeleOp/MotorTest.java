package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class MotorTest {
    private DcMotor rightFrontMotor = null;
    private DcMotor leftFrontMotor = null;
    private DcMotor rightBackMotor = null;
    private DcMotor leftBackMotor = null;
    public void runOpMode() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "frontRight");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        rightBackMotor = hardwareMap.get(DcMotor.class, "backRight");
        leftBackMotor = hardwareMap.get(DcMotor.class, "backLeft");
        if(gamepad1.right_bumper){
            rightFrontMotor.setPower(1);
        }
        else{
            rightFrontMotor.setPower(0);
        }
        if(gamepad1.left_bumper){
            leftFrontMotor.setPower(1);
        }
        else{
            leftBackMotor.setPower(0);
        }
        if(gamepad1.right_trigger!=0){
            rightBackMotor.setPower(1);
        }
        else{
            rightBackMotor.setPower(0);
        }
        if(gamepad1.left_trigger!=0){
            leftBackMotor.setPower(1);
        }
        else{
            leftBackMotor.setPower(0);
        }
    }
}
