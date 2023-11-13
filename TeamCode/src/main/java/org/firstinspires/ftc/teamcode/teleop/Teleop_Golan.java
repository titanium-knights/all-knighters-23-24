package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.*;

public class Teleop_Golan extends OpMode {
    public PixelClaw claw; //putting claw names the actual physical claw for use
    public IntakeRoller intakeRoller;

    @Override
    public void init() {
        claw=new PixelClaw(hardwareMap);
        intakeRoller=new IntakeRoller(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.y){
            claw.setPosition(0.7);
        }
        if(gamepad1.a){
            claw.setPosition(0);
        }
    }
}