package org.firstinspires.ftc.teamcode.auton;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.rr.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.rr.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.rr.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;
import org.firstinspires.ftc.teamcode.util.IntakeRoller;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.Slides;
import org.firstinspires.ftc.teamcode.util.SlidesTwoMotors;

@Autonomous(name = "PurpleDetectAuton", group = "Linear OpMode")
@Config

public class PurpleDetectAuton extends LinearOpMode{
    protected SampleMecanumDrive drive;
//    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;


    public static int SLIDE_POS_UP = -600;
    public static int SLIDE_POS_DOWN = -50;

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 1;

    public static double SLIDE_POW = .4;


    TrajectorySequence path;

    public static int START_Y = 0;
    public static int START_X = 0;

    public static int VISION_ANG_LEFT = 90;
    public static int VISION_ANG_CENTER = 0;
    public static int VISION_ANG_RIGHT = -90;

    public static int VISION_ANG; //actual angle
    public static Vector2d BEFORE_TURN = new Vector2d(START_X+28, START_Y);

    public static Vector2d TO_PARK = new Vector2d(28, START_Y+50);

    public static int position = 1;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    protected void setupDevices(){
        drive = new SampleMecanumDrive(hardwareMap);
       // vision = new GreenShroomVision(hardwareMap, null);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);
        intake = new IntakeRoller(hardwareMap);
    }

    public void initTraj() {
//        position = vision.getPosition();
        if (position == 1) {
            VISION_ANG = VISION_ANG_LEFT;
        } else if (position == 2) {
            VISION_ANG = VISION_ANG_CENTER;
        } else {
            VISION_ANG = VISION_ANG_RIGHT;
        }

        TrajectorySequenceBuilder analysis = drive.trajectorySequenceBuilder(new Pose2d(START_X, START_Y, 0))
                .lineToConstantHeading(BEFORE_TURN)
                .turn(Math.toRadians(VISION_ANG))
                .addTemporalMarker(() -> {
                    intake.intake(-INTAKE_POW);
                })
                .waitSeconds(INTAKE_TIME)
                .addTemporalMarker(() -> {
                    intake.intake(0); //stop intake
                })
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW);
                })
                .waitSeconds(0)
                .addTemporalMarker(()->{
//                    slides.setPosition(LIFT_LOWER_1, LIFT_POWER_DOWN);
//                    carriage.dump();
                })
                .lineToConstantHeading(TO_PARK)
                .addTemporalMarker(() -> {
                    carriage.setPivotIntake(false); //faces outtake
                })
                .waitSeconds(1)
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    carriage.setPivotIntake(true); //faces outtake

                })
        ;


        path = analysis.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

//        sleep(4000);
//        sleep(1000);


        telemetry.addData("Detected: ", position);
        dashTelemetry.addData("Detected", position);

        initTraj();
        telemetry.update();
        waitForStart();

        drive.setPoseEstimate(path.start());
        drive.followTrajectorySequence(path);

        while (opModeIsActive() && !Thread.currentThread().isInterrupted() && drive.isBusy()) {
            drive.update();
        }


    }
}


