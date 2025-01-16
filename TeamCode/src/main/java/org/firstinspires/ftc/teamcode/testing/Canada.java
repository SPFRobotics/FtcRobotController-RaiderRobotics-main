package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

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

        ClawServo.setPosition(.5);
        ClawRotationServo.setPosition(0);


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

    public void closClaw(double closePos){

        closePos = 0;
        ClawServo.setPosition(closePos);

    }


    public void openClaw(double openPos){

        openPos = .6;
        ClawServo.setPosition(openPos);

    }

    public void rotateClaw(double rotatePos){

        rotatePos = 0.5276;
        ClawRotationServo.setPosition(rotatePos);
    }


}



