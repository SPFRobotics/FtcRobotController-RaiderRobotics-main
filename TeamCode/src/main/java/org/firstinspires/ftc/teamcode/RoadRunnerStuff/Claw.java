package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private Servo leftArm = null;
    private Servo rightArm = null;
    private final double LEFT_ARM_OPEN_POS = 0;
    private final double RIGHT_ARM_OPEN_POS = 0;
    private final double LEFT_ARM_CLOSED_POS = 0.08;
    private final double RIGHT_ARM_CLOSED_POS = 0.09;

    public Claw(HardwareMap hardwareMap) {
        leftArm = hardwareMap.get(Servo.class, "outtakeLeftClaw");
        rightArm = hardwareMap.get(Servo.class, "outtakeRightClaw");
        leftArm.setDirection(Servo.Direction.REVERSE);
        leftArm.setPosition(LEFT_ARM_CLOSED_POS);
        rightArm.setPosition(RIGHT_ARM_CLOSED_POS);
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
}

