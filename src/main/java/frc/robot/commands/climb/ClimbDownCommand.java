package frc.robot.commands.climb;

import frc.robot.Constants.ClimbConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbDownCommand extends LoggingCommand {

    private final ClimbSubsystem climbSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param climbSubsystem The subsystem used by this command.
     */
    public ClimbDownCommand(ClimbSubsystem climbSubsystem) {
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
        climbSubsystem.setMotorSpeeds(-ClimbConstants.CLIMBER_MOTOR_SPEED, ClimbConstants.CLIMBER_MOTOR_SPEED);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (climbSubsystem.getLeftEncoder() < -40)
            return true;
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
        climbSubsystem.stop();
    }
}