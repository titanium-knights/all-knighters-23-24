package org.firstinspires.ftc.teamcode.old_autons;


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
import org.firstinspires.ftc.teamcode.util.HighHang;
import org.firstinspires.ftc.teamcode.util.IntakeRoller;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.Slides;

@Config
@Deprecated

public class RR_Close_INTAKE_PurpleDetectAuton extends LinearOpMode{
    protected SampleMecanumDrive drive;
//    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;
    protected HighHang highhang;


    public static int SLIDE_POS_UP = -1200;

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 2;

    public static double SLIDE_POW = .4;


    TrajectorySequence path;

    public static int START_Y = 0;
    public static int START_X = 0;

    public static int VISION_ANG_LEFT = -90;
    public static int VISION_ANG_CENTER = 0;
    public static int VISION_ANG_RIGHT = -90;

    public static int VISION_ANG; //actual angle
    public static Vector2d BEFORE_TURN = new Vector2d(28, 0);

    public static Vector2d BACKWARD_LEFT =  new Vector2d(28, 26);

    public static Vector2d BACKWARD_RIGHT = new Vector2d(40, 0);

    public static Vector2d BACKWARD;

    public static Pose2d TO_PARK = new Pose2d(24, 26, -90);

    public static int position = 1;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    protected void setupDevices(){
        drive = new SampleMecanumDrive(hardwareMap);
       // vision = new GreenShroomVision(hardwareMap, null);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);
        intake = new IntakeRoller(hardwareMap);
        highhang = new HighHang(hardwareMap);
    }

    public void initTraj() {
//        position = vision.getPosition();
        if (position == 1) {
            VISION_ANG = VISION_ANG_LEFT;
            BACKWARD = BACKWARD_LEFT;
        } else if (position == 2) {
            VISION_ANG = VISION_ANG_CENTER;
        } else {
            VISION_ANG = VISION_ANG_RIGHT;
            BACKWARD = BACKWARD_RIGHT;
        }

        TrajectorySequenceBuilder leftOrRightPath = drive.trajectorySequenceBuilder(new Pose2d(START_X, START_Y, 0))
                .lineToConstantHeading(BEFORE_TURN)
                .turn(Math.toRadians(VISION_ANG))
                .addTemporalMarker(() -> {
                    intake.intake(-INTAKE_POW);
                })
                .waitSeconds(INTAKE_TIME)
                .addTemporalMarker(() -> {
                    intake.intake(0); //stop intake
                })
                .lineToConstantHeading(BACKWARD)
                .waitSeconds(0)
                .lineToLinearHeading(TO_PARK)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW);
                })
                .waitSeconds(2)
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
                });

        TrajectorySequenceBuilder centerPath = drive.trajectorySequenceBuilder(new Pose2d(START_X, START_Y, 0))
                .lineToConstantHeading(BEFORE_TURN)
                .turn(Math.toRadians(VISION_ANG))
                .addTemporalMarker(() -> {
                    intake.intake(-INTAKE_POW);
                })
                .waitSeconds(INTAKE_TIME)
                .addTemporalMarker(() -> {
                    intake.intake(0); //stop intake
                })
                .waitSeconds(0)
                .lineToLinearHeading(TO_PARK)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW);
                })
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
                });

        if (position != 3) {
            path = leftOrRightPath.build();
        } else {
            path = centerPath.build();
        }

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

//        sleep(4000);
//        sleep(1000);

        highhang.goToCamera();

        telemetry.addData("Detected: ", position);
        dashTelemetry.addData("Detected", position);

        initTraj();
        telemetry.update();
        waitForStart();

        drive.setPoseEstimate(path.start());
        drive.followTrajectorySequence(path);

        highhang.goToReset();

        while (opModeIsActive() && !Thread.currentThread().isInterrupted() && drive.isBusy()) {
            drive.update();
        }

    }
}


