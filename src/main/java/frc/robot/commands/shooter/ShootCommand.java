package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends LoggingCommand {

    private final ShooterSubsystem shooterSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public ShootCommand(ShooterSubsystem shooterSubsystem) {

        this.shooterSubsystem = shooterSubsystem;

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

        // Run the shooter wheel
        shooterSubsystem.setShooterSpeed(ShooterConstants.SHOOTER_SHOOT_SPEED);

        // If this command has been running for 2 seconds, then start the feeder
        if (isTimeoutExceeded(2.0)) {
            shooterSubsystem.setFeederSpeed(ShooterConstants.FEEDER_SHOOT_SPEED);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        // Stop this command after 4 seconds total
        if (isTimeoutExceeded(4.0)) {
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