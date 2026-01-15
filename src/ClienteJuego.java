import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteJuego
{
    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            String msg;
            while ((msg = in.readLine()) != null)
            {
                if (msg.startsWith("TABLERO"))
                {
                    pintar(msg.substring(8));
                } else if (msg.startsWith("TURNO"))
                {
                    System.out.print("Tu turno (1-9): ");
                    out.println("PONER " + sc.next());
                } else if (msg.startsWith("ESPERA"))
                {
                    System.out.println("Esperando al rival...");
                } else if (msg.startsWith("FIN"))
                {
                    System.out.println("Partida terminada: " + msg);
                    break;
                }
            }
            socket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public static void pintar(String d)
    {
        String[] c = d.split(",");
        System.out.println("\nTablero:");
        for (int f = 0; f < 3; f++)
        {
            System.out.print("| " + c[f * 3] + " | " + c[f * 3 + 1] + " | " + c[f * 3 + 2] + " |");
            int n = 9 - (f * 3);
            System.out.println("    " + (n - 2) + " " + (n - 1) + " " + n);
        }
    }
}