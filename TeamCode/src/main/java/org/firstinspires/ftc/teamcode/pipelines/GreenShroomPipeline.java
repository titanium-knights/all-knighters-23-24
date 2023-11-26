package org.firstinspires.ftc.teamcode.pipelines;

// Adapted from EasyOpenCV-Sim capstoneDetectionPipeline


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class GreenShroomPipeline extends OpenCvPipeline {
    /*
     * An enum to define the capstone position
     */
    public enum CapstonePosition {
        LEFT,
        CENTER,
        RIGHT
    }

    /*
     * Some color constants
     */
    public final Scalar BLUE = new Scalar(0, 0, 255);
    public final Scalar GREEN = new Scalar(0, 255, 0);

    // values based on resolution of camera
    public static double X_POSITION_LEFT = 80;
    public static double X_POSITION_CENTER = 515;
    public static double X_POSITION_RIGHT = 920;
    public static double Y_POSITION = 270;

    public static double Y_POSITION_OFFSET = 50;

    /*
     * The core values which define the location and size of the sample regions
     */
    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(X_POSITION_LEFT, Y_POSITION);
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(X_POSITION_CENTER, Y_POSITION);
    static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(X_POSITION_RIGHT, Y_POSITION);

    // values based on resolution of camera
    static final int REGION_WIDTH = 40 * 1280 / 360 * 2;
    static final int REGION_HEIGHT = 70 * 720 / 240;

    /*
     * Points which actually define the sample region rectangles, derived from above
     * values
     *
     * Example of how points A and B work to define a rectangle
     *
     * ------------------------------------
     * | (0,0) Point A |
     * | |
     * | |
     * | |
     * | |
     * | |
     * | |
     * | Point B (70,50) |
     * ------------------------------------
     *
     */
    Point region1_pointA = new Point(
            X_POSITION_LEFT,
            Y_POSITION + Y_POSITION_OFFSET);
    Point region1_pointB = new Point(
            X_POSITION_LEFT + REGION_WIDTH,
            Y_POSITION + REGION_HEIGHT + Y_POSITION_OFFSET);
    Point region2_pointA = new Point(
            X_POSITION_CENTER,
            Y_POSITION);
    Point region2_pointB = new Point(
            X_POSITION_CENTER + REGION_WIDTH,
            Y_POSITION + REGION_HEIGHT);
    Point region3_pointA = new Point(
            X_POSITION_RIGHT,
            Y_POSITION + Y_POSITION_OFFSET);
    Point region3_pointB = new Point(
            X_POSITION_RIGHT + REGION_WIDTH,
            Y_POSITION + REGION_HEIGHT + Y_POSITION_OFFSET);

    /*
     * Working variables
     */
    Mat region1_Cb, region2_Cb, region3_Cb;
    Mat region1_Cr, region2_Cr, region3_Cr;
    Mat YCrCb = new Mat();
    Mat Cb = new Mat();
    Mat Cr = new Mat();
    int avg1, avg2, avg3;

    // Volatile since accessed by OpMode thread w/o synchronization
    private volatile CapstonePosition position = CapstonePosition.LEFT;

    public boolean updateTelemetry = true;

    private Telemetry telemetry;

    public GreenShroomPipeline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    /*
     * This function takes the RGB frame, converts to YCrCb,
     * and extracts the Cb channel to the 'Cb' variable
     */
    void inputToCb(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cr, 1);
        Core.extractChannel(YCrCb, Cb, 2);
    }

    @Override
    public void init(Mat firstFrame) {
        /*
         * We need to call this in order to make sure the 'Cb'
         * object is initialized, so that the submats we make
         * will still be linked to it on subsequent frames. (If
         * the object were to only be initialized in processFrame,
         * then the submats would become delinked because the backing
         * buffer would be re-allocated the first time a real frame
         * was crunched)
         */
        inputToCb(firstFrame);

        /*
         * Submats are a persistent reference to a region of the parent
         * buffer. Any changes to the child affect the parent, and the
         * reverse also holds true.
         */
        region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        region2_Cb = Cb.submat(new Rect(region2_pointA, region2_pointB));
        region3_Cb = Cb.submat(new Rect(region3_pointA, region3_pointB));

        region1_Cr = Cr.submat(new Rect(region1_pointA, region1_pointB));
        region2_Cr = Cr.submat(new Rect(region2_pointA, region2_pointB));
        region3_Cr = Cr.submat(new Rect(region3_pointA, region3_pointB));
    }

    @Override
    public Mat processFrame(Mat input) {
        /*
         * Overview of what we're doing:
         *
         * Out of the 3 regions, take the one with the minimum Cr + Cb, since the capstone is green.
         */

        /*
         * Get the Cb channel of the input frame after conversion to YCrCb
         */
        inputToCb(input);

        /*
         * Compute the average pixel value of each submat region. We're
         * taking the average of a single channel buffer, so the value
         * we need is at index 0. We could have also taken the average
         * pixel value of the 3-channel image, and referenced the value
         * at index 2 here.
         */
        avg1 = (int) Math.abs((Core.mean(region1_Cb).val[0] - Core.mean(region1_Cr).val[0]));
        avg2 = (int) Math.abs((Core.mean(region2_Cb).val[0] - Core.mean(region2_Cr).val[0]));
        avg3 = (int) Math.abs((Core.mean(region3_Cb).val[0] - Core.mean(region3_Cr).val[0]));

        /*
         * Draw a rectangle showing sample region 1 on the screen.
         * Simply a visual aid. Serves no functional purpose.
         */
        Imgproc.rectangle(
                input, // Buffer to draw on
                region1_pointA, // First point which defines the rectangle
                region1_pointB, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                2); // Thickness of the rectangle lines

        /*
         * Draw a rectangle showing sample region 2 on the screen.
         * Simply a visual aid. Serves no functional purpose.
         */
        Imgproc.rectangle(
                input, // Buffer to draw on
                region2_pointA, // First point which defines the rectangle
                region2_pointB, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                2); // Thickness of the rectangle lines

        /*
         * Draw a rectangle showing sample region 3 on the screen.
         * Simply a visual aid. Serves no functional purpose.
         */
        Imgproc.rectangle(
                input, // Buffer to draw on
                region3_pointA, // First point which defines the rectangle
                region3_pointB, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                2); // Thickness of the rectangle lines

        /*
         * Find the max of the 3 averages
         */
        int maxOneTwo = Math.max(avg1, avg2);
        int max = Math.max(maxOneTwo, avg3);

        /*
         * Now that we found the max, we actually need to go and
         * figure out which sample region that value was from
         */
        if (max == avg1) // Was it from region 1?
        {
            position = CapstonePosition.LEFT; // Record our analysis

            /*
             * Draw a solid rectangle on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region1_pointA, // First point which defines the rectangle
                    region1_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill
        } else if (max == avg2) // Was it from region 2?
        {
            position = CapstonePosition.CENTER; // Record our analysis

            /*
             * Draw a solid rectangle on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region2_pointA, // First point which defines the rectangle
                    region2_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill
        } else if (max == avg3) // Was it from region 3?
        {
            position = CapstonePosition.RIGHT; // Record our analysis

            /*
             * Draw a solid rectangle on top of the chosen region.
             * Simply a visual aid. Serves no functional purpose.
             */
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region3_pointA, // First point which defines the rectangle
                    region3_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill
        }

        if (updateTelemetry) {
            telemetry.addData("[Pattern]", position);
            telemetry.addData("avg1: ", avg1);
            telemetry.addData("avg2: ", avg2);
            telemetry.addData("avg3: ", avg3);
//            telemetry.addData("avg1cb: ", Core.mean(region1_Cb).val[0]);
//            telemetry.addData("avg1cr: ", Core.mean(region1_Cr).val[0]);
//            telemetry.addData("avg2cb: ", Core.mean(region2_Cb).val[0]);
//            telemetry.addData("avg2cr: ", Core.mean(region2_Cr).val[0]);
//            telemetry.addData("avg3cb: ", Core.mean(region3_Cb).val[0]);
//            telemetry.addData("avg3cr: ", Core.mean(region3_Cr).val[0]);
            telemetry.update();
        }

        /*
         * Render the 'input' buffer to the viewport. But note this is not
         * simply rendering the raw camera feed, because we called functions
         * to add some annotations to this buffer earlier up.
         */
        return input;
    }

    /*
     * Call this from the OpMode thread to obtain the latest analysis
     */
    public CapstonePosition getAnalysis() {
        return position;
    }
}
