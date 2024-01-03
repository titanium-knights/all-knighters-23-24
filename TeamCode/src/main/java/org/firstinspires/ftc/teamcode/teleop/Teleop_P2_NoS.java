package org.firstinspires.ftc.teamcode.teleop;
//imports go up here, for ex. util classes or ftc library

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.*; //star allows us to import everything in the folder :)

public class Teleop_P2_NoS extends OpMode { //class header, we will always extend a TYPE of OpMode
    //public allows us to see this variable in the FTC Dashboard (a website that allows us to edit in realtime)
    //static = field that is shared across every instance of a class
    //double is a data type that allows us to use decimal points (others incl. int, string, float...)

    public static double DRIVE_SPEED_FAST = .8;
    public static double DRIVE_SPEED_SLOW = .4;
    public static double DRIVE_SPEED_CURRENT = DRIVE_SPEED_FAST;

    public static boolean isSlowmode = false;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
    //    IntakeRoller intakeRoller;
    ArmSystem armSystem;
    PlaneLauncher planeLauncher;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();



    public void setupDevices() {
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
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
        if (isSlowmode) {
            DRIVE_SPEED_CURRENT = DRIVE_SPEED_SLOW;
        } else {
            DRIVE_SPEED_CURRENT = DRIVE_SPEED_FAST;
        }

        drive.teleOpRobotCentric(gamepad1, DRIVE_SPEED_CURRENT); //go drive vroom

        //SLOW MODE
        if (gamepad1.b) { //slowmode added
            isSlowmode = !isSlowmode;
        }

//        //ARM CONTROLS -- CONTROLLER 2
        if (gamepad2.x) {
            armSystem.setArmPos(true, false); //DOWN
        }

        if (gamepad2.dpad_down) {
            armSystem.setArmPos(true, false, 0); //DOWN LOW
        }
        if (gamepad2.dpad_left) {
            armSystem.setArmPos(true, false, 1); //PICK UP STACK
        }
        if (gamepad2.dpad_up) {
            armSystem.setArmPos(true, false, 2); //DOWN MID
        }
        if (gamepad2.dpad_right) {
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
        if(gamepad2.left_bumper){
            planeLauncher.launchPlane();
        }


    }

}
