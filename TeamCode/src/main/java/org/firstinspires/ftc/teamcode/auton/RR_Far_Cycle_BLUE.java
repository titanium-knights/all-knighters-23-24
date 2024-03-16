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
import org.firstinspires.ftc.teamcode.util.StackIntake;
import org.firstinspires.ftc.teamcode.util.WebcamServo;


@Autonomous(name = "55 - F_BLUE - 2+1 Cycle", group = "Linear OpMode")
@Config

public class RR_Far_Cycle_BLUE extends LinearOpMode{

    protected SampleMecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;
    protected HighHang highhang;

    protected WebcamServo webcamServo;

    protected PokeyClaw pokeyClaw;

    protected StackIntake stackIntake;


    public static int SLIDE_POS_UP = -700;

    public static int SLIDE_POS_UP_2 = -300;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .4;
    public static double SLIDE_POW_2 = .2;


    TrajectorySequence path;

    public static int VISION_ANG_LEFT = 80;
    public static int VISION_ANG_CENTER = 15;
    public static int VISION_ANG_RIGHT = -30;


    public static int VISION_ANG; //actual angle

    public static Pose2d PURPLE_CENTER = new Pose2d(29, 0, Math.toRadians(0));

    public static Pose2d PURPLE_CENTER_LEFT = new Pose2d(28, 0, Math.toRadians(VISION_ANG_LEFT));
    public static Pose2d PURPLE_CENTER_RIGHT = new Pose2d(27, -2, Math.toRadians(VISION_ANG_RIGHT));
    public static Pose2d PURPLE_CENTER_CENTER = new Pose2d(53, 0, Math.toRadians(180));

    public static Pose2d RESET_HOME = new Pose2d(4, 0, Math.toRadians(-90));
    public static Vector2d RESET_HOME_CLOSE = new Vector2d(4, 48);

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 2;
    public static double CARRIAGE_RAISE_TIME = 2;

    //backboard movement

    //backboard movement
    public static Pose2d BACKBOARD_DEFAULT = new Pose2d(28, 88, Math.toRadians(-90));

    public static Pose2d BACKBOARD_LEFT  = new Pose2d(19, 88, Math.toRadians(-90));

    public static Pose2d BACKBOARD_RIGHT = new Pose2d(36, 88, Math.toRadians(-90));

    public static Pose2d BACKBOARD_CENTER = new Pose2d(25, 88, Math.toRadians(-90));

    public static Pose2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization
    public static Pose2d BEFORE_STACK_WAIT = new Pose2d(51, 0, Math.toRadians(-90));
    public static Vector2d STACK_WAIT = new Vector2d(51, -27);
    public static Vector2d AFTER_STACK_WAIT = new Vector2d(50, 85);

    public static Vector2d TO_PARK_1 = new Vector2d(50, 85); //parking position ( full square)
    public static Vector2d TO_PARK_2 = new Vector2d(50, 90); //parking position ( full square)


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
        stackIntake = new StackIntake(hardwareMap);

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
            PURPLE_CENTER = PURPLE_CENTER_RIGHT;

        } else {
            // no need for center, as it is defaulted to pos = 2
            VISION_ANG = 0;
            PURPLE_CENTER = PURPLE_CENTER_CENTER;
            BACKBOARD_ADJUST = BACKBOARD_CENTER;

        }

            TrajectorySequenceBuilder dumpBothPath = drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0)) //start
                //
                .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
                    webcamServo.setPosition(false); //go down
                })
                .addTemporalMarker(() -> {
                    pokeyClaw.goToHalfPosition();
                })
                    .lineToLinearHeading(PURPLE_CENTER)
                    .waitSeconds(1)
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(false);
                })
                .addTemporalMarker(() -> {
                    pokeyClaw.openClaw(true);
                })
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(true);
                })
                    .waitSeconds(1.5)
                .lineToLinearHeading(BEFORE_STACK_WAIT)
                .lineTo(STACK_WAIT)

                //start cycle sequence
                .addTemporalMarker(()->{
                    intake.intake(1);
                })
                //linkage movement
                .addTemporalMarker(()->{
                    stackIntake.flipFlop(true);
                })
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    stackIntake.flipFlop(false);
                })
                .lineTo(AFTER_STACK_WAIT)
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    intake.intake(0);
                })
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                })
                .lineToLinearHeading(BACKBOARD_ADJUST) //adjusts for detection
//                //dumping sequence
                //dumping sequence
                    .addTemporalMarker(() -> {
                        carriage.setPivotIntake(false); //faces outtake
                    })
                    .waitSeconds(2)
                .addTemporalMarker(()-> {
                    slides.setPosition(SLIDE_POS_UP_2, SLIDE_POW); //slides up for dump
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(1)
                .addTemporalMarker( ()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                    carriage.setPivotIntake(true); //faces intake
                }) // <-- end of dumping sequence -->;
                .waitSeconds(1) //slides down
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(false); //close carriage
                    slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
                })
                .lineTo(TO_PARK_1);

        path = dumpBothPath.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        pokeyClaw.openClaw(false);
        webcamServo.setPosition(true); //go down

        position = vision.getPosition(); //get position by new camera position

        waitForStart();
        position = vision.getPosition(); //get position by new camera position


        //print positions
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


