package Cliente;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import Conexion.Conexion;
import Protocolo.Protocolo;
import java.util.InputMismatchException;
public class Cliente extends Conexion{
    public Cliente() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient() //Método para iniciar el cliente
    {
        Protocolo protocolo = new Protocolo();
        try
        {
            //Flujo de datos hacia el servidor
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            DataInputStream datosRecepcion = new DataInputStream(cs.getInputStream());

            try (Scanner scanner = new Scanner(System.in)) {
                int tipo = menuPrincipal();
                int figura = menuFigura();
                double radio = 0.0;
                double altura = 0.0;
                double lado = 0.0;
                String tipoO = "";
                String tipoF = "";

                switch (figura) {
                    case 1:
                        radio = validarDouble(scanner,"Ingresa el radio:  ");
                        altura =  validarDouble(scanner,"Ingresa la altura:  ");
                        tipoF = "CL";
                        break;
                    case 2:
                        radio = validarDouble(scanner,"Ingresa el radio:  ");
                        tipoF = "ES";
                        break;
                    case 3:
                        radio = validarDouble(scanner,"Ingresa el radio:  ");
                        altura = validarDouble(scanner,"Ingresa la altura:  ");
                        tipoF = "CO";
                        break;
                    case 4:
                        lado = validarDouble(scanner,"Ingresa la lado:  ");
                        tipoF = "CU";
                        break;
                    case 5:
                        lado = validarDouble(scanner,"Ingresa la lado:  ");
                        altura = validarDouble(scanner,"Ingresa la altura:  ");
                        tipoF = "PR";
                        break;
                    case 6:
                        altura = validarDouble(scanner,"Ingresa la altura:  ");
                        lado =validarDouble(scanner,"Ingresa el lsfo:  ");
                        tipoF = "PI";
                        break;
                }
                switch (tipo){
                    case 1:
                        tipoO = "A";
                        break;
                    case 2:
                        tipoO = "V";
                }
                String mensaje = protocolo.codificarCliente(tipoO,tipoF,altura,lado,radio);
                salidaServidor.writeUTF(mensaje);
            }


            System.out.println("Resultado: ");
            String resultado = protocolo.decodificarCliente(datosRecepcion.readUTF());
            System.out.print(resultado);


            cs.close();//Fin de la conexión

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static int menuPrincipal() {
        int opcion;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione el tipo de operacion: (Introduzca el numero de la operacion deseada)");
            System.out.println("1) Calculo de area");
            System.out.println("2) Calculo de volumen");
            opcion = validarOpcion(scanner, 2);
            return opcion;
        }while(opcion!=4);
    }
    public static int menuFigura() {
        int opcion;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nSeleccione la figura:");
            System.out.println("1) Cilindro");
            System.out.println("2) Esfera");
            System.out.println("3) Cono");
            System.out.println("4) Cubo");
            System.out.println("5) Prisma Cuadrangular");
            System.out.println("6) Piramide");
            opcion = validarOpcion(scanner, 6);
            return opcion;
        }while(opcion!=6);
    }

    private static int validarOpcion(Scanner scanner, int maxValor) {
        int valor = scanner.nextInt();
        while (valor < 1 || valor > maxValor) {
            System.out.println("Opcion invalida, por favor verifica");
            // java.util.InputMismatchException should also be caught
            // to intercept non-numeric input
            valor = scanner.nextInt();
        }
        return valor;
    }
    private static double validarDouble(Scanner scanner, String prompt) {
        System.out.println(prompt);
        double value;
        while (true) {
                try{
                    value = scanner.nextDouble();
                    break;

                } catch (InputMismatchException e){
                    System.out.println("Opcion invalida, reintenta");
                    scanner.next();
                    continue;
                }

            }

            // java.util.InputMismatchException should also be caught
            // to intercept non-numeric input

        return value;
    }
}
