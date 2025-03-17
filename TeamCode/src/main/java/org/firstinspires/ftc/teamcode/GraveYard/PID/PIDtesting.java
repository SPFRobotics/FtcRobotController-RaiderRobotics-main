package org.firstinspires.ftc.teamcode.GraveYard.PID;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.PIDcontroller;

@Disabled
@Autonomous
public class PIDtesting extends LinearOpMode {
    private double kP = 0.0055;
    private double kI = 0.0000000001;
    private double kD = 0.0002;
    PIDcontroller controller = new PIDcontroller(this,kP,kI,kD);

    public DcMotor liftRight;
    public DcMotor liftLeft;
    private double powerRight = 0;
    private double powerLeft = 0;

    public void runOpMode() {
        liftLeft = hardwareMap.dcMotor.get("liftLeft"); /** Port: ExpansionHub MotorPort 3 **/
        liftRight = hardwareMap.dcMotor.get("liftRight"); /** Port: ExpansionHub MotorPort 2 **/
        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            powerLeft = controller.controller(2000,liftLeft.getCurrentPosition(),0.8,true);
            powerRight = controller.controller(2000,liftRight.getCurrentPosition(),0.8,true);
            liftLeft.setPower(powerLeft);
            liftRight.setPower(powerRight);
            telemetry.addLine(String.format("power  Left: %6.1f, Right: %6.1f",powerLeft,powerRight));
            telemetry.update();
        }
    }
}
