package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.teamcode.cameraDetectColorTest2;


public class ColorCam {
    LinearOpMode opmode = null;
    OpenCvCamera camera = null;
    VisionPortal visionPortal;
    cameraDetectColorTest2 visionProcessor;
    cameraDetectColorTest1 gameObjectDetection = new cameraDetectColorTest1();
    //public String spikeLocation = gameObjectDetection.getPosition().toString();
    public String spikeLocation = null;
    public ColorCam(LinearOpMode lom){
        opmode = lom;
    }
    public void initCam(){
        //int cameraMonitorViewId = opmode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opmode.hardwareMap.appContext.getPackageName());
        //camera = OpenCvCameraFactory.getInstance().createWebcam(opmode.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        //gameObjectDetection = new cameraDetectColorTest1();
        //camera.setPipeline(gameObjectDetection);
        visionProcessor = new cameraDetectColorTest2();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(opmode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.addProcessor(visionProcessor);
        visionPortal = builder.build();
    }
    public void camSwap(){
    }
    public void camOn(){
        /*camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                opmode.telemetry.addData("problem"," error");
            }
        });*/
        visionPortal.resumeStreaming();
    }
    public void camOff(){
        //camera.closeCameraDeviceAsync(new OpenCvCamera.AsyncCameraCloseListener() {
        //    @Override
        //    public void onClose() {
                //camera.stopRecordingPipeline();
                //camera.stopStreaming();
        //    }
        //});
        visionPortal.stopStreaming();
    }
    public void updateSpikeLocation(){
        spikeLocation = visionProcessor.getPosition().toString();
    }
}
