import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteJuego
{
    public static void main(String[] args)
    {
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in))
        {

            String msg;
            while ((msg = in.readLine()) != null)
            {
                if (msg.startsWith("INICIO"))
                {
                    System.out.println("Conectado. " + msg);
                }
                else if (msg.startsWith("TABLERO"))
                {
                    String datos = msg.substring(8); // Quitamos la palabra "TABLERO "
                    String[] casillas = datos.split(",");

                    System.out.println("\n--- ESTADO DEL TABLERO ---");
                    for (int i = 0; i < 9; i++) {
                        // Imprimir el inicio de fila
                        if (i % 3 == 0) System.out.print("| ");

                        System.out.print(casillas[i] + " | ");

                        // Salto de línea al final de cada fila de 3
                        if ((i + 1) % 3 == 0) {
                            System.out.println();
                        }
                    }
                    System.out.println("--------------------------");
                }
                else if (msg.startsWith("ESPERA"))
                {
                    System.out.println("Esperando al rival...");
                }
                else if (msg.startsWith("TURNO"))
                {
                    System.out.print("Es tu turno. Introduce Fila y Columna (ej: 1 1): ");
                    int f = sc.nextInt();
                    int c = sc.nextInt();
                    out.println("PONER " + f + " " + c);
                }
                else if (msg.startsWith("FIN"))
                {
                    System.out.println("PARTIDA FINALIZADA: " + msg);
                    break;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}