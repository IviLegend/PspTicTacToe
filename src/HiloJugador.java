import java.io.BufferedReader;
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

    public void enviarEstado()
    {
        out.println(gestor.getTableroProtocolo());
        if (gestor.jugadorActual == id) out.println("TURNO");
        else out.println("ESPERA");
    }

    @Override
    public void run()
    {
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("INICIO " + id);

            // Bucle principal de red
            while (true)
            {
                enviarEstado();
                String peticion = in.readLine();
                if (peticion == null) break;

                if (peticion.startsWith("PONER"))
                {
                    int pos = Integer.parseInt(peticion.split(" ")[1]);
                    String res = gestor.intentarMovimiento(pos, id);

                    if (res.startsWith("FIN"))
                    {
                        String m = (res.equals("FIN_GANADOR")) ? "Ganador J" + id : "Empate";
                        out.println(gestor.getTableroProtocolo());
                        out.println("FIN " + m);
                        rival.out.println(gestor.getTableroProtocolo());
                        rival.out.println("FIN " + m);
                        break;
                    }

                    rival.enviarEstado();
                }
            }
        } catch (Exception e)
        {
            if (rival != null) rival.out.println("FIN Rival desconectado");
        }
    }
}