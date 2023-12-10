package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RedBDetectPark {
    //values
    public static int START_Y = -62;
    public static int START_X = 12;
    public static double START_DEG = 90;

    public static int detectedVisionPos = 1;
    public static int VISION_ANG_LEFT = 90;
    public static int VISION_ANG_CENTER = 0;
    public static int VISION_ANG_RIGHT = -90;

    public static int VISION_ANG = VISION_ANG_LEFT; //actual angle
    public static Vector2d BEFORE_TURN = new Vector2d(START_X+0, START_Y+28);

    public static int TO_PARK_DEG = 0;

    public static Pose2d TO_PARK = new Pose2d(56, START_Y+28, Math.toRadians(TO_PARK_DEG));
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        if (detectedVisionPos == 1) {
            VISION_ANG = VISION_ANG_LEFT;
        } else if (detectedVisionPos == 2) {
            VISION_ANG = VISION_ANG_CENTER;
        } else {
            VISION_ANG = VISION_ANG_RIGHT;
        }

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(START_X, START_Y, Math.toRadians(START_DEG)))
                                        .lineToConstantHeading(BEFORE_TURN)
                                        .turn(Math.toRadians(VISION_ANG))
                                        .waitSeconds(0)
                                        .lineToSplineHeading(TO_PARK)
                                        .addTemporalMarker(()->{
//                    slides.setPosition(LIFT_LOWER_1, LIFT_POWER_DOWN);
//                    carriage.dump();
                                        })
                                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}