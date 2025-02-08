package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Util.Unit;

public class Lift {
    private DcMotor lift;

    public Lift(HardwareMap hardwareMap) {
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public class LiftUp implements Action {

        // checks if the lift motor has been powered on
        private boolean initialized = false;
        private static final int liftMaxMotorCounts = 4062;
        private int encoderTicks;
        LiftUp(double height){
            encoderTicks = (int) Unit.inchToLift_convert(height);
        }


        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                lift.setPower(1);
                initialized = true;
            }

            // checks lift's current position
            double pos = lift.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos < encoderTicks) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun
                lift.setPower(0);
                return false;
            }
            // overall, the action powers the lift until it surpasses
            // 3000 encoder ticks, then powers it off
        }
    }

    public class LiftDown implements Action {

        // checks if the lift motor has been powered on
        private boolean initialized = false;
        private static final int liftMaxMotorCounts = 4062;
        private int encoderTicks;
        LiftDown(double height){
            encoderTicks = (int) Unit.inchToLift_convert(height);
        }


        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                lift.setPower(-1);
                initialized = true;
            }

            // checks lift's current position
            double pos = lift.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos > encoderTicks) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun
                lift.setPower(0);
                return false;
            }
            // overall, the action powers the lift until it surpasses
            // 3000 encoder ticks, then powers it off
        }
    }

    public Action moveDown(double height){
        return new LiftDown(height);
    }
    public Action moveUp(double height){
        return new LiftUp(height);
    }
}
