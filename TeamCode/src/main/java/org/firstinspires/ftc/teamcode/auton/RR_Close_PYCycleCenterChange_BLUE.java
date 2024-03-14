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
import org.firstinspires.ftc.teamcode.util.HighHang;
import org.firstinspires.ftc.teamcode.util.IntakeRoller;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.PokeyClaw;
import org.firstinspires.ftc.teamcode.util.Slides;
import org.firstinspires.ftc.teamcode.util.WebcamServo;


@Autonomous(name = "(CLOSE) 51 - BLUE - PY_CycleCenterChange", group = "Linear OpMode")
@Config

public class RR_Close_PYCycleCenterChange_BLUE extends LinearOpMode{


    protected SampleMecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;
    protected HighHang highhang;

    protected PokeyClaw pokeyClaw;

    protected WebcamServo webcamServo;


    public static int SLIDE_POS_UP = -800;

    public static int SLIDE_POS_UP_2 = -400;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .4;

    TrajectorySequence path;

    public static int VISION_ANG_LEFT = 45;
    public static int VISION_ANG_CENTER = 15;
    public static int VISION_ANG_RIGHT = -170;
    public static int VISION_ANG = VISION_ANG_CENTER; //actual angle

    public static Pose2d PURPLE_CENTER = new Pose2d(24, 0, Math.toRadians(0));
    public static Pose2d PURPLE_CENTER_LEFT = new Pose2d(26, 30, Math.toRadians(-170));

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 2;
    public static double CARRIAGE_RAISE_TIME = 2;

    //backboard movement
    public static Pose2d BACKBOARD_DEFAULT = new Pose2d(25, 37, Math.toRadians(-90));

    public static Vector2d BACKBOARD_LEFT  = new Vector2d(22, 39);

    public static Vector2d BACKBOARD_RIGHT = new Vector2d(33, 39);

    public static Vector2d BACKBOARD_CENTER = new Vector2d(26, 39);

    public static Vector2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization

    public static Pose2d BEFORE_STACK_WAIT = new Pose2d(30, 30, Math.toRadians(-90));
    public static Vector2d STACK_WAIT = new Vector2d(48, -72);

    public static Vector2d TO_PARK_1 = new Vector2d(0, 37); //parking position ( full square)
    public static Vector2d TO_PARK_2 = new Vector2d(0, 42); //parking position ( full square)


    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();


    public static int position = 1; //default visual detect

    protected void setupDevices(){
        drive = new SampleMecanumDrive(hardwareMap);
        vision = new GreenShroomVision(hardwareMap, dashTelemetry);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);
        intake = new IntakeRoller(hardwareMap);
        highhang = new HighHang(hardwareMap);
        pokeyClaw = new PokeyClaw(hardwareMap);
        webcamServo = new WebcamServo(hardwareMap);
    }

    public void initTraj() {

//        position = vision.getPosition();
        if (position == 1) {
            VISION_ANG = VISION_ANG_LEFT;
            BACKBOARD_ADJUST = BACKBOARD_LEFT;
            PURPLE_CENTER = PURPLE_CENTER_LEFT;
        } else if (position == 3) {
            VISION_ANG = VISION_ANG_RIGHT;
            BACKBOARD_ADJUST = BACKBOARD_RIGHT;
        } else {
            // no need for center, as it is defaulted to pos = 2
            VISION_ANG = VISION_ANG_CENTER;
        }

        TrajectorySequenceBuilder dumpBothPath = drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0)) //start
                .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
                    webcamServo.setPosition(false); //go down
                })
                .lineToLinearHeading(PURPLE_CENTER)
                .turn(Math.toRadians(VISION_ANG))
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(false);
                })
                .waitSeconds(1.25)
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(true);
                })
                .addTemporalMarker(() -> {
                    pokeyClaw.openClaw(true);
                })
                .waitSeconds(.25)
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(true);
                })
                .waitSeconds(.5)
//                .lineToConstantHeading(RESET_HOME) //go back home (start pos)
                .lineToLinearHeading(BACKBOARD_DEFAULT)
                .lineTo(BACKBOARD_ADJUST) //adjusts for detection
                .waitSeconds(1)

//                //dumping sequence
                .waitSeconds(1.5)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                })
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    carriage.setPivotIntake(false); //faces outtake
                })
                .waitSeconds(CARRIAGE_RAISE_TIME)

                .waitSeconds(1.5)
                //dumping sequence
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    carriage.setPivotIntake(true); //faces outtake
                }) // <-- end of dumping sequence -->;
                .addTemporalMarker(()->{
                    carriage.setCarriageOpen(false); //close carriage
                }) //end of all
                .waitSeconds(.5) //slides down
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
                })
                //dumping done

                .lineToLinearHeading(BEFORE_STACK_WAIT)
                .lineTo(STACK_WAIT)
                .waitSeconds(.5) //slides down
                .addTemporalMarker(()->{
                    intake.intake(1);
                })
                .waitSeconds(1) //slides down
                .lineToLinearHeading(BEFORE_STACK_WAIT)
                //dumping sequence
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    carriage.setPivotIntake(true); //faces outtake
                }) // <-- end of dumping sequence -->;
                .addTemporalMarker(()->{
                    carriage.setCarriageOpen(false); //close carriage
                }) //end of all
                .waitSeconds(.5) //slides down
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
                })
                //dumping done

                .waitSeconds(1);


        path = dumpBothPath.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        pokeyClaw.openClaw(false);
        webcamServo.setPosition(true);
        position = vision.getPosition(); //get position by new camera position

        waitForStart();

        telemetry.update();
        //print positions
        dashTelemetry.addData("Detected", position);

        initTraj(); //init new traj. with the updated values

        drive.setPoseEstimate(path.start());
        drive.followTrajectorySequence(path);


        while (opModeIsActive() && !Thread.currentThread().isInterrupted() && drive.isBusy()) {
            drive.update();
        }

    }
}


