package org.firstinspires.ftc.teamcode.production.drivetrain.action;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Arrays;
import java.util.List;

public class MotorController {

    private final List<DcMotorEx> motorList;

    public MotorController(List<DcMotorEx> motorList) {
        this.motorList = motorList;
    }

    public MotorController(DcMotorEx... motors) {
        this.motorList = Arrays.asList(motors);
    }

    public void performAction(MotorAction action) {
        for (DcMotorEx motor : motorList) {
            action.perform(motor);
        }
    }

}
