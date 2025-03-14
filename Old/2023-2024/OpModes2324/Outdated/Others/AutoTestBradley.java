package org.firstinspires.ftc.teamcode.OpModes2324.Outdated.Others;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
@Disabled
public class AutoTestBradley extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    //private MecanumDrive myDrive = new MecanumDrive(this);
    private MecanumDriveBradley myDrive = new MecanumDriveBradley(this);


    @Override
    public void runOpMode(){
        myDrive.initialize();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {
            //also changed because of file name error
            //myDrive.driveEncoder(MecanumDrive.Direction.RIGHT, 10, 0.3);
            myDrive.driveEncoder(MecanumDriveBradley.Direction.RIGHT, 10, 0.3);
            sleep(1000);
            while (opModeIsActive() && myDrive.isBusy()){/*Wait until all motors reach their destination*/}

            /*
            leftFrontWheel.setPower(0);
            leftBackWheel.setPower(0);
            rightBackWheel.setPower(0);
            rightFrontWheel.setPower(0);
            */
        }


    }
}