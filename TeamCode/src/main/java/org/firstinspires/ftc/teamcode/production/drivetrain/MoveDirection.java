package org.firstinspires.ftc.teamcode.production.drivetrain;

public enum MoveDirection {
    FORWARD,
    BACKWARD,
    STRAFE_LEFT,
    STRAFE_RIGHT;

    public boolean isStrafe() {
        return this.equals(STRAFE_LEFT) || this.equals(STRAFE_RIGHT);
    }
}
