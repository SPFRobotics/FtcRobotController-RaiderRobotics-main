package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

@Autonomous
public class aprilTagPositionDetection extends LinearOpMode {
    //public LinearOpMode opmode = null;
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;
    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public IMU imu = null;
    public static final double strafeMult = 1.2;


    public enum aprilTags {
        RedAllianceLeft,
        RedAllianceCenter,
        RedAllianceRight,
        BlueAllianceLeft,
        BlueAllianceCenter,
        BlueAllianceRight,
        RedAudienceWallSmall,
        RedAudienceWallLarge,
        BlueAudienceWallSmall,
        BlueAudienceWallLarge
    }
    Dictionary<aprilTags, Integer> aprilTagsDict = new Hashtable<>();
    Dictionary<aprilTags, double[]> aprilTagsPosDict = new Hashtable<>();
    public final static double[] fieldSize = new double[] {144,144};
    public final static double[] redAprilTagSmallPos = new double[] {34.5,144.5,4};
    //public final static double[] redAprilTagBigPos = new double[] {-5.5,0,1.5};
    public final static double[] redAprilTagBigPos = new double[] {29,0,5.5};
    public final static double[] blueAprilTagSmallPos = new double[] {109.5,144.5,4};
    //public final static double[] blueAprilTagBigPos = new double[] {5.5,0,1.5};
    public final static double[] blueAprilTagBigPos = new double[] {115,0,5.5};
    public final static double[] cameraOffset = new double[] {3.5,5.5}; // x offset (left: positive, right: negative), y(distance) offset; (units: inches from center)
    //double[] robotDistanceToAprilTag = new double[] {0,0};
    double[] robotFieldPos = new double[] {0,0};
    double[][] robotDistancesToAprilTags = new double[][] {{0,0},{0,0},{0,0},{0,0}}; // [0][n]: RedAudienceWallLarge, [1][n]: BlueAudienceWallLarge, [2][n]: RedAudienceWallSmall, [3][n]: BlueAudienceWallSmall
    double[][] calculationAprilTagsDistances = new double[][] {{0,0},{0,0},{0,0},{0,0}}; // [0][n]: RedAudienceWallLarge, [1][n]: BlueAudienceWallLarge, [2][n]: RedAudienceWallSmall, [3][n]: BlueAudienceWallSmall

    @Override
    public void runOpMode(){
        initCam();
        aprilTagsDict.put(aprilTags.RedAudienceWallLarge,0);
        aprilTagsDict.put(aprilTags.BlueAudienceWallLarge,1);
        aprilTagsDict.put(aprilTags.RedAudienceWallSmall,2);
        aprilTagsDict.put(aprilTags.BlueAudienceWallSmall,3);
        aprilTagsPosDict.put(aprilTags.RedAudienceWallLarge,redAprilTagBigPos);
        aprilTagsPosDict.put(aprilTags.BlueAudienceWallLarge,blueAprilTagBigPos);
        aprilTagsPosDict.put(aprilTags.RedAudienceWallSmall,redAprilTagSmallPos);
        aprilTagsPosDict.put(aprilTags.BlueAudienceWallSmall,blueAprilTagSmallPos);
        while (!isStarted()) {
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            telemetry.addData("%f",currentDetections.size());
            telemetry.update();
            //wait(1);
        }
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                //telemetryAprilTag();
                aprilTags[] aprilTagsLooking = new aprilTags[] {aprilTags.BlueAudienceWallLarge,aprilTags.RedAudienceWallLarge};
                getRobotPosAprilTag(aprilTagsLooking);

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }
    }
    public void initCam(){
        aprilTag = new AprilTagProcessor.Builder().build();
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();

        frontLeft = hardwareMap.dcMotor.get("frontLeft"); /** Port: ControlHub MotorPort 0 **/
        backLeft = hardwareMap.dcMotor.get("backLeft"); /** Port: ControlHub MotorPort 2 **/
        frontRight = hardwareMap.dcMotor.get("frontRight"); /** Port: ControlHub MotorPort 1 **/
        backRight = hardwareMap.dcMotor.get("backRight"); /** Port: ControlHub MotorPort 3 **/
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
    }
    public void getRobotPosAprilTag(aprilTags[] tagNames) {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        List<aprilTags> foundAprilTags = null;
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                for (aprilTags tagName : tagNames) {
                    if (detection.metadata.name == tagName.toString()) {
                        int arrayNum = aprilTagsDict.get(tagName);
                        robotDistancesToAprilTags[arrayNum][0] = detection.ftcPose.x + cameraOffset[0];
                        robotDistancesToAprilTags[arrayNum][1] = detection.ftcPose.y + cameraOffset[1];
                        foundAprilTags.add(tagName);
                    }
                }
            } else { //add to move on to next step or align to a position
                break;
            }
        }
        //telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",robotDistancesToAprilTags[0],robotDistancesToAprilTags[1]));
        if (foundAprilTags.size() > 0) {
            for (aprilTags tagName : foundAprilTags) {
                int arrayNum1 = aprilTagsDict.get(tagName);
                calculationAprilTagsDistances[arrayNum1][0] = aprilTagsPosDict.get(tagName)[0] - robotDistancesToAprilTags[arrayNum1][0];
                calculationAprilTagsDistances[arrayNum1][1] = aprilTagsPosDict.get(tagName)[1] - robotDistancesToAprilTags[arrayNum1][1];
            }
            double xPosSum = 0;
            double yPosSum = 0;
            for (aprilTags tagName : foundAprilTags) {
                int arrayNum1 = aprilTagsDict.get(tagName);
                xPosSum += calculationAprilTagsDistances[arrayNum1][0];
                yPosSum += calculationAprilTagsDistances[arrayNum1][1];
            }
            robotFieldPos[0] = xPosSum / foundAprilTags.size();
            robotFieldPos[1] = yPosSum / foundAprilTags.size();
            telemetry.addLine(String.format("XY %6.1f %6.1f  (inch)",robotFieldPos[0],robotFieldPos[1]));
        }
    }
    public void stop_and_reset_encoders_all() {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void run_to_position_all() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void powerZero() {
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
    }
    public double inch_convert(double inch) { return inch * (537.7 / (3.78 * Math.PI)); }
    public void move(double movePower, String moveDirection, double moveDistance) {
        stop_and_reset_encoders_all(); //Sets encoder count to 0
        if (moveDirection.equals("forward")) {
            backLeft.setTargetPosition((int) inch_convert(moveDistance));
            backRight.setTargetPosition((int) inch_convert(moveDistance));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance));
            frontRight.setTargetPosition((int) inch_convert(moveDistance));
            run_to_position_all();
            //telemetry.addData("Power", movePower);
            //telemetry.update();
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
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else if (moveDirection.equals("right")) {
            backLeft.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            backRight.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            frontRight.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            run_to_position_all();
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else if (moveDirection.equals("left")) {
            backLeft.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            backRight.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            frontLeft.setTargetPosition((int) inch_convert(-moveDistance * strafeMult));
            frontRight.setTargetPosition((int) inch_convert(moveDistance * strafeMult));
            run_to_position_all();
            backLeft.setPower(movePower);
            backRight.setPower(movePower);
            frontLeft.setPower(movePower);
            frontRight.setPower(movePower);
        } else {
            telemetry.addData("Error", "move direction must be forward,backward,left, or right.");
            //telemetry.update();
            terminateOpModeNow();
        }
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("test", "attempting to move...");
            //telemetry.addData("power back right", backRight.getPower());
            //telemetry.addData("power back left", backLeft.getPower());
            //telemetry.addData("power front right", frontRight.getPower());
            //telemetry.addData("power front left", frontLeft.getPower());
            telemetry.update();
        }
        powerZero();
    }
    public void rotate(double angle, double power) {
        double Kp = 0.5; //this is for porposanal control (ie. the closer you are the target angle the slower you will go)
        double startAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double targetAngle = startAngle + angle;
        double error = (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
        double power1 = power;
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // rotate until the target angle is reached
        System.out.printf("%f start angle = ",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        System.out.printf("%f error = ", error);
        while (opModeIsActive() && Math.abs(error) > 5) {
            //powerZero();
            error = AngleUnit.normalizeDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            // the closer the robot is to the target angle, the slower it rotates
            //power = Range.clip(Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle) / 90, 0.1, 0.5);
            power1 = power * error * Kp;
            power1 = Range.clip(power1,-0.5,0.5); //"Range.clip(value, minium, maxium)" takes the first term and puts it in range of the min and max provided
            telemetry.addData("power1",power1);
            //System.out.printf("%f power = ",power1);
            telemetry.addData("error",error);
            telemetry.addData("power", power);
            telemetry.addData("Kp",Kp);

            backLeft.setPower(power1);
            backRight.setPower(-power1);
            frontLeft.setPower(power1);
            frontRight.setPower(-power1);
            if (Math.abs(error) <= 1) {
                powerZero();
            }
            telemetry.addData("angle", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("target", targetAngle);
            telemetry.update();
            //double angleDifference = Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - targetAngle);
            //rotate(angleDifference, power);
        }
        powerZero();
    }
    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method telemetryAprilTag()
}