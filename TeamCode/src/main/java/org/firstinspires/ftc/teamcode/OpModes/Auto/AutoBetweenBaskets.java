package org.firstinspires.ftc.teamcode.OpModes.Auto;

import static java.nio.file.Files.move;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoBetweenBaskets {
    public void toSpikeMark() {
        move(.3, "forward", 113.03);
    }

    private void move(double v, String forward, double v1) {

    }
}