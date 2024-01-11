package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

public class LinearSlide {
    public LinearOpMode opmode;
    public DcMotor liftLeft = null;
    public DcMotor liftRight = null;
    public double liftPosition = 0;
    private static final int liftMaxMotorCounts = 4062;

    public LinearSlide(LinearOpMode lom){ opmode = lom; }
    public void initSlides(){
        liftLeft = opmode.hardwareMap.dcMotor.get("liftLeft"); /** Port: ExpansionHub MotorPort 3 **/
        liftRight = opmode.hardwareMap.dcMotor.get("liftRight"); /** Port: ExpansionHub MotorPort 2 **/

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    private boolean isBusy(){
        return (liftLeft.isBusy() || liftRight.isBusy());
    }
    public void LiftHold() {
        liftLeft.setTargetPosition(0);
        liftRight.setTargetPosition(0);
        liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (Math.abs(liftLeft.getCurrentPosition() - liftPosition) <= 10 && Math.abs(liftRight.getCurrentPosition() - liftPosition) <= 10) {
            liftLeft.setPower(0);
            liftRight.setPower(0);
        } else {
            liftLeft.setPower(1);
            liftRight.setPower(1);
        }
    }
    public void slide(double distance, double power){ //distance in inches
        int encoderAmount = (int)(Unit.inchToLift_convert(distance));
        liftPosition = encoderAmount;
        liftPosition = Range.clip(liftPosition,0,liftMaxMotorCounts);
        liftLeft.setTargetPosition((int)liftPosition);
        liftRight.setTargetPosition((int)liftPosition);
        liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftLeft.setPower(power);
        liftRight.setPower(power);

        while(isBusy()){
            opmode.telemetry.addLine(String.format("Left: %6.1f, Right: %6.1f", (float)liftLeft.getCurrentPosition(),(float)liftRight.getCurrentPosition()));
            opmode.telemetry.update();
        }

        liftLeft.setPower(0);
        liftRight.setPower(0);
    }
}