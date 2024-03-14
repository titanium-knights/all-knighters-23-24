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
import org.firstinspires.ftc.teamcode.util.Pokey;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.PokeyClaw;
import org.firstinspires.ftc.teamcode.util.Slides;
import org.firstinspires.ftc.teamcode.util.WebcamServo;


@Autonomous(name = "51 (CLOSE) - BLUE - PY", group = "Linear OpMode")
@Config

public class RR_Close_PYPokeyClaw_BLUE extends LinearOpMode{
     /*
    Goal of this op-mode is to dump both preload onto the detected spot (1,2,3)

    Cases for autonomous:
    a. Min score if missed detection: 5 (auton pixel score) * 2 (purple, yellow) + 5 (parking) + 3 (teleop recount) * 2 (purple, yellow) = 21
    b. Min score if missed, placing onto floor: 4 (pixel total) + 5 (parking)
    c. Max score if detected correctly onto board: 5 (auton pixel score, purple) * 2 + 20 (correct detection) + 5 (parking) + 3 (teleop recount) * 2 (purple, yellow) = 41
    d. Max score if detected BOTH correctly and placed in corresponding spot, max score w/o cycling: 61 (consider c)
     */

    protected SampleMecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;
    protected HighHang highhang;

    protected PokeyClaw pokeyClaw;

    protected WebcamServo webcamServo;


    public static int SLIDE_POS_UP = -500;

    public static int SLIDE_POS_UP_2 = -400;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .4;

    public static boolean positionManual = false;

    TrajectorySequence path;

    public static int VISION_ANG_LEFT = 90;
    public static int VISION_ANG_CENTER = -15;
    public static int VISION_ANG_RIGHT = -60;

    public static int VISION_ANG = VISION_ANG_CENTER; //actual angle
    public static Vector2d PURPLE_CENTER = new Vector2d(28, 0);
    public static Vector2d RESET_HOME = new Vector2d(10, 6);

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 2;

    //backboard movement
    public static Pose2d BACKBOARD_DEFAULT = new Pose2d(25, 37, Math.toRadians(-90));

    public static Vector2d BACKBOARD_LEFT  = new Vector2d(21, 40);

    public static Vector2d BACKBOARD_RIGHT = new Vector2d(35, 40);

    public static Vector2d BACKBOARD_CENTER = new Vector2d(30, 40);

    public static Vector2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization

    public static Vector2d TO_PARK_1 = new Vector2d(0, 36); //parking position ( full square)


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
        } else {
            // no need for center, as it is defaulted to pos = 2
            VISION_ANG = VISION_ANG_CENTER;
            BACKBOARD_ADJUST = BACKBOARD_CENTER;


        }

        TrajectorySequenceBuilder dumpBothPath = drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0)) //start
                .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
                    webcamServo.setPosition(false); //go down
                })
                .lineToConstantHeading(PURPLE_CENTER)
                .addTemporalMarker(() -> {
                    pokeyClaw.goToHalfPosition();
                })
                .turn(Math.toRadians(VISION_ANG))
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(false);
                })
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    pokeyClaw.openClaw(true);
                })
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(true);
                })
//                .waitSeconds(.25)
//                .addTemporalMarker(() -> {
//                    pokeyClaw.resetPosition(true);
//                })
                .waitSeconds(.5)
                .lineToConstantHeading(RESET_HOME) //go back home (start pos)
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
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(1)
                .addTemporalMarker(()->{
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
                .waitSeconds(1);


        path = dumpBothPath.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        pokeyClaw.openClaw(false);
        webcamServo.setPosition(true);

        waitForStart();
        if (!positionManual) {
            position = vision.getPosition(); //get position by new camera position
        }
        dashTelemetry.addData("Detected", position);


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


