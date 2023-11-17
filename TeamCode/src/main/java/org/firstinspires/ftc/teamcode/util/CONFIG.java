package org.firstinspires.ftc.teamcode.util;

public class CONFIG {
    //We use the CONFIG to store all of our port numbers. This allows us to have them all in one place.

    //ch = control hub
    //eh = expansion hub

    //Drivetrain
    public static String FRONT_LEFT = "fl"; //ch 1
    public static String FRONT_RIGHT = "fr"; //ch 3
    public static String BACK_LEFT = "bl"; //ch 0
    public static String BACK_RIGHT = "br"; //ch 2

    //Odometry
    public static String O_C = "bl"; //center odo, ch 2
    public static String O_L = "fl"; //left odo, ch 0
    public static String O_R = "fr"; //right odo, ch 1

    //Subsystems
    public static String intakeMotor = "intakeMotor"; //eh 3
    public static String arm = "armServo"; //ch 3
    public static String claw = "clawServo"; //ch 2

    public static String clawSwivel = "";
    public static String carriagePivotL = "carriagePivotServoL"; //eh 1
    public static String carriagePivotR = "carriagePivotServoR"; //eh 2

    public static String carriageFlap = "carriageFlapServo"; //eh 0

    //slides
    public static String smr = "smr"; //eh 0
    public static String sml = "sml"; //eh 1



}
