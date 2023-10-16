package org.firstinspires.ftc.teamcode.teleop;
//imports go up here, for ex. util classes or ftc library

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.*; //star allows us to import everything in the folder :)

public class TeleOp_PracticeDay extends OpMode { //class header, we will always extend a TYPE of OpMode
    //public allows us to see this variable in the FTC Dashboard (a website that allows us to edit in realtime)
    //static = field that is shared across every instance of a class
    //double is a data type that allows us to use decimal points (others incl. int, string, float...)

    public static double DRIVE_SPEED = .8;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
    IntakeRoller intakeRoller;

    @Override
    public void init() { //initializes, connects
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
    }

    @Override
    public void loop() { //constantly doing, repeating
        drive.teleOpRobotCentric(gamepad1, DRIVE_SPEED); //go drive vroom

        //gamepad controls return a float or boolean (either you press it .1 to 0 or you hit or dont hit it)
        //most of the loop code is nested in conditional statements
        if (gamepad1.left_trigger != 0) {

        }

    }

}
