package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Apecs {
    public LinearOpMode opmode = null;
    public Servo door = null;
    public double minPos = 0;
    public double maxPos = 1; //Temporary value -- PLEASE TEST VALUE!!!
    public Apecs(LinearOpMode lom){
        opmode = lom;
    }
    public void initApecs(){ door = opmode.hardwareMap.servo.get(""); /*Get Name of Door*/ }
    public void open(){
        door.setPosition(maxPos);
    }
    public void close(){
        door.setPosition(0);
    }
}
