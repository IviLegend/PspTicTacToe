import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HiloJugador extends Thread
{

    private Socket socket;
    private GestorJuego gestor;
    private int id;
    private Scanner in;
    private PrintWriter out;

    public HiloJugador(Socket socket, GestorJuego gestor, int id) throws Exception
    {
        this.socket = socket;
        this.gestor = gestor;
        this.id = id;
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run()
    {
        out.println("INICIO " + id);

        while (true)
        {
            if (gestor.getTurnoActual() == id)
            {
                out.println("TURNO");
            } else
            {
                out.println("ESPERA");
                dormir();
                continue;
            }

            String msg = in.nextLine(); // PONER 5
            String[] partes = msg.split(" ");

            if (partes[0].equals("PONER"))
            {
                int pos = Integer.parseInt(partes[1]);
                String resultado = gestor.ponerFicha(id, pos);

                out.println("TABLERO");
                gestor.pintarMapa();

                if (resultado.startsWith("FIN"))
                {
                    out.println(resultado);
                    break;
                }
            }
        }
    }

    private void dormir()
    {
        try
        {
            Thread.sleep(500);
        } catch (InterruptedException ignored)
        {
        }
    }
}