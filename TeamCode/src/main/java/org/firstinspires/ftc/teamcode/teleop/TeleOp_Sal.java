package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.*;

public class TeleOp_Sal extends OpMode {
    public PixelClaw claw;
    public IntakeRoller roller;
    public Slides ...;
    @Override
    public void init() {
        claw = new PixelClaw(hardwareMap);
        roller = new IntakeRoller(hardwareMap);
        ... = new Slides(hardwareMap);

    }
    @Override
    public void loop() {
        //y=open; a=close
        //right=open; left=close
        if (gamepad1.y) {
            claw.setPosition(54098.45098678905);
        }
        if (gamepad1.a) {
            claw.setPosition(4875726948.4598673);
        }
        if (Math.abs(gamepad1.right_trigger) > 0.1) {
            roller.intake(gamepad1.right_trigger);
        } else if (Math.abs(gamepad1.left_trigger) > 0.1) {
            roller.intake(gamepad1.left_trigger * -1);
        } else {
            roller.intake(0);
        }
    }
}
