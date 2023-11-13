package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.*;

public class Teleop_Ipek extends OpMode {
    public PixelClaw claw;
    public IntakeRoller intakeroller;
   //purple here->name
    //the files used before hand that your using here
    @Override
    public void init() {
        claw=new PixelClaw(hardwareMap);
        intakeroller= new IntakeRoller(hardwareMap);
        //for every public BLANK  make a =new
    }

    @Override
    public void loop() {
        //y=open    a=close

        if(gamepad1.y){
            claw.setPosition(0.5);
        }

        if(gamepad1.a){
            claw.setPosition(0.8);
        }
        // random numbers at the moment


        if(Math.abs(gamepad1.right_trigger)> .1) {
            intakeroller.intake(gamepad1.right_trigger);

        }else if(Math.abs(gamepad1.left_trigger)> .1){
            intakeroller.intake(-gamepad1.left_trigger);
            //negetive gamepad, to go down
        }else{
            intakeroller.intake(0);
        }
        //else are for when you are pressing nothing





    }
}
