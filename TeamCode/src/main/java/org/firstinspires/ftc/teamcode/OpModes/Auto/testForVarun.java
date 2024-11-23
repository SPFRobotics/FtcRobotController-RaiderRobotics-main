package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class testForVarun extends LinearOpMode{
    private DcMotor motor = null;
    @Override
    public void runOpMode() throws InterruptedException{
        motor = hardwareMap.dcMotor.get("Motor3");
        waitForStart();
        motor.setPower(1);
    }

}
