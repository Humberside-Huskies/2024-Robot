package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants.AutoPattern;
import frc.robot.Constants.AutoConstants.AutoPatternPos;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutonomousCommand extends SequentialCommandGroup {

    Alliance alliance;

    public AutonomousCommand(AutoPattern autoPattern, AutoPatternPos autoPatternPos, DriveSubsystem driveSubsystem,
        ShooterSubsystem shooterSubsystem) {

        // Default is to do nothing.
        // If more commands are added, the instant command will end and
        // the next command will be executed.
        addCommands(new InstantCommand());

        alliance = DriverStation.getAlliance().orElse(null);

        StringBuilder sb = new StringBuilder();
        sb.append("Auto Selections");
        sb.append("\n   Auto Pattern  : ").append(autoPattern);
        sb.append("\n   Alliance      : ").append(alliance);

        System.out.println(sb.toString());

        // If any inputs are null, then there was some kind of error.
        if (autoPattern == null) {
            System.out.println("*** ERROR - null found in auto pattern builder ***");
            return;
        }

        // Print an error if the alliance is not set
        if (alliance == null) {
            System.out.println("*** ERROR **** null Alliance ");
            return;
        }

        /*
         * Compose the appropriate auto commands
         */

        // Determine which autopattern we will use. We first determine the initial position and than
        // later the type of shooting
        switch (autoPatternPos) {
            // This is for RIGHT AUTONOMOUS
            case RIGHT:
                switch (autoPattern) {

                    case DO_NOTHING:
                        return;

                    case SHOOT_SPEAKER:
                        addCommands(new DriveSpeakerCommand(driveSubsystem, shooterSubsystem, 0.15));
                        return;

                    case DRIVE_FORWARD_AFTER_DELAY:
                        addCommands(new DriveForwardOnlyCommand(driveSubsystem, shooterSubsystem));
                        return;
                }



                return;
            // This is for CENTER AUTONOMOUS
            case CENTER:
                switch (autoPattern) {

                    case DO_NOTHING:
                        return;

                    case SHOOT_SPEAKER:
                        addCommands(new DriveSpeakerCommand(driveSubsystem, shooterSubsystem, 0.15));
                        return;


                    case DRIVE_FORWARD_AFTER_DELAY:
                        addCommands(new DriveForwardOnlyCommand(driveSubsystem, shooterSubsystem));
                        return;
                }


                return;
            // This is for LEFT AUTONOMOUS
            case LEFT:

                switch (autoPattern) {

                    case DO_NOTHING:
                        return;

                    case SHOOT_SPEAKER:
                        addCommands(new DriveSpeakerCommand(driveSubsystem, shooterSubsystem, -0.15));
                        return;

                    case DRIVE_FORWARD_AFTER_DELAY:
                        addCommands(new DriveForwardOnlyCommand(driveSubsystem, shooterSubsystem));
                        return;
                }



                return;

        }
    }

}

