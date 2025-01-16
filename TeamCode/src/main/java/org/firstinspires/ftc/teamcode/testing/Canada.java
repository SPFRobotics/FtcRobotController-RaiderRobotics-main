package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.qualcomm.robotcore.hardware.Servo;

public class Canada {
    private Servo RotationServo = null;
    private Servo WristServo = null;
    private Servo ClawRotationServo = null;
    private Servo ClawServo = null;




    // - rs is RotationServo, ws is WristServo, crs is ClawRotationServo, cs is ClawServo
    public Canada(Servo rs, Servo ws, Servo crs, Servo cs){
        RotationServo = rs;
        WristServo = ws;
        ClawRotationServo = crs;
        ClawServo = cs;


    }

    // - rrs is resetRotationServo, rws is restWristServo, rclrose is resetClawRotationServo, rclse is resetClawServo
    public void resetClaw(Servo rrs, Servo rws, double rclrose, double rclse){
        RotationServo = rrs;
        WristServo = rws;
        ClawRotationServo.setPosition(rclrose);
        ClawServo.setPosition(rclse);





    }

    // - srs is setRotationServo, sws is setWristServo
    public void setClaw(Servo srs, Servo sws){
        RotationServo = srs;
        WristServo = sws;
    }



    }

//-------------------------------------------------------------------------------------------------------


}
