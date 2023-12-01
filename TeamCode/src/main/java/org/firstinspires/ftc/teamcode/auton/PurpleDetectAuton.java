package org.firstinspires.ftc.teamcode.auton;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

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
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.Slides;

@Autonomous(name = "PurpleDetectAuton", group = "Linear OpMode")
@Config

public class PurpleDetectAuton extends LinearOpMode{
    protected SampleMecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    TrajectorySequence path;

    public static int START_Y = 0;
    public static int START_X = 0;

    public static int VISION_ANG_LEFT = -90;
    public static int VISION_ANG_CENTER = 0;
    public static int VISION_ANG_RIGHT = 90;

    public static int VISION_ANG; //actual angle
    public static Vector2d BEFORE_TURN = new Vector2d(START_X+28, START_Y+0);

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    protected void setupDevices(){
        drive = new SampleMecanumDrive(hardwareMap);
        vision = new GreenShroomVision(hardwareMap, null);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);
    }

    public void initTraj() {
        if (vision.getPosition() == 1) {
            VISION_ANG = VISION_ANG_LEFT;
        } else if (vision.getPosition() == 2) {
            VISION_ANG = VISION_ANG_CENTER;
        } else {
            VISION_ANG = VISION_ANG_RIGHT;
        }

        TrajectorySequenceBuilder analysis = drive.trajectorySequenceBuilder(new Pose2d(START_X, START_Y, 0))
                .lineToConstantHeading(BEFORE_TURN)
                .turn(Math.toRadians(VISION_ANG))
                .waitSeconds(0)
                .addTemporalMarker(()->{
//                    slides.setPosition(LIFT_LOWER_1, LIFT_POWER_DOWN);
//                    carriage.dump();
                });

        path = analysis.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        sleep(4000);
        int position =  vision.getPosition();
        sleep(1000);


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


