import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;

public class ClienteJuego
{

    public static void main(String[] args) throws Exception
    {

        Socket socket = new Socket("localhost", 5000);
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner teclado = new Scanner(System.in);

        while (true)
        {
            String msg = in.nextLine();

            if (msg.startsWith("INICIO"))
            {
                System.out.println(msg);
            } else if (msg.equals("TURNO"))
            {
                System.out.print("Tu turno (1-9): ");
                int pos = teclado.nextInt();
                out.println("PONER " + pos);
            } else if (msg.equals("ESPERA"))
            {
                System.out.println("Esperando al rival...");
            } else if (msg.equals("TABLERO"))
            {
                System.out.println(in.nextLine());
            } else if (msg.startsWith("FIN"))
            {
                System.out.println(msg);
                break;
            }
        }
    }
}