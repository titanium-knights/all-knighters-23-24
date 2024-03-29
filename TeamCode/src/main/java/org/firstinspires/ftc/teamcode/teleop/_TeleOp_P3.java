package org.firstinspires.ftc.teamcode.teleop;
//imports go up here, for ex. util classes or ftc library

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.*; //star allows us to import everything in the folder :)


@TeleOp(name = "00 TELE_OP")
public class _TeleOp_P3 extends OpMode { //class header, we will always extend a TYPE of OpMode
    //public allows us to see this variable in the FTC Dashboard (a website that allows us to edit in realtime)
    //static = field that is shared across every instance of a class
    //double is a data type that allows us to use decimal points (others incl. int, string, float...)

    public static double DRIVE_SPEED_FAST = 1;
    public static double DRIVE_SPEED_SLOW = .4;
    public static double DRIVE_SPEED_CURRENT = DRIVE_SPEED_FAST;

    public static boolean isSlowmode = false;

    public static int pixelIn = 0;
    public static double distanceSensorDistance = 5;

//    public static boolean pokeyWasUp = true;

    public static boolean isPixelIn = false;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
    IntakeRoller intakeRoller;
    PixelCarriage pixelCarriage;
    Slides slides;
    PlaneLauncher planeLauncher;
    HighHang highHang;

    PokeyClaw pokeyClaw;

    StackIntake stackIntake;

    DistanceTester distanceTester;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    /*
    All controls:
    Gamepad 1:
        DPAD: right (manual flip flop out), left (manual flip flop in), up (plane shoot), down (NONE)
        Joysticks: driving
        Bumpers: right (highHang up), left (highHang down)
        Triggers: right (intake), left (outtake)
        A: slow mode toggle
        Y: NONE
        B: stack intake flip flop
        X: NONE

    Gamepad 2:
        DPAD: right (pokey up), left (pokey down), up (up preset), down (down preset)
        Joysticks: NONE
        Bumpers: right (carriage to outtake), left (carriage to intake)
        Triggers: right (slides up), left (slides down)
        A: close carriage (down)
        Y: open carriage (up)
        B: pokey (open claw)
        X: pokey (close claw)
     */


    public void setupDevices() {
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
        slides = new Slides(hardwareMap);
        pixelCarriage = new PixelCarriage(hardwareMap);
        intakeRoller = new IntakeRoller(hardwareMap);
        planeLauncher = new PlaneLauncher(hardwareMap);
        highHang = new HighHang(hardwareMap);
        pokeyClaw = new PokeyClaw(hardwareMap);
        distanceTester = new DistanceTester(hardwareMap);
        stackIntake = new StackIntake(hardwareMap);
    }

    @Override
    public void init() { //initializes, connects
        setupDevices();
    }

    @Override
    public void loop() { //constantly doing, repeating
        if (isSlowmode) {
            DRIVE_SPEED_CURRENT = DRIVE_SPEED_SLOW;
        } else {
            DRIVE_SPEED_CURRENT = DRIVE_SPEED_FAST;
        }

        drive.teleOpRobotCentric(gamepad1, DRIVE_SPEED_CURRENT); //go drive vroom
//
//        if (pixelCarriage.isPixelInCarriage()) {
//            gamepad1.rumble(1000);
//            gamepad2.rumble(1000);
//            isPixelIn = true;
//        } else {
//            isPixelIn = false;
//        }

        telemetry.addData("pixelInCarriage: ", isPixelIn);
        dashTelemetry.addData("pixelInCarriage: ", isPixelIn);

        //SLOW MODE
        if (gamepad1.a) { //slowmode added
            isSlowmode = !isSlowmode;
        }

        telemetry.addData("isSlowmode: ", isSlowmode);
        dashTelemetry.addData("isSlowmode: ", isSlowmode);

        //Slide CONTROLS -- CONTROLLER 2
        if (gamepad2.left_trigger > .1) { //move down if the left trigger is pressed down, set power accordingly
            slides.setPower(gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > .1) { //move up if right trigger is pressed down, set power accordingly
            slides.setPower(-gamepad2.right_trigger);
        } else { //if no triggers, set power to 0, worm gear should hold arm in place
            slides.setPower(0);
        }

        //slides project onto phone
        telemetry.addData("Slides Position", slides.getPosition());
        dashTelemetry.addData("Slides Position", slides.getPosition());

        //HANGING CONTROLS -- CONTROLLER 1 bumper
        if (gamepad1.right_bumper) {
            highHang.goToHang(); //goes to the hanging position
        }
        if (gamepad1.left_bumper) {
            highHang.goBackDown(); //goes to the hanging position
        }

        //INTAKE CONTROLLERS, CONTROLLER 1 TRIGGERS
        if (gamepad1.left_trigger > .1) { //move down if the left trigger is pressed down, set power accordingly
            intakeRoller.intake(-gamepad1.left_trigger);
        } else if (gamepad1.right_trigger > .1) { //move up if right trigger is pressed down, set power accordingly
            intakeRoller.intake(gamepad1.right_trigger);
        } else { //if no triggers, set power to 0, worm gear should hold arm in place
            intakeRoller.intake(0);
        }

        if(distanceTester.returnDistance1() < distanceSensorDistance && distanceTester.returnDistance2() < distanceSensorDistance){
            pixelIn = 2;
            gamepad1.setLedColor(0, 255, 0, 1000);

        } else if (distanceTester.returnDistance1() < distanceSensorDistance || distanceTester.returnDistance2() < distanceSensorDistance){
            pixelIn = 1;
            gamepad1.setLedColor(255, 255, 0, 1000);
        } else {
            pixelIn = 0;
            gamepad1.setLedColor(255, 0, 0, 1000);
        }

        telemetry.addData("pixelIn: ", pixelIn);
        dashTelemetry.addData("pixelIn: ", pixelIn);

        //CARRIAGE PIVOT CONTROLS -- CONTROLLER 2
        if (gamepad2.left_bumper) {
            pixelCarriage.setPivotIntake(true); //faces intake
        }
        if (gamepad2.right_bumper) {
            pixelCarriage.setPivotIntake(false); //faces outtake
        }

        //CARRIAGE FLAP CONTROLS -- CONTROLLER 2
        if (gamepad2.y) { //if click a, pickup position set
            pixelCarriage.setCarriageOpen(true); //opens the carriage
        }
        if (gamepad2.a) { //place
            pixelCarriage.setCarriageOpen(false); //closes the carriage
        }

        //PLANE LAUNCHER
        if(gamepad1.dpad_up){
            planeLauncher.launchPlane();
        }

        //FLIP FLOP MANUAL CONTROLS
        if (gamepad1.dpad_left) {
            stackIntake.flipFlop(false);
        }
        if (gamepad1.dpad_right){
            stackIntake.flipFlop(true);
        }

        //reset pokey
        if(gamepad2.dpad_up) {
            pokeyClaw.resetPosition(true);
        }
        if (gamepad2.dpad_down) {
            pokeyClaw.resetPosition(false);
        }
        if (gamepad2.dpad_right) {
            pokeyClaw.increment(1);
        }
        if (gamepad2.dpad_left) {
            pokeyClaw.increment(-1);
        }


        if (gamepad2.x) {
            pokeyClaw.openClaw(false);
        }
        if (gamepad2.b) {
            pokeyClaw.openClaw(true);
        }

        if (gamepad1.b) {
            stackIntake.flipFlop();
        }


    }

}
