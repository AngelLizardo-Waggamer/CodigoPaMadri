package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name="ArribaDerechUwU", group="Pushbot")

public class AutoPos1 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    // Motores para movimiento
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    DcMotor motorB;

    double poderR;
    double poderL;
    // Servos y motores para el brazo
    CRServo servoR;
    CRServo servoL;
    DcMotor motor_brazo;

    boolean isFinished;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Inicializado");
        telemetry.update();

        // Brazo
        servoR = hardwareMap.get(CRServo.class, "servoR");
        servoL = hardwareMap.get(CRServo.class, "servoL");
        motor_brazo = hardwareMap.get(DcMotor.class, "motorBrazo");
        motorB = hardwareMap.get(DcMotor.class, "motorCaja");
        // Chasis
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();
        runtime.reset();
        isFinished = false;
        while (opModeIsActive()) {
            if (!isFinished){
                /*

                Estructura básica del código:

                while (runtime.time() < oT && opModeIsActive){
                    RobotMove(fw, turn);
                    motorB.setPower(pM);
                }
                RobotMove(0,0);
                motorB.setPower(0);
                runtime.reset();


                Donde oT es objective Time (osea, el tiempo de ejecucion del código dentro del while, en milisegundos)
                fw es la potencia de los motores de chasis para acelerar adelante o atrás (positivo o negativo respectivamente)
                turn es la potencia de giro de los motores del chasis (derecha positivo e izquierda negativo)
                pM es la potencia del motor del que se haga referencia.

                La última parte del código (últimas 3 líneas) son necesarias para cersiorarse que algún motor
                no vaya a tener un movimiento indeseado después de que se ejecute otro while. El indispensable
                es runtime.reset();

                TODAS LAS POTENCIAS SON DE -1 a 1

                Y POR LO QUE MAS QUIERAS NO BORRES EL isFinished = true; pq si no el código se va a repetir
                infinitamente por los 30s :)
                 */

                // Avance inicial
                while ( runtime.time() < 1 && opModeIsActive() )  {
                    RobotMove(0.7,0);
                }
                RobotMove(0,0);
                runtime.reset();

                // Vuelta para la fuente de cocholate
                while ( runtime.time() < 0.7 && opModeIsActive() )  {
                    RobotMove(0,-1);
                }
                RobotMove(0,0);
                runtime.reset();

                // Subir el brazo y avanzar hacia la fuente de cocholate
                while (runtime.time() < 1.2 && opModeIsActive()){
                    motor_brazo.setPower(0.7);
                    RobotMove(0.15,0);
                }
                RobotMove(0,0);
                motor_brazo.setPower(0);
                runtime.reset();

                // Sacar Objetivo del intake
                while (runtime.time() < 1  && opModeIsActive()){
                   motorB.setPower(0.7);
                }
                motorB.setPower(0);
                motor_brazo.setPower(0);
                runtime.reset();

                // Retroceder de la fuente de cocholate
                while (runtime.time() < 0.5 && opModeIsActive()){
                    RobotMove(-0.5,0);
                }
                RobotMove(0,0);
                runtime.reset();

                // Dar la vuelta para estacionarse
                while (runtime.time() < 0.95 && opModeIsActive()){
                    RobotMove(0,1);
                }
                RobotMove(0,0);
                runtime.reset();

                // INTEGRACION DE ULTIMO MINUTO: MOVER BRAZO PARA ABAJO
                while (runtime.time() < 0.8 && opModeIsActive()){
                    motor_brazo.setPower(-0.7);
                }
                motor_brazo.setPower(0);
                runtime.reset();

                // Avanzar hasta chocar con el borde para estacionarse
                while (runtime.time() < 1.90 && opModeIsActive()){
                    RobotMove(0.7,0);
                }
                RobotMove(0,0);
                runtime.reset();

                // NO LO TOQUES :)
                isFinished = true;

            }

        }

    }
    // TAMPOCO TOQUES ESTO
    public void RobotMove(double fw,double turn){
        poderL    = Range.clip(fw - (turn * 0.6), -1, 1) ;
        poderR   = Range.clip(fw + (turn * 0.6), -1, 1) ;

        leftDrive.setPower(poderL);
        rightDrive.setPower(poderR);
    }
}
