package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumChassisConvertCmm {
    private LinearOpMode opmode = null;
    private ElapsedTime runtime = new ElapsedTime();


    private DcMotor leftFrontWheel = null;
    private DcMotor rightFrontWheel = null;
    private DcMotor leftBackWheel = null;
    private DcMotor rightBackWheel = null;

    private IMU imu = null;


    //CONSTANTS
    private static final double WHEEL_DIAMETER = 9.6012; //This is in cm. Team needs to decide on units.
    static final double TICKS_PER_ROTATION = 537.6; //This is for the GoBilda motors.\\
    private static final double TICKS_PER_INCH = 55.99300087;
    //******* TODO: My want to add other constants like TICKS_PER_INCH?

    //Enumeration type used to specify direction when sending a move command.
    public enum Direction {
        FORWARD,
        FORWARD_LEFT,
        FORWARD_RIGHT,
        BACKWARD,
        BACK_LEFT,
        BACK_RIGHT,
        STRAFE,
        STRAFE_LEFT,
        STRAFE_RIGHT,

//        FORWARD_LEFT,
//        FORWARD_RIGHT,
//        BACK_LEFT,
//        BACK_RIGHT
    }
    public void initialize(HardwareMap hwMap){

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontWheel = hwMap.get(DcMotor.class, "frontLeft");
        rightFrontWheel = hwMap.get(DcMotor.class,  "frontRight");
        leftBackWheel = hwMap.get(DcMotor.class,  "backLeft");
        rightBackWheel = hwMap.get(DcMotor.class,  "backRight");
        //******* TODO: Initialize the other wheels


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.

        // Forwards
        leftFrontWheel.setDirection(DcMotor.Direction.FORWARD);
        rightFrontWheel.setDirection(DcMotor.Direction.FORWARD);
        leftBackWheel.setDirection(DcMotor.Direction.FORWARD);
        rightBackWheel.setDirection(DcMotor.Direction.FORWARD);

        // Backwards
        leftFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        rightFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        leftBackWheel.setDirection(DcMotor.Direction.REVERSE);
        rightBackWheel.setDirection(DcMotor.Direction.REVERSE);

        //Turning Right
        leftFrontWheel.setDirection(DcMotor.Direction.FORWARD);
        rightFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        leftBackWheel.setDirection(DcMotor.Direction.FORWARD);
        rightBackWheel.setDirection(DcMotor.Direction.REVERSE);

        //Turning Left
        leftFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        rightFrontWheel.setDirection(DcMotor.Direction.FORWARD);
        leftBackWheel.setDirection(DcMotor.Direction.REVERSE);
        rightBackWheel.setDirection(DcMotor.Direction.FORWARD);

        //Strafe Right
        leftFrontWheel.setDirection(DcMotor.Direction.FORWARD);
        rightFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        leftBackWheel.setDirection(DcMotor.Direction.REVERSE);
        rightBackWheel.setDirection(DcMotor.Direction.FORWARD);

        //Strafe Left
        leftFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        rightFrontWheel.setDirection(DcMotor.Direction.FORWARD);
        leftBackWheel.setDirection(DcMotor.Direction.FORWARD);
        rightBackWheel.setDirection(DcMotor.Direction.REVERSE);

        //******* TODO: Set direction for other motor

        //Make sure motors are stopped an reset encoder.
        leftFrontWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set Motors brake mode(Zero Behavior Mode)
        leftFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //******* TODO: Set motor zero behavior mode

        //Set motors run mode (could be RUN_USING_ENCODER or RUN_WITHOUT_ENCODER)
        leftFrontWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //******* TODO: Set motor run mode.

        //Initialize IMU: The IMU provides heading/direction of the robot. It can be used to turn
        // the robot and also could be used to help keep the robot straight when moving forward or strafing.
        //****** TODO: See SensorIMUOrthoganal.java sample code lines 108-115
    }

    /**
     * This a method to drive omni-wheels. See sample code BasicOmniOpMode_Linear.
     * @param axial: power forward/backwards
     * @param lateral: power left/right (Note: positive power is left)
     * @param yaw: rotation. CCW is positive
     */
    public void drive(double axial, double lateral, double yaw){

        // Combine the power for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontWheel  = axial + lateral + yaw;
        //*****TODO: See BasicOmniOpMode_Linear example. Also check out GMO https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html


        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        //****TODO: Same reference above. See code lines 127-136 on BasicOmniOpMode_Linear

        // Send calculated power to wheels
        //****TODO: See code lines 156-159

        // Good Idea to use telemetry to display data on drive hub for debugging.
        //****TODO: See code lines 162-165
    }

    /**
     * This method implements FOV drive method described in GM 0 reference:
     * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
     * @param axial
     * @param lateral
     * @param yaw
     */
    public void driveFOV(double axial, double lateral, double yaw){

        //*****TODO: Look at sample code in GM O and implement.
    }

    /**
     * This method uses RUN_TO_POSITION mode to drive the robot a the distance specified. The OpMode
     * that calls this method will need to use the isBusy() method to check when motors are done
     * executing the move
     * @param direction
     * @param distance
     * @param power
     */
    public void driveEncoder(Direction direction, double distance, double power) {

        //TODO: create a variable for each motor to hold new target position
        int newleftFrontTarget = 0;


        //TODO:Need to convert the distance to number of ticks. This involves math and using the circumference of the
        // wheel to calculate how many rotations and then muliplying that by how many encoder counters per rotation


        //TODO: Determine new target position for each motor. Will need to read the current position from
        // each motor and then add/subtract (see graphic on motor directions in GM 0 or GoBilda)
        switch (direction){
            case FORWARD:
                newleftFrontTarget = leftFrontWheel.getCurrentPosition()+ (int)distance;
                //TODO: determine new target position for each wheel
                break;
            case STRAFE:
                //TODO: calculate new target positions for each wheel when strafing
                break;
        }

        //Set new target positions for each motor
        leftFrontWheel.setTargetPosition(newleftFrontTarget);

        //Turn on RUN_TO_POSITION for each motor
        leftFrontWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Set Power for each wheel. Note power should be positive when using run to position. The motor will know which
        //direction based on encoder count.
        leftFrontWheel.setPower(power);
    }

    /**
     * Method checks to see if motors are still busy executing a move.
     * @return
     */
    public boolean isBusy(){

        //TODO: ADD other wheels to code or you could just check one motor
        return(leftFrontWheel.isBusy());
    }

    /**
     * Method turns the robot to a heading.
     * @param heading
     * @param power
     */
    public void turnToHeading(double heading, double power){

        //TODO: Write code to turn robot using the IMU

    }
}
