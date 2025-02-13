package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    private Servo leftArm = null;
    private Servo rightArm = null;
    private Servo wrist = null;
    private final double LEFT_ARM_OPEN_POS = 0.5;
    private final double RIGHT_ARM_OPEN_POS = 0.5;
    private final double LEFT_ARM_CLOSED_POS = 0.28;
    private final double RIGHT_ARM_CLOSED_POS = 0.28;
    private final double WRIST_TRANSIT_POS = 0.155;
    private final double WRIST_PICKUP_POS = 0.48435;

    public Outtake(HardwareMap hardwareMap) {
        rightArm = hardwareMap.get(Servo.class, "backRightClaw");
        leftArm = hardwareMap.get(Servo.class, "backLeftClaw");
        wrist = hardwareMap.get(Servo.class, "backWrist");
        rightArm.setDirection(Servo.Direction.REVERSE);
        leftArm.setPosition(LEFT_ARM_CLOSED_POS);
        rightArm.setPosition(RIGHT_ARM_CLOSED_POS);
        wrist.setPosition(WRIST_PICKUP_POS);
    }
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftArm.setPosition(LEFT_ARM_CLOSED_POS);
            rightArm.setPosition(RIGHT_ARM_CLOSED_POS);
            return false;
        }

    }
    public Action closeClaw(){
        return new CloseClaw();
    }
    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftArm.setPosition(LEFT_ARM_OPEN_POS);
            rightArm.setPosition(RIGHT_ARM_OPEN_POS);
            return false;
        }

    }
    public Action openClaw(){
        return new OpenClaw();
    }
    public class LowerSpec implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            wrist.setPosition(0.8);
            return false;
        }
    }
    public Action lowerSpec(){
        return new LowerSpec();
    }
    public class PrepareIntake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            wrist.setPosition(WRIST_TRANSIT_POS);
            leftArm.setPosition(LEFT_ARM_OPEN_POS);
            rightArm.setPosition(RIGHT_ARM_OPEN_POS);
            return false;
        }
    }
    public Action prepareIntake(){
        return new PrepareIntake();
    }

}

