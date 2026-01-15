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

            String linea;
            while ((linea = in.readLine()) != null)
            {
                if (linea.startsWith("INICIO"))
                {
                    System.out.println("Eres el Jugador " + linea.split(" ")[1]);
                } else if (linea.startsWith("TABLERO"))
                {
                    pintarMapa(linea.substring(8));
                } else if (linea.startsWith("ESPERA"))
                {
                    System.out.println("Esperando al rival...");
                } else if (linea.startsWith("TURNO"))
                {
                    System.out.print("Tu turno. Dime posición (1-9): ");
                    int pos = sc.nextInt();
                    out.println("PONER " + pos);
                } else if (linea.startsWith("FIN"))
                {
                    System.out.println("PARTIDA ACABADA: " + linea);
                    break;
                } else if (linea.startsWith("ERROR"))
                {
                    System.err.println(linea);
                }
            }
        } catch (IOException e)
        {
            System.out.println("Conexión perdida con el servidor.");
        }
    }

    public static void pintarMapa(String datos)
    {
        String[] casillas = datos.split(",");
        System.out.println("\nTablero:");
        for (int f = 0; f < 3; f++)
        {
            for (int c = 0; c < 3; c++)
            {
                int i = f * 3 + c;
                System.out.print("| " + casillas[i] + " ");
            }

            int numRef = 9 - (f * 3);
            System.out.println("|    " + (numRef - 2) + " " + (numRef - 1) + " " + numRef);
        }
        System.out.println();
    }
}