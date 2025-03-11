// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OperatorConsole;
import frc.robot.subsystems.MecanumDrive;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class JoystickDrive extends Command {

  private MecanumDrive drive;
  private OperatorConsole console;

  /** Creates a new JoystickDrive. */
  public JoystickDrive(MecanumDrive d, OperatorConsole c) {

    drive = d;
    console = c;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double speed = console.getDriveController().getLeftStickY();
    double turn = console.getDriveController().getRightStickX();
    double strafe = console.getDriveController().getLeftStickX();
    drive.setSpeed(speed, turn, strafe);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.motorStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
