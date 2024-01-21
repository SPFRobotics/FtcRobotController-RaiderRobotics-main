package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class PIDcontroller {
  // Parameters you define when creating the object, DONT'T TOUCH "dT" UNLESS YOU KNOW WHAT YOU ARE DOING
  public LinearOpMode opmode;
  public double kP;
  public double kI;
  public double kD;
  public double targetPoint;
  /** For fine tuning P, I, and D, read link that is below **/
  // Eli needs to add link, so yell at him
  public long dT = 20; //(Units: milliSeconds) time between cycles

  // Predefined constants, also don't touch
  private double maxIntegral = 50000000;
  private double maxErrorReset = 10000;
  private double minErrorReset = 0;

  // Changing variables
  private double error = 0;
  private double prevError = 0;
  private double integral = 0;
  private double derivative = 0;
  private double power = 0;

  public PIDcontroller(LinearOpMode lom, double targetVal, double kPval, double kIval, double kDval) {
    opmode = lom;
    targetPoint = targetVal;
    kP = kPval;
    kI = kIval;
    kD = kDval;
    dT = 20;
  }
  public PIDcontroller(LinearOpMode lom, double targetVal, double kPval, double kIval, double kDval, long dTval) {
    opmode = lom;
    targetPoint = targetVal;
    kP = kPval;
    kI = kIval;
    kD = kDval;
    dT = dTval;
  }

  public double controller(double sensorVal, double powerMax) {
    error = targetPoint - sensorVal;
    integral = integral + error;
    if (Math.abs(error) > maxErrorReset) {
      integral = 0;
    }
    if (Math.abs(error) < minErrorReset) {
      integral = 0;
    }
    derivative = error - prevError;
    prevError = error;
    power = error*kP + integral*kI + derivative*kD;
    power = Range.clip(power,-powerMax,powerMax);
    opmode.telemetry.addData("error: ", error);
    opmode.telemetry.addData("integral: ", integral);
    opmode.telemetry.addData("derivative: ", derivative);
    opmode.telemetry.addData("power: ", power);
    //return power;
    opmode.sleep(dT);
    return power;
  }
  public double controller(double sensorVal, double powerMax, boolean useIntegralMax) {
    error = targetPoint - sensorVal;
    integral = integral + error;
    if (useIntegralMax) {
      integral = Range.clip(integral,-maxIntegral,maxIntegral);
    }
    derivative = error - prevError;
    prevError = error;
    power = error*kP + integral*kI + derivative*kD;
    power = Range.clip(power,-powerMax,powerMax);
    opmode.telemetry.addData("error: ", error);
    opmode.telemetry.addData("integral: ", integral);
    opmode.telemetry.addData("derivative: ", derivative);
    opmode.telemetry.addData("power: ", power);
    //return power;
    opmode.sleep(dT);
    return power;
  }
}
