package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    private Servo claw = null;
    private Servo wristLeft = null;
    private final double OPEN_POS = 0.65;
    private final double CLOSED_POS = 0;
    private final double PLACING_POS = 1;
    private final double TRANSFER_POS = 0;

    public Outtake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "outtakeClaw");
        wristLeft = hardwareMap.get(Servo.class, "outtakeLeftWrist");
        wristLeft.setDirection(Servo.Direction.REVERSE);
        claw.setPosition(OPEN_POS);
        wristLeft.setPosition(TRANSFER_POS);
    }
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(CLOSED_POS);
            return false;
        }
    }
    public Action closeClaw(){
        return new CloseClaw();
    }
    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(OPEN_POS);
            return false;
        }

    }
    public Action openClaw(){
        return new OpenClaw();
    }
    public class PreparePlacement implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            wristLeft.setPosition(PLACING_POS);
            return false;
        }
    }
    public Action preparePlacement(){
        return new PreparePlacement();
    }
    public class PrepareTransfer implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            wristLeft.setPosition(TRANSFER_POS);
            return false;
        }
    }
    public Action prepareTransfer(){
        return new PrepareTransfer();
    }


}

