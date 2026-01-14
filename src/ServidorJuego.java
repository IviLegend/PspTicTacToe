import java.io.*;
import java.net.*;

public class ServidorJuego
{
    public static void main(String[] args)
    {
        try (ServerSocket servidor = new ServerSocket(5000))
        {
            System.out.println("Servidor iniciado. Esperando jugadores...");

            GestorJuego gestor = new GestorJuego(); // Unico objeto compartido

            // Acepta Jugador 1
            Socket s1 = servidor.accept();
            HiloJugador j1 = new HiloJugador(s1, 1, gestor);
            System.out.println("Jugador 1 conectado.");

            // Acepta Jugador 2
            Socket s2 = servidor.accept();
            HiloJugador j2 = new HiloJugador(s2, 2, gestor);
            System.out.println("Jugador 2 conectado.");

            // Iniciar hilos
            j1.setRival(j2);
            j2.setRival(j1);
            new Thread(j1).start();
            new Thread(j2).start();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}