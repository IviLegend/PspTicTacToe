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

    public void setRival(HiloJugador r)
    {
        this.rival = r;
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
                enviar(gestor.obtenerMapaComoTexto());

                if (gestor.jugadorActual == id)
                {
                    enviar("TURNO");
                } else
                {
                    enviar("ESPERA");
                }

                String msg = in.readLine();
                if (msg == null) break;

                if (msg.startsWith("PONER"))
                {
                    int pos = Integer.parseInt(msg.split(" ")[1]);
                    String resultado = gestor.realizarMovimiento(pos, id);

                    if (resultado.startsWith("FIN"))
                    {
                        enviar(gestor.obtenerMapaComoTexto());
                        enviar(resultado);
                        rival.enviar(gestor.obtenerMapaComoTexto());
                        rival.enviar(resultado);
                        break;
                    } else if (resultado.startsWith("ERROR"))
                    {
                        enviar(resultado);
                    }
                }
            }
        } catch (IOException e)
        {
            rival.enviar("FIN El rival se ha desconectado");
        }
    }
}