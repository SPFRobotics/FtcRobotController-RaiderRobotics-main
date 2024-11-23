package org.firstinspires.ftc.teamcode.OpModes.Auto;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SpecimenDetection implements VisionProcessor {
    @Override
    public void init(int width, int height, CameraCalibration calibration){}

    public enum GameObjectLocation{
        LEFT,
        RIGHT,
        CENTER,
        NONE
    }

    private static Point Specimen_BoundingBox_TopLeft_AnchorPoint = new Point(0, 240);
    private static int BoundingBox_Width = 640;
    private static int BoundingBox_Height = 160;
    private static int LeftLineLocation = 120;
    private static int CenterLeftLineLocation = 240;
    private static int CenterRightLineLocation = 400;
    private static int RightLineLocation = 520;

    private static final Scalar
        lower_red_bounds = new Scalar(100,0,0,0),
        upper_red_bounds = new Scalar(255,70,70,255),
        lower_blue_bounds = new Scalar(0,0,100,0),
        upper_blue_bounds = new Scalar(100,00,255,255);

    private final Scalar
        RED = new Scalar(255,0,0),
        BLUE = new Scalar(0,0,255),
        BLACK = new Scalar(0,0,0);
    private Scalar color = BLACK;

    public double minSectorPercent = 1440, minTotalPercent = 1040;
    public double maxPercent = 0, highestSector = 0;
    public double redPercent = 0, bluePercent = 0;

    private Mat redMat = new Mat(), blueMat = new Mat(), blurredMat = new Mat();
    private double redPercentLeft, bluePercentLeft, leftPercent = 0;

    private Mat redMatLeft = new Mat(), blueMatLeft = new Mat(), blurredMatLeft = new Mat();
    private double redPercentCenter, bluePercentCenter, centerPercent = 0;

    private Mat redMatCenter = new Mat(), blueMatCenter = new Mat(), blurredMatCenter = new Mat();

    public double redPercentRight, bluePercentRight, rightPercent = 0;

    private Mat redMatRight = new Mat(), blueMatRight = new Mat(), blurredMatRight = new Mat();
    //creates our boxes :)
    Point SpecimenPointA = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y
    );
    Point SpecimenPointB = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x + BoundingBox_Width,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y + BoundingBox_Height
    );
    Point SpecimenPointALeft = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y
    );
    Point SpecimenPointBLeft = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x + LeftLineLocation,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y + BoundingBox_Height
    );
    Point SpecimenPointACenter = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x + CenterLeftLineLocation,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y
    );
    Point SpecimenPointBCenter = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x + CenterLeftLineLocation,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y + BoundingBox_Height
    );
    Point SpecimenPointARight = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x + RightLineLocation,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y
    );
    Point SpecimenPointBRight = new Point(
            Specimen_BoundingBox_TopLeft_AnchorPoint.x + BoundingBox_Width,
            Specimen_BoundingBox_TopLeft_AnchorPoint.y + BoundingBox_Height
    );

    private volatile GameObjectLocation position = GameObjectLocation.NONE;

    @Override
    public Object processFrame(Mat input, long captureTimeNanos){
        //this defines our mats and WHERE they are! :D
        Imgproc.blur(input, blurredMat, new Size(5, 5));
        Imgproc.blur(input, blurredMatLeft, new Size(5,5));
        Imgproc.blur(input, blurredMatCenter, new Size(5,5));
        Imgproc.blur(input, blurredMatRight, new Size(5,5));
        blurredMat = blurredMat.submat(new Rect(SpecimenPointA, SpecimenPointB));
        blurredMatLeft = blurredMatLeft.submat(new Rect(SpecimenPointALeft, SpecimenPointBLeft));
        blurredMatCenter = blurredMatCenter.submat(new Rect(SpecimenPointACenter, SpecimenPointBCenter));
        blurredMatRight.submat(new Rect(SpecimenPointARight, SpecimenPointBRight));

        //basically, the following smoothens out the image, getting rid of gaps!
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.morphologyEx(blurredMat, blurredMat, Imgproc.MORPH_CLOSE, kernel);
        Imgproc.morphologyEx(blurredMatLeft, blurredMatLeft, Imgproc.MORPH_CLOSE, kernel);
        Imgproc.morphologyEx(blurredMatCenter, blurredMatCenter, Imgproc.MORPH_CLOSE, kernel);
        Imgproc.morphologyEx(blurredMatRight, blurredMatRight, Imgproc.MORPH_CLOSE, kernel);

        //this part searches each mat to find if any of the pixels remain in the range specified for each color!
        Core.inRange(blurredMat, lower_red_bounds, upper_red_bounds, redMat);
        Core.inRange(blurredMat, lower_blue_bounds, upper_blue_bounds, blueMat);
        Core.inRange(blurredMatLeft, lower_red_bounds, upper_red_bounds, redMat);
        Core.inRange(blurredMatLeft, lower_blue_bounds, upper_blue_bounds, blueMat);
        Core.inRange(blurredMatCenter, lower_red_bounds, upper_red_bounds, redMat);
        Core.inRange(blurredMatCenter, lower_blue_bounds, upper_blue_bounds, blueMat);
        Core.inRange(blurredMatRight, lower_red_bounds, upper_red_bounds, redMat);
        Core.inRange(blurredMatRight, lower_blue_bounds, upper_blue_bounds, blueMat);

        //if the pixel is filled (non-zero) it figures out what the color that pixel is
        redPercent = Core.countNonZero(redMat);
        bluePercent = Core.countNonZero(blueMat);
        redPercentLeft = Core.countNonZero(redMatLeft);
        bluePercentLeft = Core.countNonZero(blueMatLeft);
        leftPercent = redPercentLeft + bluePercentLeft;
        redPercentCenter = Core.countNonZero(redMatCenter);
        bluePercentCenter = Core.countNonZero(blueMatCenter);
        centerPercent = redPercentCenter + bluePercentCenter;
        redPercentRight = Core.countNonZero(redMatRight);
        bluePercentRight = Core.countNonZero(blueMatRight);
        rightPercent = redPercentRight + bluePercentRight;jgo
    }


}
