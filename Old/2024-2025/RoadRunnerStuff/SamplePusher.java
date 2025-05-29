package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SamplePusher {
    private final double INIT_POS = 0;
    private final double PUSHING_POS = 0.975;
    private final double REST_POS = 0.5;
    private Servo arm = null;
    public SamplePusher(HardwareMap hardwareMap) {
        arm = hardwareMap.get(Servo.class, "sweeper");
        arm.setPosition(REST_POS);
    }
    public class LowerArm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(PUSHING_POS);
            return false;
        }
    }
    public Action lowerArm(){
        return new LowerArm();
    }
    public class RaiseArm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(REST_POS);
            return false;
        }
    }
    public Action raiseArm(){
        return new RaiseArm();
    }
}
