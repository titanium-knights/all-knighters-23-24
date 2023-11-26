package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;
import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.Slides;

@Autonomous(name="SlidesDumpAuton", group="Linear Opmode")
@Config
public class SlidesDumpAuton extends LinearOpMode {

    protected MecanumDrive drive;
    protected GreenShroomVision vision;
    protected Slides slides;
    protected PixelCarriage carriage;
    public static double POWER = .8;
    public static int PAUSE_TIME = 600;
    public static int BACKWARD_TIME = 550;
    public static int FORWARD_TIME = 300;


    public static int SLIDE_POS_UP = 400;
    public static int SLIDE_POS_DOWN = 50;
    public static double SLIDE_POW = .4;

    public static int STRAFE_TIME_LEFT = 425;
    public static int STRAFE_TIME_RIGHT = 400;

    public static int WAIT_VISION = 425;

    public static int BACKWARD_13_TIME = 400;
    public static int position;



    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();


    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);
        vision = new GreenShroomVision(hardwareMap, dashTelemetry);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
//        sleep(WAIT_VISION);
//        position = vision.getPosition();
//        telemetry.addData("Detected: ", position);
//        dashTelemetry.addData("Detected", position);
//        dashTelemetry.update();

        waitForStart();
        sleep(WAIT_VISION);
        position = vision.getPosition();
        telemetry.addData("Detected: ", position);
        dashTelemetry.addData("Detected", position);

        telemetry.addData("Slides 1 (right) Position", slides.getPositionR());
        dashTelemetry.addData("Slides (right) Position", slides.getPositionR());
        telemetry.addData("Slides 2 (left) Position", slides.getPositionL());
        dashTelemetry.addData("Slides 2 (left) Position", slides.getPositionL());

        telemetry.addData("Slides (average) Position", slides.getAverage());
        dashTelemetry.addData("Slides (average) Position", slides.getAverage());

        switch (position) {
            case 1:
                drive.strafeRightWithPower(POWER);
                sleep(STRAFE_TIME_LEFT);
                drive.stop();
                sleep(PAUSE_TIME);
                drive.backwardWithPower(POWER);
                sleep(BACKWARD_13_TIME);
                drive.stop();
                sleep(PAUSE_TIME);
                break;
            case 2:
                drive.backwardWithPower(POWER);
                sleep(BACKWARD_TIME);
                drive.stop();
                sleep(PAUSE_TIME);
                break;

            case 3:
                drive.strafeLeftWithPower(POWER);
                sleep(STRAFE_TIME_RIGHT);
                drive.stop();
                sleep(PAUSE_TIME);
                drive.backwardWithPower(POWER);
                sleep(BACKWARD_13_TIME);
                drive.stop();
                sleep(PAUSE_TIME);
                break;
            default:
                drive.backwardWithPower(POWER);
                sleep(BACKWARD_TIME);
                drive.stop();
                sleep(PAUSE_TIME);
                break;

        }


        //dumping
        slides.setPosition(SLIDE_POS_UP, SLIDE_POW);
        drive.stop();
        sleep(PAUSE_TIME);

        carriage.setPivotIntake(false); //faces outtake
        drive.stop();
        sleep(PAUSE_TIME);
        sleep(PAUSE_TIME);

        slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW);
        drive.stop();
        sleep(PAUSE_TIME);

        carriage.setCarriageOpen(true); //opens the carriage
        drive.stop();
        sleep(PAUSE_TIME);

        carriage.setCarriageOpen(true); //opens the carriage
        drive.stop();
        sleep(PAUSE_TIME * 2);

        carriage.setPivotIntake(false); //faces outtake
        drive.stop();
        sleep(PAUSE_TIME);

//        drive.forwardWithPower(POWER);
//        sleep(FORWARD_TIME);

        drive.stop();
        sleep(PAUSE_TIME);

    }



}
