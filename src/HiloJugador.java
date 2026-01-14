import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloJugador implements Runnable
{
    private Socket socket;
    private int id;
    private GestorJuego gestor;
    private HiloJugador rival;
    private PrintWriter out;

    public HiloJugador(Socket s, int id, GestorJuego g)
    {
        this.socket = s;
        this.id = id;
        this.gestor = g;
    }

    public void setRival(HiloJugador rival)
    {
        this.rival = rival;
    }

    public void enviar(String msg)
    {
        out.println(msg);
    }

    @Override
    public void run()
    {
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            enviar("INICIO " + id);

            while (true)
            {
                enviar(gestor.getEstadoTablero());
                if (gestor.getTurnoActual() == id)
                {
                    enviar("TURNO");
                } else
                {
                    enviar("ESPERA");
                }

                String line = in.readLine();
                if (line == null) break; // Desconexión

                if (line.startsWith("PONER"))
                {
                    String[] partes = line.split(" ");
                    int f = Integer.parseInt(partes[1]);
                    int c = Integer.parseInt(partes[2]);

                    String resultado = gestor.ponerFicha(f, c, id);
                    if (resultado.startsWith("FIN"))
                    {
                        enviar(resultado);
                        rival.enviar(resultado);
                        break;
                    }
                }
            }
        } catch (IOException e)
        {
            rival.enviar("FIN El rival se ha desconectado. ¡Ganaste!");
        }
    }
}