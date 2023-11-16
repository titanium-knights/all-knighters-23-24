package org.firstinspires.ftc.teamcode.teleop;
//imports go up here, for ex. util classes or ftc library

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.*; //star allows us to import everything in the folder :)

@TeleOp
public class Teleop_P1 extends OpMode { //class header, we will always extend a TYPE of OpMode
    //public allows us to see this variable in the FTC Dashboard (a website that allows us to edit in realtime)
    //static = field that is shared across every instance of a class
    //double is a data type that allows us to use decimal points (others incl. int, string, float...)

    public static double DRIVE_SPEED = .8;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
    IntakeRoller intakeRoller;
    PixelClaw pixelClaw;
    Slides slides;
    ArmSystem armSystem;


    public void setupDevices() {
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
        slides = new Slides(hardwareMap);
        pixelClaw = new PixelClaw(hardwareMap);
        armSystem = new ArmSystem(hardwareMap);
    }

    @Override
    public void init() { //initializes, connects
        setupDevices();
    }

    @Override
    public void loop() { //constantly doing, repeating
        drive.teleOpRobotCentric(gamepad1, DRIVE_SPEED); //go drive vroom

        //gamepad controls return a float or boolean (either you press it .1 to 0 or you hit or dont hit it)
        //most of the loop code is nested in conditional statements

        //Intake CONTROLS
        if(Math.abs(gamepad1.right_trigger)> .1) {
            intakeRoller.intake(gamepad1.right_trigger);

        }else if(Math.abs(gamepad1.left_trigger)> .1){
            intakeRoller.intake(-gamepad1.left_trigger);
            //negative gamepad, to go down
        }else{
            intakeRoller.intake(0);
        }

        //Slide CONTROLS -- CONTROLLER 2
        if (gamepad2.left_trigger > .1) { //move back if the left trigger is pressed down, set power accordingly
            slides.setPower(-gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > .1) { //move forward if right trigger is pressed down, set power accordingly
            slides.setPower(gamepad2.right_trigger);
        } else { //if no triggers, set power to 0, worm gear should hold arm in place
            slides.setPower(0);
        }

        //CARRIAGE PIVOT CONTROLS -- CONTROLLER 1
        if (gamepad1.x) {
            pixelClaw.setPivotIntake(true); //faces intake
        }
        if (gamepad1.b) {
            pixelClaw.setPivotIntake(false); //faces outtake
        }

        //CLAW CONTROLS -- CONTROLLER 1
        if (gamepad1.y) { //if click a, pickup position set
            pixelClaw.setCarriageOpen(true); //opens the carriage
        }
        if (gamepad1.a) { //place
            pixelClaw.setCarriageOpen(false); //closes the carriage
        }

        //ARM CONTROLS -- CONTROLLER 2
        if (gamepad2.x) {
            armSystem.setArmPos(true); //DOWN
        }
        if (gamepad1.b) { //place outtake
            armSystem.setArmPos(false);
        }

        //CLAW CONTROLS -- CONTROLLER 1
        if (gamepad1.y) {
            armSystem.setClawOpen(true); //OPEN
        }
        if (gamepad1.a) { //place
            armSystem.setClawOpen(false);
        }


    }

}