package org.firstinspires.ftc.teamcode.teleop;
//imports go up here, for ex. util classes or ftc library

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.*; //star allows us to import everything in the folder :)

@Deprecated
public class TeleOp_PracticeDay extends OpMode { //class header, we will always extend a TYPE of OpMode
    //public allows us to see this variable in the FTC Dashboard (a website that allows us to edit in realtime)
    //static = field that is shared across every instance of a class
    //double is a data type that allows us to use decimal points (others incl. int, string, float...)

    public static double DRIVE_SPEED = .8;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
    IntakeRoller intakeRoller;
    Arm arm;
    PixelCarriage pixelCarriage;

    public void setupDevices() {
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
        arm = new Arm(hardwareMap);
        pixelCarriage = new PixelCarriage(hardwareMap);
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

        //ARM CONTROLS
        if (gamepad1.left_trigger > .1) { //move back if the left trigger is pressed down, set power accordingly
            arm.setPower(-gamepad1.left_trigger);
        } else if (gamepad1.right_trigger > .1) { //move forward if right trigger is pressed down, set power accordingly
            arm.setPower(gamepad1.right_trigger);
        } else { //if no triggers, set power to 0, worm gear should hold arm in place
            arm.setPower(0);
        }

        //sliders: right=up; left=down
        if(gamepad1.right_bumper) {
//            slider.move(0.5);
        }
        if(gamepad1.left_bumper) {
//            slider.move(-0.5);
        }

//        //CLAW PIVOT CONTROLS
//        if (gamepad1.a) { //if click a, pickup position set
//            pixelClaw.setPivotPos("PICKUP");
//        }
//        if (gamepad1.b) { //place
//            pixelClaw.setPivotPos("PLACE");
//        }

        //CLAW CONTROLS
        if (gamepad1.y) { //if click a, pickup position set
            pixelCarriage.setPosition(.1);
        }
        if (gamepad1.a) { //place
            pixelCarriage.setPosition(0);
        }


    }

}
