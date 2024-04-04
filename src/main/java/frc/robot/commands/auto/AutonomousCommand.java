package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.AutoConstants.AutoPattern;
import frc.robot.Constants.AutoConstants.AutoPosition;
import frc.robot.Constants.ShooterConstants.shooterType;
import frc.robot.commands.drive.DriveForwardCommand;
import frc.robot.commands.drive.DriveRotateCommand;
import frc.robot.commands.shooter.DefaultShooterCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class AutonomousCommand extends SequentialCommandGroup {

    Alliance alliance;

    public AutonomousCommand(AutoPattern autoPattern, AutoPosition autoPosition, int autoDelay, DriveSubsystem driveSubsystem,
        ShooterSubsystem shooterSubsystem, ClimbSubsystem climbSubsystem, VisionSubsystem visionSubsystem,
        LightsSubsystem lightsSubsystem,
        IntakeSubsystem intakeSubsystem) {

        // Default is to do nothing.
        // If more commands are added, the instant command will end and
        // the next command will be executed.
        addCommands(new InstantCommand());

        alliance = DriverStation.getAlliance().orElse(null);

        StringBuilder sb = new StringBuilder();
        sb.append("Auto Selections");
        sb.append("\n   Delay (sec)   : ").append(autoDelay);
        sb.append("\n   Auto Pattern  : ").append(autoPattern);
        sb.append("\n   Auto Position : ").append(autoPosition);
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

        // addCommands(new ClimbDownCommand(climbSubsystem));

        // Add a delay if required
        if (autoDelay > 0) {
            addCommands(new WaitCommand(autoDelay));
        }

        // Determine which autopattern we will use. We first determine the initial position and than
        // later the type of shooting
        switch (autoPattern) {
        case DO_NOTHING:
            break;

        case SHOOT_SPEAKER_AND_DRIVE:
            addCommands(
                new DefaultShooterCommand(shooterSubsystem, lightsSubsystem, intakeSubsystem,
                    shooterType.SpeakerShooter));

            addCommands(new DriveForwardCommand(driveSubsystem, 300, -0.15));
            // if (alliance.equals(Alliance.Blue) && autoPosition.equals(AutoPosition.LEFT))
            addCommands(new DriveRotateCommand(driveSubsystem, 180, 0.1));
            addCommands(new DriveForwardCommand(driveSubsystem, 350, 0.20));
            break;

        case DRIVE_OUT:
            addCommands(new DriveForwardCommand(driveSubsystem, 5, -0.1));
            addCommands(new DriveRotateCommand(driveSubsystem, 180, 0.02));
            addCommands(new DriveForwardCommand(driveSubsystem, 30, 1));

            break;

        case SHOOT_SPEAKER:
            addCommands(
                new DefaultShooterCommand(shooterSubsystem, lightsSubsystem, intakeSubsystem, shooterType.SpeakerShooter));
            break;

        case SHOOT_AMP:
            // someone should do code this :/
            break;

        case TEST:
            break;
        }
    }
}
