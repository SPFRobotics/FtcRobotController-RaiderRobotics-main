package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class PIDtestingDrive extends LinearOpMode {
    private double kP = 0.01;
    private double kI = 0.00001;
    private double kD = 0.0001;
    PIDcontroller controller = new PIDcontroller(this,kP,kI,kD);

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    private double powerFrontRight = 0;
    private double powerFrontLeft = 0;
    private double powerBackLeft = 0;
    private double powerBackRight = 0;
    private double targetDist = 30;

    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft"); /** Port: ControlHub MotorPort 1 **/
        backLeft = hardwareMap.dcMotor.get("backLeft"); /** Port: ControlHub MotorPort 0 **/
        frontRight = hardwareMap.dcMotor.get("frontRight"); /** Port: ExpansionHub MotorPort 3 **/
        backRight = hardwareMap.dcMotor.get("backRight"); /** Port: ExpansionHub MotorPort 2 **/
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        targetDist = Unit.inch_convert(targetDist);

        waitForStart();
        while (opModeIsActive()) {
            double power = 0.5;
            powerFrontLeft = controller.controller(targetDist,frontLeft.getCurrentPosition(),power);
            powerFrontRight = controller.controller(targetDist,frontRight.getCurrentPosition(),power);
            powerBackLeft = controller.controller(targetDist,backLeft.getCurrentPosition(),power);
            powerBackRight = controller.controller(targetDist,backRight.getCurrentPosition(),power);
            frontLeft.setPower(powerFrontLeft);
            frontRight.setPower(powerFrontRight);
            backLeft.setPower(powerBackLeft);
            backRight.setPower(powerBackRight);
            telemetry.addLine(String.format("Front Power \n Left: %6.1f, Right: %6.1f \n Back Power \n Left: %6.1f, Right: %6.1f",powerFrontLeft,powerFrontRight,powerBackLeft,powerBackRight));
            telemetry.update();
        }
    }
}
