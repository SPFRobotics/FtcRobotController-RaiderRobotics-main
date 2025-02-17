package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class NewExtendo {
    private DcMotor extendo;

    public NewExtendo(HardwareMap hardwareMap) {
        extendo = hardwareMap.get(DcMotor.class, "liftLeft");
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendo.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public class LiftOut implements Action {

        // checks if the lift motor has been powered on
        private boolean initialized = false;
        private static final int liftMaxMotorCounts = (int)(15.5*(537.7 / (4.409)));
        private int encoderTicks;

        LiftOut(double position){
            encoderTicks = inchToMotorTicks(position);
        }


        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                extendo.setPower(1);
                initialized = true;
            }

            // checks lift's current position
            double pos = extendo.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos < encoderTicks && pos > liftMaxMotorCounts) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun
                extendo.setPower(0);
                initialized = false;
                return false;
            }
            // overall, the action powers the lift until it surpasses
            // 3000 encoder ticks, then powers it off
        }
    }

    public class LiftIn implements Action {

        // checks if the lift motor has been powered on
        private boolean initialized = false;
        private static final int liftMaxMotorCounts = (int)(15.5*(537.7 / (4.409)));
        private int encoderTicks;
        LiftIn(double position){
            encoderTicks = inchToMotorTicks(position);
        }


        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                extendo.setPower(-1);
                initialized = true;
            }

            // checks lift's current position
            double pos = extendo.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos > encoderTicks && pos > 0) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun
                extendo.setPower(0);
                initialized = false;
                return false;
            }
        }
    }

    public Action moveIn(double position){
        return new LiftIn(position);
    }
    public Action moveOut(double position){
        return new LiftOut(position);
    }
    int inchToMotorTicks(double inches){
        return (int)(inches*(537.7 / (4.409)));
    }
}
