import java.io.*;
import java.net.*;

public class ServidorJuego
{
    public static void main(String[] args)
    {
        try (ServerSocket servidor = new ServerSocket(5000))
        {
            System.out.println("Esperando jugadores...");
            GestorJuego gestor = new GestorJuego();

            Socket s1 = servidor.accept();
            System.out.println("Jugador 1 conectado");

            Socket s2 = servidor.accept();
            System.out.println("Jugador 2 conectado");

            HiloJugador h1 = new HiloJugador(s1, 1, gestor);
            HiloJugador h2 = new HiloJugador(s2, 2, gestor);

            h1.setRival(h2);
            h2.setRival(h1);

            new Thread(h1).start();
            new Thread(h2).start();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}