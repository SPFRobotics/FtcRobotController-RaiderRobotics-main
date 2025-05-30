package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;

public class Values {
    @Config
    public static class Outtake{
        public static double ClawClosedPos = 0.25;
        public static double ClawOpenPos = 0;
        public static double wristSpeedMultiplyer = 0.05;
        public static double rOuttakeWrist = 0;
        public static double lOuttakeWrist = 0;
    }

    @Config
    public static class Testing{
        public static int time = 0;
    }

    @Config
    public static class verticalSlide{
        public static double power = 1;
    }

    @Config
    public static class Intake{
        public static double ClawOpenPos = 0;
        public static double ClawClosedPos = 0.5;
        public static double wristLeftTransferPos = 0.0;
        public static double wristLeftIntakePos = 0.3;
        public static double wristRightTransferPos = 0;
        public static double wristRightIntakePos = 0.3;
        public static double rotationIntakePos = 0;
        public static double rotationTransferPos = 0;
        public static double wristSpeedMultiplyer = 0.025;

    }
}
