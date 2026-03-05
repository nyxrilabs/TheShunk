package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class Robot extends TimedRobot {
  private static final double DPAD_SPEED = 0.6;

  private final DifferentialDrive m_robotDrive;
  private final PS4Controller m_controller = new PS4Controller(0);

  private final PWMSparkMax m_leftMotor1  = new PWMSparkMax(1);
  private final PWMSparkMax m_leftMotor2  = new PWMSparkMax(3);

  private final PWMSparkMax m_rightMotor1 = new PWMSparkMax(0);
  private final PWMSparkMax m_rightMotor2 = new PWMSparkMax(2);

  private final MotorControllerGroup m_leftGroup  =
      new MotorControllerGroup(m_leftMotor1, m_leftMotor2);
  private final MotorControllerGroup m_rightGroup =
      new MotorControllerGroup(m_rightMotor1, m_rightMotor2);

  public Robot() {
    m_leftMotor1.setInverted(true);
    m_leftMotor2.setInverted(true);
    m_rightMotor1.setInverted(true);
    // m_rightMotor2.setInverted(true);

    m_robotDrive = new DifferentialDrive(m_leftGroup::set, m_rightGroup::set);

    SendableRegistry.addChild(m_robotDrive, m_leftGroup);
    SendableRegistry.addChild(m_robotDrive, m_rightGroup);
  }

  @Override
  public void teleopPeriodic() {
    int pov = m_controller.getPOV();

    if (pov == 90) {
      // D-pad up: forward
      m_robotDrive.tankDrive(DPAD_SPEED, DPAD_SPEED);
    } else if (pov == 270) {
      // D-pad down: backward
      m_robotDrive.tankDrive(-DPAD_SPEED, -DPAD_SPEED);
    } else if (pov == 0) {
      // D-pad right: turn right
      m_robotDrive.tankDrive(DPAD_SPEED, -DPAD_SPEED);
    } else if (pov == 180) {
      // D-pad left: turn left
      m_robotDrive.tankDrive(-DPAD_SPEED, DPAD_SPEED);
    } else {
      // No D-pad — use sticks
      m_robotDrive.tankDrive(-m_controller.getLeftY(), m_controller.getRightY());
    }
  }
}