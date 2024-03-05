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
import org.firstinspires.ftc.teamcode.util.Slides;
import org.firstinspires.ftc.teamcode.util.WebcamServo;


@Config
@Deprecated
public class RR_Close_PY_BLUE extends LinearOpMode{
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

    protected WebcamServo webcamServo;


    public static int SLIDE_POS_UP = -700;

    public static int SLIDE_POS_UP_2 = -300;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .4;

    TrajectorySequence path;

    public static int VISION_ANG_LEFT = 90;
    public static int VISION_ANG_CENTER = 0;
    public static int VISION_ANG_RIGHT = -90;

    public static int VISION_ANG; //actual angle
    public static Vector2d PURPLE_CENTER = new Vector2d(24, 0);

    public static Vector2d RESET_HOME = new Vector2d(0, 0);

    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 2;

    //backboard movement
    public static Pose2d BACKBOARD_DEFAULT = new Pose2d(25, 40, Math.toRadians(-90));

    public static Vector2d BACKBOARD_LEFT  = new Vector2d(22, 40);

    public static Vector2d BACKBOARD_RIGHT = new Vector2d(30, 40);

    public static Vector2d BACKBOARD_CENTER = new Vector2d(26, 40);

    public static Vector2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization

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
                .lineToConstantHeading(PURPLE_CENTER)
                .turn(Math.toRadians(VISION_ANG))
                .addTemporalMarker(() -> {
                    intake.intake(-INTAKE_POW);
                })
                .waitSeconds(INTAKE_TIME)
                .addTemporalMarker(() -> {
                    intake.intake(0); //stop intake
                })
                .lineToConstantHeading(RESET_HOME) //go back home (start pos)
                .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
                    highhang.goToReset(); //go down
                })
                .lineToLinearHeading(BACKBOARD_DEFAULT)
                .lineTo(BACKBOARD_ADJUST) //adjusts for detection
                .waitSeconds(2)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                })
//                //dumping sequence
                .waitSeconds(3)
                .addTemporalMarker(() -> {
                    carriage.setPivotIntake(false); //faces outtake
                })
                .waitSeconds(3)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP_2, SLIDE_POW); //slides up for dump
                })
//                //dumping sequence
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                })
                .waitSeconds(2)
                .addTemporalMarker(()->{
                    carriage.setPivotIntake(true); //faces outtake
                }) // <-- end of dumping sequence -->;
                .waitSeconds(1) //slides down
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

        waitForStart();

        highhang.goToCamera();
        sleep(3000); //wait two seconds
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


