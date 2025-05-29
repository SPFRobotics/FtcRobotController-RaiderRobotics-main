package org.firstinspires.ftc.teamcode.GraveYard.PID;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.Hardware.PIDcontroller;
import org.firstinspires.ftc.teamcode.Hardware.Util.Unit;

@Disabled
@Autonomous
@Config
public class PIDtestingDrive extends LinearOpMode {
    public static double kP = 0.01;
    public static double kI = 0.00001;
    public static double kD = 0.0001;
    public static double POWER = 0.5;
    public static String DIRECTION = "left";
    public static double DISTANCE = 20;

    PIDcontroller controller = new PIDcontroller(this,kP,kI,kD);
    MecanumChassis chassis = new MecanumChassis(this);

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    private double powerFrontRight = 0;
    private double powerFrontLeft = 0;
    private double powerBackLeft = 0;
    private double powerBackRight = 0;
    private double targetDist = -30;

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

        chassis.initializeMovement();

        waitForStart();
        /*while (opModeIsActive()) {
            powerFrontLeft = controller.controller(targetDist,frontLeft.getCurrentPosition(),POWER);
            powerFrontRight = controller.controller(targetDist,frontRight.getCurrentPosition(),POWER);
            powerBackLeft = controller.controller(targetDist,backLeft.getCurrentPosition(),POWER);
            powerBackRight = controller.controller(targetDist,backRight.getCurrentPosition(),POWER);
            frontLeft.setPower(powerFrontLeft);
            frontRight.setPower(powerFrontRight);
            backLeft.setPower(powerBackLeft);
            backRight.setPower(powerBackRight);
            telemetry.addLine(String.format("Front Power \n Left: %6.1f, Right: %6.1f \n Back Power \n Left: %6.1f, Right: %6.1f",powerFrontLeft,powerFrontRight,powerBackLeft,powerBackRight));
            telemetry.update();
        }*/
        chassis.move(POWER,DIRECTION,DISTANCE);
    }
}
