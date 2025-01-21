package org.firstinspires.ftc.teamcode.Hardware;


public class Button {
    private boolean stillPressed = false;
    private boolean activated = false;


    public boolean press(boolean button){
        if (!button){
            stillPressed = false;
        }
        if (button && !stillPressed && !activated){
            activated = true;
            stillPressed = true;
        }
        else if (button && !stillPressed && activated) {
            activated = false;
            stillPressed = true;
        }
        return activated;
    }
}
