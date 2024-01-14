// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Constants.LauncherConstants;

public class PrepareLaunch extends CommandBase {

    private ShooterSubsystem m_ShooterSubsystem;

  /** Creates a new ShooterCommand. */
  public PrepareLaunch(ShooterSubsystem shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_ShooterSubsystem = shooter;

    addRequirements(m_ShooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ShooterSubsystem.setTopMotor(LauncherConstants.kLauncherSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}