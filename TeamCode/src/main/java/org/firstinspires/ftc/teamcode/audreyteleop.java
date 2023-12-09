package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "audreyteleop (Blocks to Java)")
public class audreyteleop extends LinearOpMode {

  private DcMotor backLeft;
  private DcMotor frontLeft;
  private DcMotor backRight;
  private DcMotor frontRight;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    float y;
    double x;
    float rx;
    double denominator;

    backLeft = hardwareMap.get(DcMotor.class, "backLeft");
    frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
    backRight = hardwareMap.get(DcMotor.class, "backRight");
    frontRight = hardwareMap.get(DcMotor.class, "frontRight");

    backLeft.setDirection(DcMotor.Direction.REVERSE);
    frontLeft.setDirection(DcMotor.Direction.REVERSE);
    waitForStart();
    while (opModeIsActive()) {
      // Reverse left side
      // Y stick value is reversed
      y = -gamepad1.left_stick_y;
      x = gamepad1.left_stick_x * 1.1;
      rx = gamepad1.right_stick_x;
      // All motors are the same ratio
      denominator = JavaUtil.maxOfList(JavaUtil.createListWith(JavaUtil.sumOfList(JavaUtil.createListWith(Math.abs(y), Math.abs(x), Math.abs(rx))), 1, null));
      backLeft.setPower((y + x + rx) / denominator);
      backRight.setPower(((y - x) + rx) / denominator);
      frontLeft.setPower(((y - x) - rx) / denominator);
      frontRight.setPower(((y + x) - rx) / denominator);
    }
  }
}
