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


@Autonomous(name = "50 - F_BLUE - PY", group = "Linear OpMode")
@Config

public class RR_Far_PY_BLUE extends LinearOpMode{

    protected SampleMecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;
    protected HighHang highhang;

    protected WebcamServo webcamServo;

    protected PokeyClaw pokeyClaw;


    public static int SLIDE_POS_UP = -700;

    public static int SLIDE_POS_UP_2 = -300;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .8;

    TrajectorySequence path;

    public static int VISION_ANG_LEFT = 95;
    public static int VISION_ANG_CENTER = -15;
    public static int VISION_ANG_RIGHT = -50;


    public static int VISION_ANG; //actual angle
    public static Vector2d PURPLE_CENTER = new Vector2d(29, 0);
    public static Pose2d RESET_HOME = new Pose2d(4, 0, Math.toRadians(-90));
    public static Vector2d RESET_HOME_CLOSE = new Vector2d(4, 48);

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 2;
    public static double CARRIAGE_RAISE_TIME = 2;

    //backboard movement
    public static Pose2d BACKBOARD_DEFAULT = new Pose2d(28, 88, Math.toRadians(-90));

    public static Vector2d BACKBOARD_LEFT  = new Vector2d(18, 88);

    public static Vector2d BACKBOARD_RIGHT = new Vector2d(34, 88);

    public static Vector2d BACKBOARD_CENTER = new Vector2d(27, 88);

    public static Vector2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization

    public static Vector2d TO_PARK_1 = new Vector2d(55, 85); //parking position ( full square)
    public static Vector2d TO_PARK_2 = new Vector2d(55, 90); //parking position ( full square)


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
        } else if (position == 3) {
            VISION_ANG = VISION_ANG_RIGHT;
            BACKBOARD_ADJUST = BACKBOARD_RIGHT;
        } // no need for center, as it is defaulted to pos = 2

        TrajectorySequenceBuilder dumpBothPath = drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0)) //start
                .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
                    webcamServo.setPosition(false); //go down
                })
                .addTemporalMarker(() -> {
                    pokeyClaw.goToHalfPosition();
                })
                .waitSeconds(1)
                .lineToConstantHeading(PURPLE_CENTER)
                .turn(Math.toRadians(VISION_ANG))
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    pokeyClaw.openClaw(true);
                })
                .waitSeconds(1.5)
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(true);
                })
                .lineToLinearHeading(RESET_HOME)
                .lineToConstantHeading(RESET_HOME_CLOSE)
                .waitSeconds(.5)
//                .lineToLinearHeading(BACKBOARD_DEFAULT)
                .addTemporalMarker( ()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                    carriage.setPivotIntake(false); //faces outtake
                })
                .lineToLinearHeading(BACKBOARD_DEFAULT)
                .lineTo(BACKBOARD_ADJUST) //adjusts for detection
                .waitSeconds(1.5)
//                //dumping sequence
                //dumping sequence
                .addTemporalMarker( ()->{
                    slides.setPosition(SLIDE_POS_UP_2, SLIDE_POW); //slides up for dump
                })
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(1)
                .addTemporalMarker( ()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                    carriage.setPivotIntake(true); //faces outtake
                }) // <-- end of dumping sequence -->;
                .waitSeconds(.5) //slides down
                .addTemporalMarker(()->{
                    carriage.setCarriageOpen(false); //close carriage
                })
                .waitSeconds(.5) //slides down
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
                })
                .lineTo(TO_PARK_1)
                .lineTo(TO_PARK_2)
                .waitSeconds(.5);

        path = dumpBothPath.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        pokeyClaw.openClaw(false);
        webcamServo.setPosition(true); //go down

        waitForStart();

        //print positions
        position = vision.getPosition(); //get position by new camera position

        dashTelemetry.addData("Detected", position);
        dashTelemetry.addData("SLIDES POS", slides.getPosition());

        initTraj(); //init new traj. with the updated values

        telemetry.update();

        drive.setPoseEstimate(path.start());
        drive.followTrajectorySequence(path);

        while (opModeIsActive() && !Thread.currentThread().isInterrupted() && drive.isBusy()) {
            drive.update();
        }

    }
}


