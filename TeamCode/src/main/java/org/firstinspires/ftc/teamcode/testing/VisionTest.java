//package org.firstinspires.ftc.teamcode.opmode.test.vision;
package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import org.firstinspires.ftc.teamcode.Hardware.Outdated.cameraDetectColorTest1;

@Disabled
@Autonomous(name = "Game Object Test")
public class VisionTest extends LinearOpMode {

    cameraDetectColorTest1 gameObjectDetection = new cameraDetectColorTest1();
    OpenCvCamera camera;
    String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        gameObjectDetection = new cameraDetectColorTest1();
        camera.setPipeline(gameObjectDetection);
        //camera.setPipeline(new cameraDetectColorTest1());


        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("problem"," error");
            }
        });

        while (!isStarted()) {
            telemetry.addData("POSITION: ", gameObjectDetection.getPosition().toString());
            telemetry.addData("Info: ", "MaxPercent: %f, HighestSector: %f", gameObjectDetection.randomStuffInfo()[0],gameObjectDetection.randomStuffInfo()[1]);
            telemetry.update();
        }

        waitForStart();
    }
}