//package org.firstinspires.ftc.teamcode.util;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Vector2d;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import java.util.Arrays;
//
///**
// * Util Class for Mecanum DriveTrains
// *
// * @author Ari Kamat - Ex Programming Lead of Titanium Knights
// */
//public class MecanumDrive {
//    public DcMotorEx[] motors = new DcMotorEx[4]; // Array contains 4 DcMotors in the following order: front-left (fl),
//    // front-right (fr), back-left (bl), and back-right (br)
//
//    /**
//     * Constructor for Drive class
//     * Should only be used if robot uses a Mecanum Drivetrain
//     * Retrieves references to DcMotors through passed in hardware map and assigns
//     * them as elements in the motors array
//     * Due to nature of a Mecanum drivetrain the 2 left motors' direction need to be
//     * reversed
//     * Ensures that all the motors are set to brake when it is assigned to run at 0
//     * power
//     *
//     * @param hmap reference to OpMode's HardwareMap
//     *
//     */
//    public MecanumDrive(HardwareMap hmap) {
//        motors[0] = hmap.get(DcMotorEx.class, Config.FRONT_LEFT);
//        motors[0].setDirection(DcMotorSimple.Direction.REVERSE);
//        motors[1] = hmap.get(DcMotorEx.class, Config.FRONT_RIGHT);
//        motors[2] = hmap.get(DcMotorEx.class, Config.BACK_LEFT);
//        motors[2].setDirection(DcMotorSimple.Direction.REVERSE);
//        motors[3] = hmap.get(DcMotorEx.class, Config.BACK_RIGHT);
//        for (DcMotorEx motor : motors) {
//            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        }
//    }
//
//    /**
//     * Sets raw power values to all four wheels based on the 4 power values that are
//     * passed
//     *
//     * @param fl power applied to front left motor
//     * @param fr power applied to front right motor
//     * @param bl power applied to back left motor
//     * @param br power applied to back right motor
//     */
//    public void setRawPowers(double fl, double fr, double bl, double br) {
//        double[] powers = { fl, fr, bl, br };
//        setRawPowers(powers);
//    }
//
//    /**
//     * Sets raw power values to all four wheels/motors via an array of motor-power
//     * values
//     *
//     * @param powers array of doubles that represent the powers. Array is of the
//     *               following structure: {fl, fr, bl, br}
//     */
//    public void setRawPowers(double[] powers) {
//        int i = 0;
//        // iterate through the array and set the motor's power to the respective power
//        // given in the powers array
//        for (DcMotorEx motor : motors) {
//            motor.setPower(powers[i]);
//            i++;
//        }
//    }
//
//    /**
//     * Uses Mecanum kinematic equations to find power for all 4 motors based on x y
//     * and rotational powers required. Also scaled up and down if necessary
//     *
//     * @param x             power in the horizontal or x direction
//     * @param y             power in the vertical or y direction
//     * @param rot           rotational power
//     * @param speedModifier double that represents how much scaling is desired for
//     *                      powers
//     */
//    public void setPower(double x, double y, double rot, double speedModifier) {
//        double[] power = {
//                y + x + rot, // front-left motor
//                y - x - rot, // front-right motor
//                y - x + rot, // back-left motor
//                y + x - rot, // back-right motor
//        };
//
//        // uniformly scale up/down the powers on all 4 drivetrain motors based on the
//        // speed multiplier that wants to be utilized
//        for (int i = 0; i < power.length; i++) {
//            power[i] *= speedModifier;
//        }
//
//        // motors can only be set a power in the interval [-1, 1]
//        // due to scaling and addition of x y and rot terms, an element in the power
//        // array could be exceeding this bounds
//        // the 4 power values need to now be mapped back into the interval but maintain
//        // its relative ratios
//        // finding the power with maximum magnitude and then dividing all the powers
//        // by that value will ensure that the maximum magnitude of power will be 1
//        double max = Math.max(
//                Math.abs(Arrays.stream(power).max().getAsDouble()),
//                Math.abs(Arrays.stream(power).min().getAsDouble()));
//        if (max > 1) {
//            for (int i = 0; i < 4; i++) {
//                power[i] /= max;
//            }
//        }
//
//        setRawPowers(power);
//    }
//
//    /**
//     * Often, during opModes, the speedModifier is irrelevant as it is desired to go
//     * at the speed that the drivetrain is supposed to go at without modifications
//     * In this case, the overloaded setPower method is invoked with a speedModifier
//     * parameter of 1
//     *
//     * @param x   power in the horizontal or x direction
//     * @param y   power in the vertical or y direction
//     * @param rot rotational power
//     */
//    public void setPower(double x, double y, double rot) {
//        setPower(x, y, rot, 1);
//    }
//
//    /**
//     * Returns a double array of length 4 containing the values of the 4 motors'
//     * individual powers
//     *
//     * @param x   power in the x direction
//     * @param y   power in the y direction
//     * @param rot rotational power
//     * @return double array of powers for all 4 motors.
//     */
//    public double[] getPowerArr(double x, double y, double rot) {
//
//        double[] power = {
//                y + x + rot, // front-left motor
//                y - x - rot, // front-right motor
//                y - x + rot, // back-left motor
//                y + x - rot, // back-right motor
//        };
//
//        // motors can only be set a power in the interval [-1, 1]
//        // due to scaling and addition of x y and rot terms, an element in the power
//        // array could be exceeding this bounds
//        // the 4 power values need to now be mapped back into the interval but maintain
//        // its relative ratios
//        // finding the power with maximum magnitude and then dividing all the powers
//        // by that value will ensure that the maximum magnitude of power will be 1
//
//        double max = Math.max(
//                Math.abs(Arrays.stream(power).max().getAsDouble()),
//                Math.abs(Arrays.stream(power).min().getAsDouble()));
//        if (max > 1) {
//            for (int i = 0; i < 4; i++) {
//                power[i] /= max;
//            }
//        }
//        return power;
//    }
//
//    /**
//     * Sets Power using the setPower(double x, double y, double rot) method but
//     * takes in x and y powers through a Vector2d data type. assumes desired
//     * rotation power is 0
//     *
//     * @param v Vector2d of x and y powers
//     */
//    public void setPower(Vector2d v) {
//        setPower(v.getX(), v.getY(), 0);
//    }
//
//    /**
//     * Sets Power using the setPower(double x, double y, double rot) method but
//     * takes in x, y, and rotational powers through a Pose2d data type
//     *
//     * @param p Pose2d of x, y,and Rot powers
//     */
//    public void setPower(Pose2d p) {
//        setPower(p.getX(), p.getY(), p.getHeading());
//    }
//
//    /**
//     * Returns an array of all motors' encoder values as an array
//     *
//     * @return An integer array of encoder values in form {fl,fr,bl,br}
//     */
//    public int[] getEncoderVals() {
//        return new int[] {
//                motors[0].getCurrentPosition(),
//                motors[1].getCurrentPosition(),
//                motors[2].getCurrentPosition(),
//                motors[3].getCurrentPosition()
//        };
//    }
//
//    /**
//     * Sets position of all drive encoders based on an input array formatted as {fl,
//     * fr, bl, br}
//     *
//     * @param pos array of all target encoder positions
//     */
//    public void setTargetPosition(int[] pos) {
//        for (int i = 0; i < motors.length; i++) {
//            motors[i].setTargetPosition(pos[i]);
//        }
//    }
//
//    /**
//     * Sets the mode for all 4 drive motors at once
//     *
//     * @param mode The mode that all drive motors need to be set to
//     */
//    public void setMode(DcMotorEx.RunMode mode) {
//        for (DcMotorEx motor : motors) {
//            motor.setMode(mode);
//        }
//    }
//
//    /**
//     * Method to be called in teleop if robot-centric movement is desired
//     * Takes in appropriate input from gamepads and invokes the setPower method with
//     * the respective values as parameters
//     *
//     * @param g1 gamepad1 or gamepad that controls driving.
//     */
//    public void teleOpRobotCentric(Gamepad g1) {
//        // x-axis on left joy stick controls movement along x-direction - this is called
//        // strafing
//        // y-axis on left joy stick controls movement along y-direction - this
//        // forward/backwards movement
//        // x-axis on right joy stick controls rotation
//        setPower(g1.left_stick_x, -g1.left_stick_y, g1.right_stick_x);
//    }
//
//    /**
//     * Method to be called in teleop if robot-centric movement is desired
//     * Takes in appropriate input from gamepads and invokes the setPower method with
//     * the respective values as parameters
//     * Also scales powers down or up if desired, typically used for "slow-mode" for
//     * precise movements when driving
//     *
//     * @param g1            gamepad1 or gamepad that controls driving.
//     * @param speedModifier double that represents how much scaling is desired for
//     *                      powers
//     */
//    public void teleOpRobotCentric(Gamepad g1, double speedModifier) {
//
//        setPower(
//                g1.left_stick_x,
//                -g1.left_stick_y,
//                g1.right_stick_x,
//                speedModifier);
//    }
//
//    /**
//     * In a robot-centric driving, forwards means the robot driving straight
//     * forwards and backwards means the robot drives straight reverse
//     * In a field-centric driving environment, forward means the robot drives
//     * straight in the direction away from the driver and backwards means the robot
//     * drives straight towards the driver
//     * irrespective of the orientation of the robot.
//     * It performs these movements by knowledge of its orientations and compensating
//     * for that through logic that involves trigonometric functions
//     *
//     * @param g1  gamepad1 or gamepad that controls driving.
//     * @param imu an object of the IMU class that will enable knowing robot's
//     *            orientation
//     */
//    public void teleOpFieldCentric(Gamepad g1, IMU imu) {
//        // robot's angle or orientation needs to be known first
//        double angle = Math.toRadians(imu.getZAngle());
//
//        double inputY = -g1.left_stick_y;
//        double inputX = g1.left_stick_x;
//        double rot = g1.right_stick_x;
//
//        // Given its angle, modify x and y such that they represent the actual movement
//        // needed for movement to no longer be dependent on orientation of the robot
//        double x = Math.cos(angle) * inputX - Math.sin(angle) * inputY;
//        double y = Math.sin(angle) * inputX + Math.cos(angle) * inputY;
//        setPower(x, y, rot);
//    }
//
//    /**
//     * In a robot-centric driving, forwards means the robot driving straight
//     * forwards and backwards means the robot drives straight reverse
//     * In a field-centric driving environment, forward means the robot drives
//     * straight in the direction away from the driver and backwards means the robot
//     * drives straight towards the driver
//     * irrespective of the orientation of the robot.
//     * It performs these movements by knowledge of its orientations and compensating
//     * for that through logic that involves trigonometric functions
//     *
//     * @param g1      gamepad1 or gamepad that controls driving.
//     * @param degrees a double that represents the current heading of the robot in
//     *                degrees
//     */
//    public void teleOpFieldCentric(Gamepad g1, double degrees) {
//        double angle = Math.toRadians(degrees);
//        double inputY = -g1.left_stick_y;
//        double inputX = g1.left_stick_x;
//        double rot = g1.right_stick_x;
//        double x = Math.cos(angle) * inputX - Math.sin(angle) * inputY;
//        double y = Math.sin(angle) * inputX + Math.cos(angle) * inputY;
//        setPower(x, y, rot);
//    }
//
//    /**
//     * In a robot-centric driving, forwards means the robot driving straight
//     * forwards and backwards means the robot drives straight reverse
//     * In a field-centric driving environment, forward means the robot drives
//     * straight in the direction away from the driver and backwards means the robot
//     * drives straight towards the driver
//     * irrespective of the orientation of the robot.
//     * It performs these movements by knowledge of its orientations and compensating
//     * for that through logic that involves trigonometric functions
//     *
//     * @param g1  gamepad1 or gamepad that controls driving.
//     * @param rad a double that represents the current heading of the robot in
//     *            degrees
//     */
//    public void teleOpFieldCentricRad(Gamepad g1, double rad) {
//        double inputY = -g1.left_stick_y;
//        double inputX = g1.left_stick_x;
//        double rot = g1.right_stick_x;
//        double x = Math.cos(rad) * inputX - Math.sin(rad) * inputY;
//        double y = Math.sin(rad) * inputX + Math.cos(rad) * inputY;
//        setPower(x, y, rot);
//    }
//
//    /**
//     * Method to check if all motors are busy at a given point
//     *
//     * @return A boolean if all motors are busy
//     */
//    public boolean allMotorsBusy() {
//        boolean a = true;
//        for (DcMotorEx motor : motors) {
//            a = a && motor.isBusy();
//        }
//        return a;
//    }
//
//    /**
//     * Stops all motors
//     * Sets all 4 motors to run at power of 0
//     */
//    public void stop() {
//        setRawPowers(0, 0, 0, 0);
//    }
//
//    /**
//     * Sets power to drivetrain such that it will turn right at the desired power
//     *
//     * @param power power that the drivetrain should turn right at
//     */
//    public void turnRightWithPower(double power) {
//        setPower(new Pose2d(0, 0, power));
//    }
//
//    /**
//     * Sets power to dt such that it will turn left at desired power
//     *
//     * @param power power that the drivetrain should turn left at
//     */
//    public void turnLeftWithPower(double power) {
//        setPower(new Pose2d(0, 0, -power));
//    }
//
//    /**
//     * Sets power to the drivetrain such that it will strafe right at a certain
//     * power
//     *
//     * @param power power that the drivetrain should strafe right at
//     */
//    public void strafeRightWithPower(double power) {
//        setPower(new Pose2d(power, 0, 0));
//    }
//
//    /**
//     * Sets power to the drivetrain such that it will strafe left at a certain power
//     *
//     * @param power power that the drivetrain should strafe left at
//     */
//    public void strafeLeftWithPower(double power) {
//        setPower(new Pose2d(-power, 0, 0));
//    }
//
//    /**
//     * Sets power to the drivetrain such that it will drive forward at a certain
//     * power
//     *
//     * @param power power that the drivetrain should drive forward at
//     */
//    public void forwardWithPower(double power) {
//        setPower(new Pose2d(0, power, 0));
//    }
//
//    /**
//     * Sets power to the drivetrain such that it will drive backwards at a certain
//     * power
//     *
//     * @param power power that the drivetrain should drive backwards at
//     */
//    public void backwardWithPower(double power) {
//        setPower(new Pose2d(0, -power, 0));
//    }
//
//}