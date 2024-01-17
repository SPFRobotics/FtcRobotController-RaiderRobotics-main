package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class PIDtesting extends LinearOpMode {
    private double kP = 0.008;
    private double kI = 0;
    private double kD = 0;
    PIDcontroller controller = new PIDcontroller(this,2000,kP,kI,kD);

    public DcMotor liftRight;
    public DcMotor liftLeft;
    private double powerRight = 0;
    private double powerLeft = 0;

    public void runOpMode() {
        liftLeft = hardwareMap.dcMotor.get("liftLeft"); /** Port: ExpansionHub MotorPort 3 **/
        liftRight = hardwareMap.dcMotor.get("liftRight"); /** Port: ExpansionHub MotorPort 2 **/
        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (true) {
            powerLeft = controller.controller(liftLeft.getCurrentPosition(),0.5,true);
            powerRight = controller.controller(liftRight.getCurrentPosition(),0.5,true);
            telemetry.addLine(String.format("power  Left: %6.1f, Right: %6.1f",powerLeft,powerRight));

            liftLeft.setPower(powerLeft);
            liftRight.setPower(powerRight);
        }
    }
}
