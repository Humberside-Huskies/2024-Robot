package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.LightsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DefaultShooterCommand extends LoggingCommand {

    private final ShooterSubsystem             shooterSubsystem;
    private final LightsSubsystem              lightsSubsystem;

    private final ShooterConstants.shooterType shooterType;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public DefaultShooterCommand(ShooterSubsystem shooterSubsystem, LightsSubsystem lightsSubsystem,
        ShooterConstants.shooterType shooterType) {

        this.shooterSubsystem = shooterSubsystem;
        this.lightsSubsystem  = lightsSubsystem;
        this.shooterType      = shooterType;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(shooterSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (shooterType == ShooterConstants.shooterType.SpeakerShooter) {
            // Run the shooter wheel
            shooterSubsystem.setShooterSpeed(ShooterConstants.SHOOTER_SHOOT_SPEAKER_SPEED);
            // If this command has been running for 0.5 seconds, then start the feeder
            if (isTimeoutExceeded(0.5)) {
                shooterSubsystem.setFeederSpeed(ShooterConstants.FEEDER_SHOOT_SPEAKER_SPEED);
            }
        }
        else if (shooterType == ShooterConstants.shooterType.AMPShooter) {
            // Run the shooter wheel
            shooterSubsystem.setShooterSpeed(ShooterConstants.SHOOTER_SHOOT_AMP_SPEED);
            // If this command has been running for 2 seconds, then start the feeder
            if (isTimeoutExceeded(0.5)) {
                shooterSubsystem.setFeederSpeed(ShooterConstants.FEEDER_SHOOT_AMP_SPEED);
            }
        }

        lightsSubsystem.setLEDRainbow();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // Stop this command after 2.5 seconds total
        // FIX check if note is loaded if it isn't spin for 0.5 second more and then stop
        if (isTimeoutExceeded(2.5) || (shooterSubsystem.isNoteLoaded() && isTimeoutExceeded(1))) {
            setFinishReason("Shot fired");
            return true;
        }
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
        logCommandEnd(interrupted);
    }
}