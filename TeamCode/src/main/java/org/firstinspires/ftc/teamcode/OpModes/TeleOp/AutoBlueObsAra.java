
package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot.Intake;
import org.firstinspires.ftc.teamcode.Hardware.Robot.LinearSlide;
import org.firstinspires.ftc.teamcode.Hardware.Robot.MecanumChassis;
import org.firstinspires.ftc.teamcode.Hardware.aprilTagDetectionMovement;

public class AutoBlueObsAra {
    public LinearOpMode opmode = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontright = null;
    private IMU imu = null;
    public AutoBlueObsAra(LinearOpMode lom){
        opmode = lom;
    }

    // private double inch_convert(double inc) { return inch * (537.7 /(3.78 * Math.PI));}
    private double inToCm(int inches) { return inches * 2.54; }
    private double cm_convert(double cm) { return cm * (537.7 / (9.6012 / Math.PI)); }
}
