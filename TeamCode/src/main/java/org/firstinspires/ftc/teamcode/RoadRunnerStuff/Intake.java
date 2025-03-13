package org.firstinspires.ftc.teamcode.RoadRunnerStuff;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.OpModes.Values;

public class Intake {
    private Servo wristLeft = null;
    private Servo wristRight = null;
    private Servo claw = null;
    private Servo clawRotation = null;
    private Servo armRotation = null;

    public Intake(HardwareMap hardwareMap){
        wristLeft = hardwareMap.get(Servo.class, "intakeLeftWrist");
        wristRight = hardwareMap.get(Servo.class, "intakeRightWrist");
        clawRotation = hardwareMap.get(Servo.class, "intakeClawRotation");
        claw = hardwareMap.get(Servo.class, "intakeClaw");
        wristRight.setDirection(Servo.Direction.REVERSE);

        // Initialize positions
        claw.setPosition(Values.Intake.ClawOpenPos);
        clawRotation.setPosition(Values.Intake.rotationTransferPos);
        wristLeft.setPosition(Values.Intake.wristLeftTransferPos);
        wristRight.setPosition(Values.Intake.wristRightTransferPos);
    }
    public class PrepareIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(Values.Intake.ClawOpenPos);
            wristLeft.setPosition(Values.Intake.wristLeftIntakePos);
            wristRight.setPosition(Values.Intake.wristRightIntakePos);
            return false;
        }
    }
    public class Transfer implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            wristLeft.setPosition(Values.Intake.wristLeftTransferPos);
            wristRight.setPosition(Values.Intake.wristRightTransferPos);
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
