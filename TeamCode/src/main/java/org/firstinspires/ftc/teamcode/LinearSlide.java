package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class LinearSlide {
    public LinearOpMode opmode;
    public DcMotor liftLeft = null;
    public DcMotor liftRight = null;
    public double liftPosition = 0;

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
    private void LiftHold() {
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
    private void slide(double distance, double power){ //distance in inches
        int encoderAmount = (int)(Unit.inch_convert(distance));
        liftLeft.setTargetPosition(encoderAmount);
        liftRight.setTargetPosition(encoderAmount);
        liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftLeft.setPower(power);
        liftRight.setPower(power);

        while(isBusy()){/*Wait Til The Motors Stop Moving*/}

        liftLeft.setPower(0);
        liftRight.setPower(0);
    }
}
