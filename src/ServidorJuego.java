import java.net.ServerSocket;
import java.net.Socket;

public class ServidorJuego
{

    public static void main(String[] args) throws Exception
    {

        ServerSocket server = new ServerSocket(5000);
        GestorJuego gestor = new GestorJuego();

        System.out.println("Esperando jugadores...");

        Socket j1 = server.accept();
        Socket j2 = server.accept();

        System.out.println("Jugadores conectados");

        new HiloJugador(j1, gestor, 1).start();
        new HiloJugador(j2, gestor, 2).start();
    }
}
