package org.firstinspires.ftc.teamcode.production.vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class DuckPipline extends OpenCvPipeline {

    private final Telemetry telemetry;
    private final Mat mat = new Mat();

    private DuckPipline.Position position;

    private final Rect LEFT_RECT = new Rect(
            new Point(120, 150),
            new Point(30, 60)
    );

    private final Rect MIDDLE_RECT = new Rect(
            new Point(210, 150),
            new Point(120, 60)
    );

    private final Rect RIGHT_RECT = new Rect(
            new Point(300, 150),
            new Point(210, 60)
    );

    private final Scalar TARGET_COLOR = new Scalar(0, 255, 0);
    private final Scalar INCORRECT_COLOR = new Scalar(255, 0, 0);

    public DuckPipline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        Scalar lowHSV = new Scalar(20, 100, 100);
        Scalar highHSV = new Scalar(30, 255, 255);

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat leftRect = mat.submat(LEFT_RECT);
        Mat middleRect = mat.submat(MIDDLE_RECT);
        Mat rightRect = mat.submat(RIGHT_RECT);

        double leftValue = Core.sumElems(leftRect).val[0] / LEFT_RECT.area();
        double middleValue = Core.sumElems(middleRect).val[0] / MIDDLE_RECT.area();
        double rightValue = Core.sumElems(rightRect).val[0] / RIGHT_RECT.area();

        leftRect.release();
        middleRect.release();
        rightRect.release();

        double max = Math.max(Math.max(leftValue, middleValue), rightValue);

        if (max == leftValue) {
            position = Position.LEFT;
        }
        else if (max == middleValue) {
            position = Position.MIDDLE;
        }
        else if (max == rightValue) {
            position = Position.RIGHT;
        }

        telemetry.addLine("Position Found: " + position);
        telemetry.update();

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Imgproc.rectangle(mat, LEFT_RECT, position == Position.LEFT ? TARGET_COLOR : INCORRECT_COLOR);
        Imgproc.rectangle(mat, MIDDLE_RECT, position == Position.MIDDLE ? TARGET_COLOR : INCORRECT_COLOR);
        Imgproc.rectangle(mat, RIGHT_RECT, position == Position.RIGHT ? TARGET_COLOR : INCORRECT_COLOR);

        return mat;
    }

    public DuckPipline.Position getPosition() {
        return position;
    }

    enum Position {
        LEFT,
        MIDDLE,
        RIGHT
    }
}
