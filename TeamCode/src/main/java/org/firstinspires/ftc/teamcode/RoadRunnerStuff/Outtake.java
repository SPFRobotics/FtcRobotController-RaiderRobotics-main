package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    private Servo claw = null;
    private final double OPEN_POS = 0;
    private final double CLOSED_POS = 0.1;

    public Outtake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "backClaw");
        claw.setPosition(OPEN_POS);
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

}

