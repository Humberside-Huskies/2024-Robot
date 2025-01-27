// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Global constants
    public static final double DEFAULT_COMMAND_TIMEOUT_SECONDS = 5;

    public static final class SafetyConstants {
        public static double DRIVE_SPEED_MULTIPLIER_SAFETY_ON = 0.5;
        public static double DRIVE_SPEED_MULTIPLIER           = 1.0;
    }

    public static final class AutoConstants {
        public static enum AutoPattern {
            DO_NOTHING,
            SHOOT_SPEAKER_AND_DRIVE,
            SHOOT_AMP,
            SHOOT_SPEAKER,
            DRIVE_OUT,
            TEST
        };

        public static enum AutoPosition {
            LEFT, CENTER, RIGHT;
        }
    }

    public static final class DriveConstants {

        public static enum DriveMode {
            TANK, SINGLE_STICK_ARCADE, DUAL_STICK_ARCADE;
        }

        public static final double  SLEW_LIMIT                    = 500;         // 1.5;
        public static final int     LEFT_MOTOR_PORT               = 10;
        public static final int     RIGHT_MOTOR_PORT              = 20;

        public static final boolean LEFT_MOTOR_REVERSED           = false;
        public static final boolean RIGHT_MOTOR_REVERSED          = true;

        public static final boolean LEFT_ENCODER_REVERSED         = true;
        public static final boolean RIGHT_ENCODER_REVERSED        = false;

        public static final double  ENCODER_COUNTS_PER_REVOLUTION = 16.8;
        public static final double  ROBOT_WHEEL_DIAMETER_CMS      = 6 * 2.54;

        public static final double  CMS_PER_ENCODER_COUNT         =
            // Assumes the encoders are directly mounted on the wheel shafts
            (ROBOT_WHEEL_DIAMETER_CMS * Math.PI) / ENCODER_COUNTS_PER_REVOLUTION;

    }

    public static final class ClimbConstants {
        public static final int    RIGHT_MOTOR_PORT    = 40;
        public static final int    LEFT_MOTOR_PORT     = 41;

        public static final double CLIMBER_MOTOR_SPEED = 0.5;
        public static final double RETRACT_MOTOR_SPEED = -0.65;
    }

    public static final class ShooterConstants {
        public static final int SHOOTER_MOTOR_PORT = 31;
        public static final int FEEDER_MOTOR_PORT  = 30;

        public static enum shooterType {
            AMPShooter, SpeakerShooter, PassShooter, TrapShooter
        };

        public static final double SHOOTER_SHOOT_SPEAKER_SPEED = -1;
        public static final double FEEDER_SHOOT_SPEAKER_SPEED  = -1;

        public static final double SHOOTER_SHOOT_AMP_SPEED     = -0.2;
        public static final double FEEDER_SHOOT_AMP_SPEED      = -0.3;

        public static final double SHOOTER_SHOOT_TRAP_SPEED    = -0.9;
        public static final double FEEDER_SHOOT_TRAP_SPEED     = -0.9;

        public static final double SHOOTER_SHOOT_PASS_SPEED    = -0.5;
        public static final double FEEDER_SHOOT_PASS_SPEED     = -0.5;

        // public static final double SHOOTE =
    }

    public static final class IntakeConstants {
        public static final int    GROUND_MOTOR_PORT   = 51;

        public static final double GROUND_INTAKE_SPEED = 1;
        public static final double FEEDER_INTAKE_SPEED = -0.5;
    }

    public static final class OperatorConstants {

        public static final int    DRIVER_CONTROLLER_PORT         = 0;
        public static final double GAME_CONTROLLER_STICK_DEADBAND = 0.2;
    }

    public static final class LightsConstants {
        public static final int LED_PORT = 0;
        public static final int NUM_LEDS = 30;
    }

    public static final class VisionConstants {
        // how many degrees back is your limelight rotated from perfectly vertical?
        public static final double LIMELIGHT_MOUNT_ANGLE_DEG = 27.0;
        // distance from the center of the Limelight lens to the floor
        public static final double LIMELIGHT_LENS_HEIGHT_INCHES = 22.0;
        // distance from the target to the floor
        public static final double GOAL_HEIGHT_INCHES = 60.0;
    }


}

