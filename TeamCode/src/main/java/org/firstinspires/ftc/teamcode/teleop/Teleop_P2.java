package org.firstinspires.ftc.teamcode.teleop;
//imports go up here, for ex. util classes or ftc library

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.*; //star allows us to import everything in the folder :)

@TeleOp
public class Teleop_P2 extends OpMode { //class header, we will always extend a TYPE of OpMode
    //public allows us to see this variable in the FTC Dashboard (a website that allows us to edit in realtime)
    //static = field that is shared across every instance of a class
    //double is a data type that allows us to use decimal points (others incl. int, string, float...)

    public static double DRIVE_SPEED = .8;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
//    IntakeRoller intakeRoller;
    PixelCarriage pixelCarriage;
    Slides slides;
    ArmSystem armSystem;
    PlaneLauncher planeLauncher;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();



    public void setupDevices() {
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
        slides = new Slides(hardwareMap);
        pixelCarriage = new PixelCarriage(hardwareMap);
        armSystem = new ArmSystem(hardwareMap);
//        intakeRoller = new IntakeRoller(hardwareMap);
        planeLauncher = new PlaneLauncher(hardwareMap);
    }

    @Override
    public void init() { //initializes, connects
        setupDevices();
        armSystem.setArmPos(false, true);
    }

    @Override
    public void loop() { //constantly doing, repeating
        drive.teleOpRobotCentric(gamepad1, DRIVE_SPEED); //go drive vroom

        //gamepad controls return a float or boolean (either you press it .1 to 0 or you hit or dont hit it)
        //most of the loop code is nested in conditional statements

        //Intake CONTROLS -- CONTROLLER 1
//        if(Math.abs(gamepad1.right_trigger)> .1) {
//            intakeRoller.intake(gamepad1.right_trigger);
//
//        }else if(Math.abs(gamepad1.left_trigger)> .1){
//            intakeRoller.intake(-gamepad1.left_trigger);
//            //negative gamepad, to go down
//        }else{
//            intakeRoller.intake(0);
//        }

        //Slide CONTROLS -- CONTROLLER 2
        slides.withinBounds();
        if (gamepad2.left_trigger > .1) { //move down if the left trigger is pressed down, set power accordingly
            slides.setPower(-gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > .1) { //move up if right trigger is pressed down, set power accordingly
            slides.setPower(gamepad2.right_trigger);
        } else { //if no triggers, set power to 0, worm gear should hold arm in place
            slides.setPower(0);
        }
        //slides project onto phone
        telemetry.addData("Slides 1 (right) Position", slides.getPositionR());
        dashTelemetry.addData("Slides (right) Position", slides.getPositionR());
        telemetry.addData("Slides 2 (left) Position", slides.getPositionL());
        dashTelemetry.addData("Slides 2 (left) Position", slides.getPositionL());

        telemetry.addData("Slides (average) Position", slides.getAverage());
        dashTelemetry.addData("Slides (average) Position", slides.getAverage());


        //CARRIAGE PIVOT CONTROLS -- CONTROLLER 1
        if (gamepad1.x) {
            pixelCarriage.setPivotIntake(true); //faces intake
        }
        if (gamepad1.b) {
            pixelCarriage.setPivotIntake(false); //faces outtake
        }

        //CARRIAGE FLAP CONTROLS -- CONTROLLER 1
//        if (gamepad1.y) { //if click a, pickup position set
//            pixelCarriage.setCarriageOpen(true); //opens the carriage
//        }
//        if (gamepad1.a) { //place
//            pixelCarriage.setCarriageOpen(false); //closes the carriage
//        }

//        //ARM CONTROLS -- CONTROLLER 2
        if (gamepad2.x) {
            armSystem.setArmPos(true, false); //DOWN
        }
        if (gamepad2.b) { //place outtake
            armSystem.setArmPos(false, false);
        }

        //CLAW CONTROLS -- CONTROLLER 2
        if (gamepad2.a) {
            armSystem.setClawOpen(true); //OPEN
        }
        if (gamepad2.y) { //place
            armSystem.setClawOpen(false);
        }

        //PLANE LAUNCHER

        if(gamepad1.left_bumper){
            planeLauncher.launchPlane();
        }


    }

}
