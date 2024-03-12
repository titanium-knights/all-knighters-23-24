package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RR_Close {

            public static int VISION_ANG_LEFT = 45;
            public static int VISION_ANG_CENTER = 15;
            public static int VISION_ANG_RIGHT = -60;

            public static int VISION_ANG = VISION_ANG_CENTER; //actual angle
            public static Vector2d PURPLE_CENTER = new Vector2d(24, 0);
            public static Vector2d RESET_HOME = new Vector2d(0, 0);

            public static double INTAKE_POW = .8;
            public static int INTAKE_TIME = 2;
            public static double CARRIAGE_RAISE_TIME = 2;

            //backboard movement
            public static Pose2d BACKBOARD_DEFAULT = new Pose2d(25, 37, Math.toRadians(-90));

            public static Vector2d BACKBOARD_LEFT  = new Vector2d(22, 39);

            public static Vector2d BACKBOARD_RIGHT = new Vector2d(33, 39);

            public static Vector2d BACKBOARD_CENTER = new Vector2d(26, 39);

            public static Vector2d BACKBOARD_ADJUST = BACKBOARD_CENTER; //changes based on visualization

            public static Pose2d BEFORE_STACK_WAIT = new Pose2d(30, 30, Math.toRadians(-90));
            public static Vector2d STACK_WAIT = new Vector2d(48, -72);

            public static Vector2d TO_PARK_1 = new Vector2d(0, 37); //parking position ( full square)
            public static Vector2d TO_PARK_2 = new Vector2d(0, 42); //parking position ( full square)



            public static int position = 1; //default visual detect
    public static void main(String[] args) {

        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(0, 0, Math.toRadians(0)))//                        .addTemporalMarker(() -> { //high hang will go down in beginning of sequence for safety
//                            webcamServo.setPosition(false); //go down
//                        })
                        .lineToConstantHeading(PURPLE_CENTER)
                        .turn(Math.toRadians(VISION_ANG))
                        .waitSeconds(0.5)
//                        .addTemporalMarker(() -> {
//                            pokeyClaw.goToHalfPosition();
//                        })
//                        .waitSeconds(1.25)
//                        .addTemporalMarker(() -> {
//                            pokeyClaw.openClaw(true);
//                        })
                        .waitSeconds(.25)
//                        .addTemporalMarker(() -> {
//                            pokeyClaw.resetPosition(true);
//                        })
                        .waitSeconds(.5)
                        .lineToConstantHeading(RESET_HOME) //go back home (start pos)
                        .lineToLinearHeading(BACKBOARD_DEFAULT)
                        .lineTo(BACKBOARD_ADJUST) //adjusts for detection
                        .waitSeconds(1)

//                //dumping sequence
                        .waitSeconds(1.5)
//                        .addTemporalMarker(()->{
//                            slides.setPosition(SLIDE_POS_UP, SLIDE_POW); //slides up for dump
//                        })
                        .waitSeconds(2)
//                        .addTemporalMarker(() -> {
//                            carriage.setPivotIntake(false); //faces outtake
//                        })
                        .waitSeconds(CARRIAGE_RAISE_TIME)

                        .waitSeconds(1.5)
                        //dumping sequence
//                        .addTemporalMarker(()-> {
//                            carriage.setCarriageOpen(true);
//                        })//opens the carriage
                        .waitSeconds(1)
//                        .addTemporalMarker(()->{
//                            carriage.setPivotIntake(true); //faces outtake
//                        }) // <-- end of dumping sequence -->;
//                        .addTemporalMarker(()->{
//                            carriage.setCarriageOpen(false); //close carriage
//                        }) //end of all
                        .waitSeconds(.5) //slides down
//                        .addTemporalMarker(()->{
//                            slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
//                        })
                        //dumping done

                        .lineToLinearHeading(BEFORE_STACK_WAIT)
                        .lineTo(STACK_WAIT)
                        .waitSeconds(.5) //slides down
//                        .addTemporalMarker(()->{
//                            intake.intake(1);
//                        })
                        .waitSeconds(1) //slides down
                        .lineToLinearHeading(BEFORE_STACK_WAIT)
                        //dumping sequence
                        .addTemporalMarker(()-> {
//                            carriage.setCarriageOpen(true);
                        })//opens the carriage
                        .waitSeconds(1)
                        .addTemporalMarker(()->{
//                            carriage.setPivotIntake(true); //faces outtake
                        }) // <-- end of dumping sequence -->;
                        .addTemporalMarker(()->{
//                            carriage.setCarriageOpen(false); //close carriage
                        }) //end of all
                        .waitSeconds(.5) //slides down
                        .addTemporalMarker(()->{
                           // slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW); //slides up for dump
                        })
                        //dumping done

                        .waitSeconds(1)
                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
            .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}