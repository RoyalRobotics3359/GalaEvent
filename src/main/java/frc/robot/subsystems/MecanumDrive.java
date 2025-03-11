// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MecanumDrive extends SubsystemBase {

  private SparkMax leftFront;
  private SparkMax leftBack;
  private SparkMax rightFront;
  private SparkMax rightBack;

  private SparkMaxConfig LF_config;
  private SparkMaxConfig LB_config;
  private SparkMaxConfig RF_config;
  private SparkMaxConfig RB_config;

  /** Creates a new MecanumDrive. */

  // To follow the new way to set up REV SparkMax motors, follow:
  // https://docs.revrobotics.com/brushless/revlib/revlib-overview/migrating-to-revlib-2025
  public MecanumDrive() {

    leftFront = new SparkMax(Constants.Motors.leftFront.getId(), MotorType.kBrushed);
    leftBack = new SparkMax(Constants.Motors.leftBack.getId(), MotorType.kBrushed);
    rightFront = new SparkMax(Constants.Motors.rightFront.getId(), MotorType.kBrushed);
    rightBack = new SparkMax(Constants.Motors.rightBack.getId(), MotorType.kBrushed);

    LF_config = new SparkMaxConfig();
    LB_config = new SparkMaxConfig();
    RF_config = new SparkMaxConfig();
    RB_config = new SparkMaxConfig();

    LF_config.inverted(Constants.Motors.leftFront.isReversed()).idleMode(IdleMode.kBrake).smartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT);
    LB_config.inverted(Constants.Motors.leftBack.isReversed()).idleMode(IdleMode.kBrake).smartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT);
    RF_config.inverted(Constants.Motors.rightFront.isReversed()).idleMode(IdleMode.kBrake).smartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT);
    RB_config.inverted(Constants.Motors.rightBack.isReversed()).idleMode(IdleMode.kBrake).smartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT);

    leftFront.configure(LF_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftBack.configure(LB_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFront.configure(RF_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightBack.configure(RB_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // This method gives power to each specific motor on the mecanum drive by adding/subtracting the vectors speed, turn, and/or strafe to
  // make the robot drive omnidirectional.
  public void setSpeed(double speed, double turn, double strafe) {
    double scale = Math.max(speed + turn + strafe, 1.0);

    double LF_power = (speed + turn + strafe) / scale;
    double LB_power = (speed + turn - strafe) / scale;
    double RF_power = (speed - turn - strafe) / scale;
    double RB_power = (speed - turn + strafe) / scale;

    leftFront.set(LF_power);
    leftBack.set(LB_power);
    rightFront.set(RF_power);
    rightBack.set(RB_power);
  }


  // This method brakes the robot by setting the speed to each motor to zero
  public void motorStop() {
    leftFront.set(0.0);
    leftBack.set(0.0);
    rightFront.set(0.0);
    rightBack.set(0.0);
  }
}
