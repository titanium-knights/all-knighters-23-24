package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.*;

@Deprecated

public class Teleop_Golan extends OpMode {
    public PixelCarriage claw; //putting claw names the actual physical claw for use
    public IntakeRoller intakeRoller;

    @Override
    public void init() {
        claw=new PixelCarriage(hardwareMap);
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
        if (Math.abs(gamepad1.right_trigger) > .1) {
            intakeRoller.intake(gamepad1.right_trigger);
        }
        else if (Math.abs(gamepad1.left_trigger) > .1) {
            intakeRoller.intake(-gamepad1.left_trigger);
        }
        else {
            intakeRoller.intake(0);}
    }
}