package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.util.*;
@Deprecated

//Welcome to our TeleOp code default
@TeleOp
public class TeleOp_Practice extends OpMode {
    //declare objects of subsystems
   // MecanumDrive drive; // aidawkfafhiuawf
    IntakeRoller intake;

    @Override //runs on startup of the app
    public void init() {
        //instantiate our ports
       // drive = new MecanumDrive(hardwareMap);
        intake = new IntakeRoller(hardwareMap);

    }

    @Override //runs continuously
    public void loop() {
        //conditionals for different buttons go here
    }
}
