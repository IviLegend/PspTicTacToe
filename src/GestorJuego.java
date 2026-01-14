import java.util.Arrays;

public class GestorJuego
{
    private char[][] tablero;
    private int turnoActual; // 1 o 2
    private boolean partidaFinalizada;

    public GestorJuego()
    {
        tablero = new char[3][3];
        for (char[] fila : tablero) Arrays.fill(fila, '-'); // 'L' de Libre
        turnoActual = 1; // Empieza el Jugador 1
        partidaFinalizada = false;
    }

    public synchronized String ponerFicha(int fila, int col, int idJugador)
    {
        if (partidaFinalizada) return "ERROR Partida finalizada";
        if (idJugador != turnoActual) return "ERROR No es tu turno";
        if (fila < 0 || fila > 2 || col < 0 || col > 2 || tablero[fila][col] != '-')
        {
            return "ERROR Casilla ocupada o inválida";
        }

        tablero[fila][col] = (idJugador == 1) ? 'X' : 'O';

        if (comprobarVictoria())
        {
            partidaFinalizada = true;
            return "FIN Ganador Jugador " + idJugador;
        }

        // Cambio de turno [cite: 58]
        turnoActual = (turnoActual == 1) ? 2 : 1;
        return "OK";
    }

    public synchronized int getTurnoActual()
    {
        return turnoActual;
    }

    public synchronized String getEstadoTablero()
    {
        StringBuilder sb = new StringBuilder("TABLERO ");
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sb.append(i).append(",").append(j).append(":").append(tablero[i][j]).append(" ");
            }
        }
        return sb.toString().trim();
    }

    private boolean comprobarVictoria()
    {
        // Lógica simplificada de tres en raya (debes completarla para filas, col y diag)
        return false;
    }
}