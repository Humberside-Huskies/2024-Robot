package frc.robot.commands.climb;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class DefaultClimbCommand extends LoggingCommand {

    private final ClimbSubsystem climbSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param climbSubsystem The subsystem used by this command.
     */
    public DefaultClimbCommand(ClimbSubsystem climbSubsystem) {
        this.climbSubsystem = climbSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(climbSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // FIXME: Use input from the Operator to run the climb, don't just turn on the climb motors

        // Run the shooter wheel
        // climbSubsystem.setMotorSpeeds(ClimbConstants.CLIMBER_MOTOR_SPEED);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // Stop this command after 3 seconds total
        if (isTimeoutExceeded(3.0)) {
            setFinishReason("Climb finished");
            return true;
        }

        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}