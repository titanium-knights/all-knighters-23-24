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
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;
import org.firstinspires.ftc.teamcode.util.HighHang;
import org.firstinspires.ftc.teamcode.util.IntakeRoller;
import org.firstinspires.ftc.teamcode.util.Pokey;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.PokeyClaw;
import org.firstinspires.ftc.teamcode.util.Slides;
import org.firstinspires.ftc.teamcode.util.WebcamServo;

@Deprecated
//@Autonomous(name = "51 (FAR) - BLUE - PY_Cycle", group = "Linear OpMode")
//@Config

public class UNSTABLE_RR_Far_PYCycle_BLUE extends LinearOpMode{
   /*
   Op mode gets 1 pixel from the stack and then dumps yellow and white to backboard
    */

    protected SampleMecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;
    protected HighHang highhang;

    protected WebcamServo webcamServo;

    protected Pokey pokey;
    protected PokeyClaw pokeyClaw;


    public static int SLIDE_POS_UP = -700;

    public static int SLIDE_POS_UP_2 = -300;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .4;

    TrajectorySequence path;

    public static int VISION_ANG_LEFT = 60;
    public static int VISION_ANG_CENTER = 15;
    public static int VISION_ANG_RIGHT = -90;


    public static int VISION_ANG; //actual angle

    public static Pose2d PURPLE_CENTER = new Pose2d(28, 0, Math.toRadians(0));
    public static Pose2d PURPLE_CENTER_CENTER = new Pose2d(40, 30, Math.toRadians(-180));

    public static Pose2d RESET_HOME = new Pose2d(4, 0, Math.toRadians(-90));
    public static Vector2d RESET_HOME_CLOSE = new Vector2d(4, 48);

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 2;
    public static double CARRIAGE_RAISE_TIME = 2;

    //backboard movement
    public static Pose2d BACKBOARD_DEFAULT = new Pose2d(48, 88, Math.toRadians(-90));

    public static Vector2d BACKBOARD_LEFT  = new Vector2d(22, 88);

    public static Vector2d BACKBOARD_RIGHT = new Vector2d(30, 88);

    public static Vector2d BACKBOARD_CENTER = new Vector2d(25, 88);

    public static Vector2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization

    public static Vector2d TO_PARK_1 = new Vector2d(50, 85); //parking position ( full square)
    public static Vector2d TO_PARK_2 = new Vector2d(50, 90); //parking position ( full square)


    public static Pose2d STACK_WAIT = new Pose2d(48, -24, Math.toRadians(-90));

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
            PURPLE_CENTER = PURPLE_CENTER_CENTER;
        }

        TrajectorySequenceBuilder dumpBothPath = drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0)) //start
                .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
                    webcamServo.setPosition(false); //go down
                })
                .addTemporalMarker(() -> {
                    pokeyClaw.goToHalfPosition();
                })
                .waitSeconds(1)
                .lineToLinearHeading(PURPLE_CENTER)
                .turn(Math.toRadians(VISION_ANG))
                .waitSeconds(1)
                .addTemporalMarker(() -> {
                    pokeyClaw.openClaw(true);
                })
                .waitSeconds(1.5)
                .addTemporalMarker(() -> {
                    pokeyClaw.resetPosition(true);
                })
                .lineToLinearHeading(STACK_WAIT)
                .lineToLinearHeading(BACKBOARD_DEFAULT)
                .lineTo(BACKBOARD_ADJUST) //adjusts for detection
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                })
//                //dumping sequence
                .waitSeconds(2)
                .addTemporalMarker(() -> {
                    carriage.setPivotIntake(false); //faces outtake
                })
                .waitSeconds(CARRIAGE_RAISE_TIME)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP_2, SLIDE_POW); //slides up for dump
                })
                .waitSeconds(1.5)
//                //dumping sequence
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .addTemporalMarker(()->{
                    carriage.setPivotIntake(true); //faces outtake
                }) // <-- end of dumping sequence -->;
                .waitSeconds(.5) //slides down
                .addTemporalMarker(()->{
                    carriage.setCarriageOpen(false); //close carriage
                }) //end of all
                .waitSeconds(1) //slides down
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
                })
                .lineTo(TO_PARK_1)
                .lineTo(TO_PARK_2)
                .waitSeconds(1);


        path = dumpBothPath.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        pokeyClaw.openClaw(false);
        webcamServo.setPosition(true); //go down
        position = vision.getPosition(); //get position by new camera position

        waitForStart();

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


