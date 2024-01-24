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


@Autonomous(name = "Far side", group = "Linear OpMode")
@Config

public class RR_Far_SideDumpBothPreload_BLUE extends LinearOpMode{
    /*
    Basically do the same thing as the DumpBothPreloadBLUE but first move right
     */

    protected SampleMecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;
    protected IntakeRoller intake;
    protected HighHang highhang;


    public static int SLIDE_POS_UP = -900;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .4;

    TrajectorySequence path;

    public static Vector2d START_RIGHT_SIDE = new Vector2d(0, 24);

    //backboard movement
    public static Pose2d BACKBOARD_DEFAULT = new Pose2d(24, 61, Math.toRadians(-90));

    public static Vector2d BACKBOARD_LEFT  = new Vector2d(24, 61);

    public static Vector2d BACKBOARD_RIGHT = new Vector2d(33, 61);

    public static Vector2d BACKBOARD_CENTER = new Vector2d(28, 61);

    public static Vector2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization

    public static Vector2d TO_PARK_1 = new Vector2d(0, 61); //parking position ( full square)
    public static Vector2d TO_PARK_2 = new Vector2d(0, 66); //parking position ( full square)

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();


    public static int position = 1; //default visual detect

    protected void setupDevices(){
        drive = new SampleMecanumDrive(hardwareMap);
        vision = new GreenShroomVision(hardwareMap, dashTelemetry);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);
        intake = new IntakeRoller(hardwareMap);
        highhang = new HighHang(hardwareMap);
    }

    public void initTraj() {
//        position = vision.getPosition();
        if (position == 1) {
            BACKBOARD_ADJUST = BACKBOARD_LEFT;
        } else if (position == 3) {
            BACKBOARD_ADJUST = BACKBOARD_RIGHT;
        } // no need for center, as it is defaulted to pos = 2

        TrajectorySequenceBuilder dumpBothPath = drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0)) //start

                .waitSeconds(1)
                .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
                    highhang.goToReset(); //go down
                })
                .waitSeconds(10)
                .lineTo(START_RIGHT_SIDE) // go to the start pos near backdrop
                .lineToLinearHeading(BACKBOARD_DEFAULT)
                .lineTo(BACKBOARD_ADJUST) //adjusts for detection
                .waitSeconds(3)
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
                })
//                //dumping sequence
                .waitSeconds(3)
                .addTemporalMarker(() -> {
                    carriage.setPivotIntake(false); //faces outtake
                })
                .waitSeconds(3)
                .addTemporalMarker(()-> {
                    carriage.setCarriageOpen(true);
                })//opens the carriage
                .waitSeconds(3)
                .addTemporalMarker(()->{
                    carriage.setPivotIntake(true); //faces outtake
                }) // <-- end of dumping sequence -->;
                .waitSeconds(3) //slides down
                .addTemporalMarker(()->{
                    carriage.setCarriageOpen(false); //close carriage
                }) //end of all
                .waitSeconds(1) //slides down
                .addTemporalMarker(()->{
                    slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
                })
                .lineTo(TO_PARK_1)
                .lineTo(TO_PARK_2)
                .waitSeconds(3);


        path = dumpBothPath.build();

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        waitForStart();

        highhang.goToCamera();
        sleep(5000); //wait five seconds
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


