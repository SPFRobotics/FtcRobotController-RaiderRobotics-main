package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Lift {
    private DcMotor liftLeft;
    private DcMotor liftRight;

    public Lift(HardwareMap hardwareMap) {
        liftLeft = hardwareMap.get(DcMotor.class, "liftLeft");
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight = hardwareMap.get(DcMotor.class, "liftRight");
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);
        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public class LiftUp implements Action {

        // checks if the lift motor has been powered on
        private boolean initialized = false;
        private static final int liftMaxMotorCounts = (int)(28*(537.7 / (4.724955)));
        private int encoderTicks;

        LiftUp(double height){
            encoderTicks = inchToMotorTicks(height);
        }



        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                liftLeft.setPower(1);
                liftRight.setPower(1);
                initialized = true;
            }

            // checks lift's current position
            double pos = liftLeft.getCurrentPosition();
            double pos1 = liftRight.getCurrentPosition();
            packet.put("liftPos", pos);
            if ((pos < encoderTicks && pos1 < encoderTicks) && pos < liftMaxMotorCounts) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun
                liftLeft.setPower(0);
                liftRight.setPower(0);
                initialized = false;
                return false;
            }
            // overall, the action powers the lift until it surpasses
            // 3000 encoder ticks, then powers it off
        }
    }

    public class LiftDown implements Action {

        // checks if the lift motor has been powered on
        private boolean initialized = false;
        private static final int liftMaxMotorCounts = (int)(28*(537.7 / (4.724955)));
        private int encoderTicks;
        LiftDown(double height){
            encoderTicks = inchToMotorTicks(height);
        }


        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                liftLeft.setPower(-1);
                liftRight.setPower(-1);
                initialized = true;
            }

            // checks lift's current position
            double pos = liftLeft.getCurrentPosition();
            double pos1 = liftRight.getCurrentPosition();
            packet.put("liftPos", pos);
            if ((pos > encoderTicks&& pos1> encoderTicks) && pos > 0) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun
                liftLeft.setPower(0);
                liftRight.setPower(0);
                initialized = false;
                return false;
            }
        }
    }

    public Action moveDown(double height){
        return new LiftDown(height);
    }
    public Action moveUp(double height){
        return new LiftUp(height);
    }
    int inchToMotorTicks(double inches){
        return (int)(inches*(537.7 / (4.724955)));
    }
}
