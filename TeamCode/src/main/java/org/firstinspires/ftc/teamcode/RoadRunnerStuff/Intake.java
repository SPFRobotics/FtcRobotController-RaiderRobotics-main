package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private Servo wrist = null;
    private Servo claw = null;
    private Servo clawRotation = null;
    private Servo armRotation = null;
    private final double CLAW_CLOSED_POS = 0.6;
    private final double CLAW_OPEN_POS = 0.3;
    private final double ARM_ROTATION_POS = 0.5221;
    private final double CLAW_ROTATION_INTAKE =0.65;
    private final double CLAW_ROTATION_GROUND_INTAKE =0.15;
    private final double CLAW_ROTATION_TRANSFER =0;
    private final double WRIST_TRANSFER_POS = 0.7;
    private final double WRIST_INTAKE_POS = 0.35;
    private final double WRIST_GROUND_INTAKE_POS = 0.2;
    public Intake(HardwareMap hardwareMap){
        armRotation = hardwareMap.get(Servo.class, "frontRotation");
        wrist = hardwareMap.get(Servo.class, "frontWrist");
        clawRotation = hardwareMap.get(Servo.class, "frontClawRotation");
        claw = hardwareMap.get(Servo.class, "frontClaw");
        wrist.setDirection(Servo.Direction.REVERSE);

        // Initialize positions
        claw.setPosition(CLAW_OPEN_POS);
        armRotation.setPosition(ARM_ROTATION_POS);
        clawRotation.setPosition(CLAW_ROTATION_TRANSFER);
        wrist.setPosition(WRIST_TRANSFER_POS);
    }
    public class PrepareIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            clawRotation.setPosition(CLAW_ROTATION_INTAKE);
            wrist.setPosition(WRIST_INTAKE_POS);
            return false;
        }
    }
    public class PrepareTransfer implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            clawRotation.setPosition(CLAW_ROTATION_TRANSFER);
            wrist.setPosition(WRIST_TRANSFER_POS);
            return false;
        }
    }
    public class CloseClaw implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(CLAW_CLOSED_POS);
            return false;
        }
    }
    public class OpenClaw implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(CLAW_OPEN_POS);
            return false;
        }
    }
    public class PrepareGroundIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            clawRotation.setPosition(CLAW_ROTATION_GROUND_INTAKE);
            wrist.setPosition(WRIST_GROUND_INTAKE_POS);
            return false;
        }
    }
    public Action prepareIntake(){
        return new PrepareIntake();
    }
    public Action prepareGroundIntake(){
        return new PrepareIntake();
    }
    public Action prepareTransfer(){
        return new PrepareTransfer();
    }
    public Action closeClaw(){
        return new CloseClaw();
    }
    public Action openClaw(){
        return new OpenClaw();
    }
}
