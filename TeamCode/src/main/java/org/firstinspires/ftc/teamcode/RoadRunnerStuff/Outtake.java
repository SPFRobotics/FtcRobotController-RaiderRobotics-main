package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    private Servo claw = null;
    private Servo wrist = null;
    private final double OPEN_POS = 0.25;
    private final double CLOSED_POS = 0;
    private final double PREPARE_PLACEMENT_POS = 0.4;
    private final double TRANSFER_POS = 0.68;

    public Outtake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "outtakeClaw");
        wrist = hardwareMap.get(Servo.class, "outtakeWrist");
        claw.setDirection(Servo.Direction.REVERSE);
        claw.setPosition(CLOSED_POS);
        wrist.setPosition(PREPARE_PLACEMENT_POS);
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
            wrist.setPosition(PREPARE_PLACEMENT_POS);
            return false;
        }
    }
    public Action preparePlacement(){
        return new PreparePlacement();
    }
    public class PrepareTransfer implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            wrist.setPosition(TRANSFER_POS);
            return false;
        }
    }
    public Action prepareTransfer(){
        return new PrepareTransfer();
    }



}

