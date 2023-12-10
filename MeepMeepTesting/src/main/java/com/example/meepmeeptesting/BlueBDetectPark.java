package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueBDetectPark {
    //values
    public static int START_Y = 56;
    public static int START_X = 10;
    public static double START_DEG = Math.toRadians(-90);

    public static int VISION_ANG_LEFT = -90;
    public static int VISION_ANG_CENTER = 0;
    public static int VISION_ANG_RIGHT = 90;

    public static int VISION_ANG = VISION_ANG_CENTER; //actual angle
    public static Vector2d BEFORE_TURN = new Vector2d(START_X+0, START_Y-28);

    public static Vector2d TO_PARK = new Vector2d(START_X+50, START_Y-28);
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(START_X, START_Y, START_DEG))
                                .lineToConstantHeading(BEFORE_TURN)
                                .turn(Math.toRadians(VISION_ANG))
                                .waitSeconds(0)
                                .addTemporalMarker(()->{
//                    slides.setPosition(LIFT_LOWER_1, LIFT_POWER_DOWN);
//                    carriage.dump();
                                })
                                .lineToConstantHeading(TO_PARK)

                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}