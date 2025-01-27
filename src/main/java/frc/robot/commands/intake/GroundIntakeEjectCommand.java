package frc.robot.commands.intake;

import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LightsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class GroundIntakeEjectCommand extends LoggingCommand {

    private final IntakeSubsystem  intakeSubsystem;
    private final LightsSubsystem  lightsSubsystem;
    private final ShooterSubsystem shooterSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param shooterSubsystem The subsystem used by this command.
     */
    public GroundIntakeEjectCommand(IntakeSubsystem intakeSubsystem, LightsSubsystem lightsSubsystem,
        ShooterSubsystem shooterSubsystem) {
        this.intakeSubsystem  = intakeSubsystem;
        this.lightsSubsystem  = lightsSubsystem;
        this.shooterSubsystem = shooterSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        intakeSubsystem.setGroundSpeed(-IntakeConstants.GROUND_INTAKE_SPEED);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (isTimeoutExceeded(1.5)) {
            return true;
        }
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stop();
        logCommandEnd(interrupted);
    }
}