import java.util.Random;

public class GestorJuego
{
    public String[][] mapa;
    public String relleno = "-";
    public int jugadorActual;
    private boolean partidaFinalizada = false;

    public GestorJuego()
    {
        mapa = new String[3][3];
        // Rellenar el mapa
        for (int f = 0; f < 3; f++)
        {
            for (int c = 0; c < 3; c++)
            {
                mapa[f][c] = relleno;
            }
        }

        jugadorActual = 1;
    }

    public synchronized String realizarMovimiento(int posicion, int idJugador)
    {
        if (partidaFinalizada) return "FIN La partida ya terminó";
        if (idJugador != jugadorActual) return "ERROR No es tu turno";

        int f = 0, c = 0;
        switch (posicion)
        {
            case 1 ->
            {
                f = 2;
                c = 0;
            }
            case 2 ->
            {
                f = 2;
                c = 1;
            }
            case 3 ->
            {
                f = 2;
                c = 2;
            }
            case 4 ->
            {
                f = 1;
                c = 0;
            }
            case 5 ->
            {
                f = 1;
                c = 1;
            }
            case 6 ->
            {
                f = 1;
                c = 2;
            }
            case 7 ->
            {
                f = 0;
                c = 0;
            }
            case 8 ->
            {
                f = 0;
                c = 1;
            }
            case 9 ->
            {
                f = 0;
                c = 2;
            }
            default ->
            {
                return "ERROR Posición inválida";
            }
        }

        if (!mapa[f][c].equals(relleno))
        {
            return "ERROR Casilla ocupada";
        }

        mapa[f][c] = (idJugador == 1) ? "X" : "O";

        if (comprobarVictoria(mapa[f][c]))
        {
            partidaFinalizada = true;
            return "FIN Ganador Jugador " + idJugador;
        }

        if (comprobarEmpate())
        {
            partidaFinalizada = true;
            return "FIN Empate";
        }

        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        return "OK";
    }

    public synchronized String obtenerMapaComoTexto()
    {
        String res = "TABLERO ";
        for (int f = 0; f < 3; f++)
        {
            for (int c = 0; c < 3; c++)
            {
                res += mapa[f][c] + ((f == 2 && c == 2) ? "" : ",");
            }
        }
        return res;
    }

    private boolean comprobarVictoria(String ficha)
    {
        // Horizontal y vertical
        for (int i = 0; i < 3; i++)
        {
            if (mapa[i][0].equals(ficha) && mapa[i][1].equals(ficha) && mapa[i][2].equals(ficha)) return true;
            if (mapa[0][i].equals(ficha) && mapa[1][i].equals(ficha) && mapa[2][i].equals(ficha)) return true;
        }
        // Diagonales
        if (mapa[0][0].equals(ficha) && mapa[1][1].equals(ficha) && mapa[2][2].equals(ficha)) return true;
        if (mapa[0][2].equals(ficha) && mapa[1][1].equals(ficha) && mapa[2][0].equals(ficha)) return true;
        return false;
    }

    private boolean comprobarEmpate()
    {
        for (int f = 0; f < 3; f++)
        {
            for (int c = 0; c < 3; c++)
            {
                if (mapa[f][c].equals(relleno)) return false;
            }
        }
        return true;
    }
}