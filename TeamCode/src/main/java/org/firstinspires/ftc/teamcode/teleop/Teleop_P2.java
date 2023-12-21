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

    public static double DRIVE_SPEED_FAST = .8;
    public static double DRIVE_SPEED_SLOW = .4;
    public static double DRIVE_SPEED_CURRENT = DRIVE_SPEED_FAST;

    public static boolean isSlowmode = false;

    //make instance of the classes (i.e, subsystems or dt)
    MecanumDrive drive; //no value
    IntakeRoller intakeRoller;
    PixelCarriage pixelCarriage;
    Slides slides;
    PlaneLauncher planeLauncher;
    HighHang highHang;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();



    public void setupDevices() {
        //hardwareMap = phone/ android app, connects the name of the ports to the actual object
        drive = new MecanumDrive(hardwareMap); //connecting ports to
        slides = new Slides(hardwareMap);
        pixelCarriage = new PixelCarriage(hardwareMap);
        intakeRoller = new IntakeRoller(hardwareMap);
        planeLauncher = new PlaneLauncher(hardwareMap);
        highHang = new HighHang(hardwareMap);
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

        //SLOW MODE
        if (gamepad1.b) { //slowmode added
            isSlowmode = !isSlowmode;
        }

        telemetry.addData("isSlowmode: ", isSlowmode);
        dashTelemetry.addData("isSlowmode: ", isSlowmode);

        //Slide CONTROLS -- CONTROLLER 1
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

        //HANGING CONTROLS -- CONTROLLER 1 D pad up
        if (gamepad1.dpad_up) {
            highHang.goToHang(); //goes to the hanging position
        }
        if (gamepad1.dpad_down) {
            highHang.goBackDown(); //goes to the hanging position
        }

        //INTAKE CONTROLLERS, CONTROLLER 1 TRIGGERS
        if (gamepad1.left_trigger > .1) { //move down if the left trigger is pressed down, set power accordingly
            intakeRoller.intake(gamepad1.left_trigger);
        } else if (gamepad1.right_trigger > .1) { //move up if right trigger is pressed down, set power accordingly
            intakeRoller.intake(-gamepad1.right_trigger);
        } else { //if no triggers, set power to 0, worm gear should hold arm in place
            intakeRoller.intake(0);
        }

        //INTAKE STACK CONTROLLERS, CONTROLLER 1 TRIGGERS
        if (gamepad1.y) {
            intakeRoller.getFromStack(false); //idle
        }
        if (gamepad1.a) {
            intakeRoller.getFromStack(true); //getStack
        }

        //CARRIAGE PIVOT CONTROLS -- CONTROLLER 1
        if (gamepad2.x) {
            pixelCarriage.setPivotIntake(true); //faces intake
        }
        if (gamepad2.b) {
            pixelCarriage.setPivotIntake(false); //faces outtake
        }

        //CARRIAGE FLAP CONTROLS -- CONTROLLER 1
        if (gamepad2.y) { //if click a, pickup position set
            pixelCarriage.setCarriageOpen(true); //opens the carriage
        }
        if (gamepad2.a) { //place
            pixelCarriage.setCarriageOpen(false); //closes the carriage
        }

        //PLANE LAUNCHER
        if(gamepad2.dpad_up){
            planeLauncher.launchPlane();
        }



    }

}
