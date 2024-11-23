package org.firstinspires.ftc.teamcode.Hardware.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware.Util.Unit;

public class LinearSlide {
    public LinearOpMode opmode;
    public DcMotor lift = null;
    public double liftPosition = 0;
    private static final int liftMaxMotorCounts = 4062;

    public LinearSlide(LinearOpMode lom){ opmode = lom; }
    public void initSlides(){
        lift = opmode.hardwareMap.dcMotor.get("lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    private boolean isBusy(){
        return (lift.isBusy());
    }
    public void LiftHold() {
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (Math.abs(lift.getCurrentPosition() - liftPosition) <= 10) {
            lift.setPower(0);
        } else {
            lift.setPower(1);
        }
    }
    public void slide(double distance, double power){ //distance in inches
        int encoderAmount = (int)(Unit.inchToLift_convert(distance));
        liftPosition = encoderAmount;
        liftPosition = Range.clip(liftPosition,0,liftMaxMotorCounts);
        lift.setTargetPosition((int)liftPosition);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lift.setPower(power);

        while(isBusy()){
            //opmode.telemetry.addLine(String.format("Left: %6.1f, Right: %6.1f", (float)lift.getCurrentPosition()));
            //opmode.telemetry.update();
        }

        lift.setPower(0);
    }
}