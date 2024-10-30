package org.firstinspires.ftc.teamcode.Hardware.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private LinearOpMode opmode = null;
    private Servo leftArm = null;
    private Servo rightArm = null;
    private final double LEFT_ARM_OPEN_POS = 0;
    private final double RIGHT_ARM_OPEN_POS = 0;
    private final double LEFT_ARM_CLOSED_POS = .5;
    private final double RIGHT_ARM_CLOSED_POS = .5;
    public Claw(LinearOpMode lom){
        opmode = lom;
    }
    public void init(){
        leftArm = opmode.hardwareMap.servo.get("leftArm");
        rightArm = opmode.hardwareMap.servo.get("rightArm");
        close();
    }
    public void close(){
        leftArm.setPosition(LEFT_ARM_CLOSED_POS);
        rightArm.setPosition(RIGHT_ARM_CLOSED_POS);
    }
    public void open(){
        leftArm.setPosition(LEFT_ARM_OPEN_POS);
        rightArm.setPosition(RIGHT_ARM_OPEN_POS);
    }
}
