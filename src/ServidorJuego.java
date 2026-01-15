import java.io.*;
import java.net.*;

public class ServidorJuego
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket servidor = new ServerSocket(5000);
            GestorJuego gestor = new GestorJuego();

            System.out.println("Esperando Jugador 1...");
            HiloJugador j1 = new HiloJugador(servidor.accept(), 1, gestor);

            System.out.println("Esperando Jugador 2...");
            HiloJugador j2 = new HiloJugador(servidor.accept(), 2, gestor);

            j1.setRival(j2);
            j2.setRival(j1);
            new Thread(j1).start();
            new Thread(j2).start();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}