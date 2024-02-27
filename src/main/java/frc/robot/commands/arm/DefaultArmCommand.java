package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.LoggingCommand;
import frc.robot.operator.GameController;
import frc.robot.subsystems.ArmSubsystem;

public class DefaultArmCommand extends LoggingCommand {

    private final ArmSubsystem   armSubsystem;
    private final XboxController driverController;

    /**
     * Creates a new ExampleCommand.
     *
     * @param armSubsystem The subsystem used by this command.
     */
    public DefaultArmCommand(GameController driverController,
        ArmSubsystem armSubsystem) {

        this.driverController = driverController;
        this.armSubsystem     = armSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(armSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double leftArmTrigger  = driverController.getLeftTriggerAxis();
        double rightArmTrigger = driverController.getRightTriggerAxis();

        if (leftArmTrigger > 0 && rightArmTrigger == 0)
            armSubsystem.setArmSpeed(leftArmTrigger);
        else if (rightArmTrigger > 0 && leftArmTrigger == 0)
            armSubsystem.setArmSpeed(rightArmTrigger * -1);
        else
            armSubsystem.setArmSpeed(0);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}