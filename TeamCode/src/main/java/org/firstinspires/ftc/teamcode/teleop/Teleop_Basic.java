package org.firstinspires.ftc.teamcode.teleop;
//imports go up here, for ex. util classes or ftc library

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.*; //star allows us to import everything in the folder :)

@Config

@TeleOp
public class Teleop_Basic extends OpMode { //class header, we will always extend a TYPE of OpMode
    //public allows us to see this variable in the FTC Dashboard (a website that allows us to edit in realtime)
    //static = field that is shared across every instance of a class
    //double is a data type that allows us to use decimal points (others incl. int, string, float...)

    public static double DRIVE_SPEED = .8;
    public static int SLIDE_POS1 = -50;
    public static int SLIDE_POS2 = 50;
    public static int BUFFER = 20;


    public static double SLIDE_POW = .1;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
    Slides slides;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();



    public void setupDevices() {
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
        slides = new Slides(hardwareMap);
    }

    @Override
    public void init() { //initializes, connects
        setupDevices();
    }

    @Override
    public void loop() { //constantly doing, repeating
        drive.teleOpRobotCentric(gamepad1, DRIVE_SPEED); //go drive vroom

        telemetry.addData("Slides 1 (right) Position", slides.getPositionR());
        dashTelemetry.addData("Slides (right) Position", slides.getPositionR());
        telemetry.addData("Slides 2 (left) Position", slides.getPositionL());
        dashTelemetry.addData("Slides 2 (left) Position", slides.getPositionL());

        telemetry.addData("Slides (average) Position", slides.getAverage());
        dashTelemetry.addData("Slides (average) Position", slides.getAverage());

        if (gamepad1.a) {
            slides.setPosition(SLIDE_POS1, SLIDE_POW);
        }

        if (gamepad1.y) {
            slides.setPosition(SLIDE_POS2, SLIDE_POW);
        }

        if (gamepad1.x) {
            slides.setPosition(SLIDE_POS1, SLIDE_POW);
            slides.stop();
        }

        if (gamepad1.b) {
            slides.setPosition(SLIDE_POS2, SLIDE_POW);
            slides.stop();
        }


    }

}
