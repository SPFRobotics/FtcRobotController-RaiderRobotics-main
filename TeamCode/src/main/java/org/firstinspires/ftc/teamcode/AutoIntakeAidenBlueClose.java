package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.List;

@Autonomous
public class AutoIntakeAidenBlueClose extends LinearOpMode {
    //MOVEMENT MOTOR VARS
    private static final double strafeMult = 1.2;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor liftLeft = null;
    private DcMotor liftRight = null;
    private IMU imu = null;




    //INTAKE MOTOR VARS
    private DcMotor intake = null;
    double maxIntakePos = 4062;
    double minEncoder = 0;

    //Color Vars
    OpenCvCamera camera;
    cameraDetectColorTest1 gameObjectDetection = new cameraDetectColorTest1();
    /*final*/ String spikeLocation = gameObjectDetection.getPosition().toString();
    public enum backBoardAprilTags {
        RedAllianceLeft,
        RedAllianceCenter,
        RedAllianceRight,
        BlueAllianceLeft,
        BlueAllianceCenter,
        BlueAllianceRight
    }
    private double[] cameraOffset = new double[] {3.5,5.5}; // x offset (left: positive, right: negative), y(distance) offset; (units: inches from center)
    private double[] robotDistanceToAprilTag = new double[] {0,0};
    private double moveOffsetY = 20;
    //public double[] outputInfo = new double[] {};
    public backBoardAprilTags selectedAprilTag = null;

    //Outtake Vars
    Servo wristLeft = null;
    Servo wristRight = null;
    double wristPos = 0;
    double minWristPos = -1.0;
    double maxWristPos = 0.9;
    double[] moveDistance = new double[] {0,0};
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;


    //INTAKE FUNCTIONS
    public void initializeIntake(){
        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private void intake(double power, long sec) {
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setPower(power);
        sleep(sec * 1000);
        intake.setPower(0);
    }
    public void placeOnSpikeMark(){
        //Move to center of spike marks
        //spikeLocation = "LEFT";
        double power = -.3;
        if(spikeLocation.equals("LEFT")) {
            selectedAprilTag = backBoardAprilTags.BlueAllianceLeft;
            move(.3, "forward", 18);
            move(.3, "left", 12);
            intake(power, 3);
            move(.3, "right", 12);
            //move(.3, "backward", 18);
            rotate(-90,0.3);
            //while (backBoardDetection.aprilTag.getDetections().size() <= 0) {telemetry.addData("%f",backBoardDetection.aprilTag.getDetections().size());telemetry.update();}
            moveToAprilTag(selectedAprilTag);
        } else if(spikeLocation.equals("RIGHT")){
            selectedAprilTag = backBoardAprilTags.BlueAllianceRight;
            move(.3, "forward", 18);
            move(.3, "right", 12);
            intake(power, 3);
            move(.3, "left", 12);
            //move(.3, "backward", 18);
            rotate(-90,0.3);
            //while (backBoardDetection.aprilTag.getDetections().size() <= 0) {telemetry.addData("%f",backBoardDetection.aprilTag.getDetections().size());telemetry.update();}
            moveToAprilTag(selectedAprilTag);
        } else if(spikeLocation.equals("CENTER")){
            selectedAprilTag = backBoardAprilTags.BlueAllianceCenter;
            move(.3, "forward", 25);
            intake(power, 3);
            //move(.3, "backward", 25);
            rotate(-90,0.3);
            //while (backBoardDetection.aprilTag.getDetections().size() <= 0) {telemetry.addData("%f",backBoardDetection.aprilTag.getDetections().size());telemetry.update();}
            moveToAprilTag(selectedAprilTag);
        } else if(!spikeLocation.equals("CENTER") && !spikeLocation.equals("LEFT")) {
            selectedAprilTag = backBoardAprilTags.BlueAllianceRight;
            move(.3, "forward", 18);
            move(.3, "right", 12);
            intake(power, 3);
            move(.3, "left", 12);
            //move(.3, "backward", 18);
            rotate(-90,0.3);
            //while (backBoardDetection.aprilTag.getDetections().size() <= 0) {telemetry.addData("%f",backBoardDetection.aprilTag.getDetections().size());telemetry.update();}
            moveToAprilTag(selectedAprilTag);
        }else {
            telemetry.addData("Team Element", "Not Found");
            telemetry.update();
        }
    }



    //MOVEMENT FUNCTIONS
    //3.78(in inches, 9.6012 is centimeters) is the diameter of the wheel, and 537.7 is how many motor counts are in 1 full rotation of the motor's axle
    private double inch_convert(double inch) { return inch * (537.7 / (3.78 * Math.PI)); }
    private double inToCm(int inches) { return inches * 2.54; }
    private double cmToIn(double cm) { return cm / 2.54; }
    private double cm_convert(double cm) { return cm * (537.7 / (9.6012 / Math.PI)); }

    void parkFarRed(){
        move(.3, "forward", 5.5);
        move(.3, "right", 96);
    }
    void parkCloseRed(){
        move(.3, "forward", 3);
        move(.3, "right", 46);
    }
    void parkFarBlue(){
        move(.3, "forward", 5.5);
        move(.3, "left", 96);
    }
    void parkCloseBlue(){
        move(.3, "forward", 3);
        move(.3, "left", 46);
    }

    private void initializeMovement() {
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        stop_and_reset_encoders_all();

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
        //waitForStart();
    }
    private void stop_and_reset_encoders_all() {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    private void run_to_position_all() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void powerZero() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    private void move(double movePower, String moveDirection, double moveDistance){
        stop_and_reset_encoders_all(); //Sets encoder count to 0
        if (moveDirection.equals("forward")) {
            backLeft.setTargetPosition((int) inch_convert(moveDistance));
            backRight.setTargetPosition((int) inch_convert(moveDistance));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance));
            frontRight.setTargetPosition((int) inch_convert(moveDistance));
            run_to_position_all();
            telemetry.addData("Power", movePower);
            telemetry.update();
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else if (moveDirection.equals("backward")) {
            backLeft.setTargetPosition((int) inch_convert(-moveDistance));
            backRight.setTargetPosition((int) inch_convert(-moveDistance));
            frontLeft.setTargetPosition((int) inch_convert(-moveDistance));
            frontRight.setTargetPosition((int) inch_convert(-moveDistance));
            run_to_position_all();
            backLeft.setPower(-movePower);
            backRight.setPower(-movePower);
            frontLeft.setPower(-movePower);
            frontRight.setPower(-movePower);
        } else if (moveDirection.equals("right")) {
            backLeft.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            backRight.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            frontRight.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            run_to_position_all();
            backLeft.setPower(-movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(-movePower);
        } else if (moveDirection.equals("left")) {
            backLeft.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            backRight.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(-moveDistance*strafeMult));
            frontRight.setTargetPosition((int) inch_convert(moveDistance*strafeMult));
            run_to_position_all();
            backLeft.setPower(movePower);
            backRight.setPower(-movePower);
            frontLeft.setPower(-movePower);
            frontRight.setPower(movePower);
        } else {
            telemetry.addData("Error", "move direction must be forward,backward,left, or right.");
            telemetry.update();
            terminateOpModeNow();
        }
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("test", "attempting to move...");
            telemetry.addData("power back right", backRight.getPower());
            telemetry.addData("power back left", backLeft.getPower());
            telemetry.addData("power front right", frontRight.getPower());
            telemetry.addData("power front left", frontLeft.getPower());
            telemetry.update();
            sleep(10);
        }
        powerZero();
        telemetry.addData("test", "done!");
        telemetry.update();
    }
    private void rotate(double angle, double power) {
        double Kp = 0.5; //this is for proportional control (ie. the closer you are the target angle the slower you will go)
        double startAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double targetAngle = startAngle + angle;
        double error = (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
        double power1 = 0;
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // rotate until the target angle is reached
        //System.out.printf("%f start angle = ",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        //System.out.printf("%f error = ", error);
        while (opModeIsActive() && Math.abs(error) > 5) {
            //powerZero();
            error = AngleUnit.normalizeDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            // the closer the robot is to the target angle, the slower it rotates
            //power = Range.clip(Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle) / 90, 0.1, 0.5);
            power1 = Range.clip((power*(error*Kp)),-0.5,0.5); //"Range.clip(value, minium, maxium)" takes the first term and puts it in range of the min and max provided
            telemetry.addData("power",power1);
            System.out.printf("%f power = ",power1);
            telemetry.addData("error",error);

            backLeft.setPower(power1);
            backRight.setPower(-power1);
            frontLeft.setPower(power1);
            frontRight.setPower(-power1);
            if (Math.abs(error) <= 5) {
                powerZero();
                break;
            }
            telemetry.addData("angle", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("target", targetAngle);
            telemetry.update();
            //double angleDifference = Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            //rotate(angleDifference, power);
        }
        powerZero();
        telemetry.update();
    }
    public void moveToAprilTag(backBoardAprilTags tagName) {
        //outputInfo = new double[] {};
        //initCam();
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        while (currentDetections.size() <= 0) {telemetry.addData("%f",currentDetections.size());telemetry.update();sleep(10);}
        boolean foundAprilTag = false;
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.metadata.name.equals(tagName.toString())) {
                    robotDistanceToAprilTag[0] = detection.ftcPose.x + cameraOffset[0];
                    robotDistanceToAprilTag[1] = detection.ftcPose.y + cameraOffset[1];
                    foundAprilTag = true;
                }
            } else { //add to move on to next step or align to a position
                break;
            }
        }
        //opmode.telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",robotDistanceToAprilTag[0],robotDistanceToAprilTag[1]));
        /*if (foundAprilTag) {
            move(0.5,"right",robotDistanceToAprilTag[0]);
            rotate(180,0.5);
            move(0.5,"backward",(robotDistanceToAprilTag[1]-20));
        }*/
        moveDistance = new double[] {robotDistanceToAprilTag[0],(robotDistanceToAprilTag[1] - moveOffsetY)};
    }

    //Claw funcs
    public void initializeOuttake(){
        intake = hardwareMap.dcMotor.get("intake");  /** Port: ExpansionHub MotorPort 1 **/
        wristLeft = hardwareMap.servo.get("wristLeft"); /** Port: ExpansionHub ServoPort 4 **/
        wristRight = hardwareMap.servo.get("wristRight");
        wristLeft.setDirection(Servo.Direction.REVERSE);
    }
    public void outtake(double targetPos){
        wristPos = Range.clip(targetPos,minWristPos,maxWristPos);
        wristLeft.setPosition(targetPos);
        wristRight.setPosition(targetPos);
    }
    public void initCam(){
        aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }
    public void camOn() {
        visionPortal.resumeStreaming();
    }
    public void camOff() {
        visionPortal.stopStreaming();
    }


    //Run Op Mode
    public void runOpMode(){
        initializeIntake();
        initializeMovement();
        initializeOuttake();
        //backBoardDetection.initCam();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        gameObjectDetection = new cameraDetectColorTest1();
        camera.setPipeline(gameObjectDetection);
        //backBoardDetection.initCam();

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
        //initCam();

        while(!isStarted()){
            spikeLocation = gameObjectDetection.getPosition().toString();
            telemetry.addData("Test", "working");
            telemetry.addData("Location", spikeLocation);
            telemetry.update();
        }
        waitForStart();
        if(opModeIsActive()) {
            camera.stopRecordingPipeline();
            camera.stopStreaming();
            initCam();
            camOn();
            sleep(1000);
            placeOnSpikeMark();
            //moveDistance = backBoardDetection.outputInfo;
            telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",moveDistance[0],moveDistance[1]));
            telemetry.update();
            move(0.3,"right",moveDistance[0]);
            rotate(180,0.3);
            move(0.3,"backward",moveDistance[1]);
            //parkCloseBlue();
        }
    }
}
