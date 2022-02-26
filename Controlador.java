package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="mucho poder", group="Linear Opmode")
public class Controlador extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    // Motores para movimiento
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    //private DcMotor centerDrive = null;

    // Servos y motores para el brazo
    CRServo servoR;
    CRServo servoL;
    DcMotor motorB;
    DcMotor motor_brazo;

    private boolean slow = false;

    static final double MAX_POS     =  1.0;
    static final double MIN_POS     =  0;

    double  position = (MAX_POS - MIN_POS) / 2;
    double positionC = 1;

    double pl;  //Poder de las llantas

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Brazo
        servoR = hardwareMap.get(CRServo.class, "servoR");
        servoL = hardwareMap.get(CRServo.class, "servoL");
        motorB = hardwareMap.get(DcMotor.class, "motorCaja");
        motor_brazo = hardwareMap.get(DcMotor.class, "motorBrazo");

        // Chasis
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        // centerDrive = hardwareMap.get(DcMotor.class, "center_drive");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        //centerDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // variables para la energia de cada motor en el chasis
            double leftPower;
            double rightPower;
            double centerPower;
            slow = gamepad2.a;



            // Variables para los valores que controlaran los motores en el chasis (gamepad2)
            double poderBrazo;
            double drive = -gamepad2.left_stick_y;
            double turn  =  gamepad2.right_stick_x;
            double center_drive = gamepad2.left_stick_x;
            float left = gamepad2.left_trigger;
            float right = gamepad2.right_trigger;

            // Variables para los botones que controlan el brazo (gamepad2)
            boolean sleft = gamepad1.left_bumper;
            boolean sright = gamepad1.right_bumper;
            double brazo = gamepad1.left_stick_y;

            // energia a enviar al brazo
            poderBrazo = Range.clip(brazo, -0.6, 0.6) ;
            // enviar el poder al brazo
            motor_brazo.setPower(-poderBrazo);

            // energia de los servos del carrusel
            if(sleft){
                position = 1;
            }else if(sright){
                position = -1;
            }else{
                position = 0;
            }

            if(gamepad1.a){
                positionC = 1;
            }else if(gamepad1.b){
                positionC = -1;
            }else{
                positionC = 0;
            }

            // enviar el poder a los servos del carrusel
            servoL.setPower(position);
            servoR.setPower(-position);
            motorB.setPower(positionC );



            // Poder de los motores del chasis
            pl = 1;
            leftPower    = Range.clip(drive - (turn * 0.6), -pl, pl) ;
            rightPower   = Range.clip(drive + (turn * 0.6), -pl, pl) ;
            //centerPower  = Range.clip(center_drive, -0.6,0.6);

            // Funcion del boton para movimientos lentos
            if (gamepad2.right_bumper){
                leftPower = leftPower * 0.3;
                rightPower = rightPower * 0.3;
                //centerPower = centerPower * 0.3;
            }

            // Enviar el poder calculado a las ruedas




            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            //centerDrive.setPower(-centerPower);



            //sleep(CYCLE_MS);
            idle();
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
