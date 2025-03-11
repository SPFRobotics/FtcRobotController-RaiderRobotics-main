package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.OpModes.Values;

public class Intake {
    private Servo wrist = null;
    private Servo claw = null;
    private Servo clawRotation = null;
    private Servo armRotation = null;

    public Intake(HardwareMap hardwareMap){
        wrist = hardwareMap.get(Servo.class, "intakeWrist");
        clawRotation = hardwareMap.get(Servo.class, "intakeRotation");
        claw = hardwareMap.get(Servo.class, "intakeClaw");
        wrist.setDirection(Servo.Direction.REVERSE);

        // Initialize positions
        claw.setPosition(Values.Intake.ClawOpenPos);
        clawRotation.setPosition(Values.Intake.rotationTransferPos);
        wrist.setPosition(Values.Intake.wristTransferPos);
    }
    public class PrepareIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(Values.Intake.ClawOpenPos);
            clawRotation.setPosition(Values.Intake.rotationIntakePos);
            wrist.setPosition(Values.Intake.wristIntakePos);
            return false;
        }
    }
    public class Transfer implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            clawRotation.setPosition(Values.Intake.rotationTransferPos);
            wrist.setPosition(Values.Intake.wristTransferPos);
            return false;
        }
    }
    public class CloseClaw implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(Values.Intake.ClawClosedPos);
            return false;
        }
    }
    public class OpenClaw implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(Values.Intake.ClawOpenPos);
            return false;
        }
    }

    public Action prepareIntake(){
        return new PrepareIntake();
    }

    public Action transfer(){
        return new Transfer();
    }
    public Action closeClaw(){
        return new CloseClaw();
    }
    public Action openClaw(){
        return new OpenClaw();
    }
}
