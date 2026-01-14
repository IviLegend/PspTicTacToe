public class GestorJuego
{

    private String[][] tablero = new String[3][3];
    private int turnoActual = 1;
    private boolean fin = false;

    public GestorJuego()
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                tablero[i][j] = "-";
    }

    private int[] traducirPosicion(int posicion)
    {
        return switch (posicion)
        {
            case 1 -> new int[]{2, 0};
            case 2 -> new int[]{2, 1};
            case 3 -> new int[]{2, 2};
            case 4 -> new int[]{1, 0};
            case 5 -> new int[]{1, 1};
            case 6 -> new int[]{1, 2};
            case 7 -> new int[]{0, 0};
            case 8 -> new int[]{0, 1};
            case 9 -> new int[]{0, 2};
            default -> null;
        };
    }

    public synchronized String ponerFicha(int jugador, int posicion)
    {

        if (fin) return "FIN Partida terminada";
        if (jugador != turnoActual) return "ERROR No es tu turno";
        if (posicion < 1 || posicion > 9) return "ERROR Posición inválida";

        int[] pos = traducirPosicion(posicion);
        if (!tablero[pos[0]][pos[1]].equals("-"))
            return "ERROR Casilla ocupada";

        tablero[pos[0]][pos[1]] = (jugador == 1) ? "X" : "O";

        if (comprobarVictoria())
        {
            fin = true;
            return "FIN Gana el jugador " + jugador;
        }

        if (comprobarEmpate())
        {
            fin = true;
            return "FIN Empate";
        }

        turnoActual = (turnoActual == 1) ? 2 : 1;
        return "OK";
    }

    public synchronized int getTurnoActual()
    {
        return turnoActual;
    }

    public synchronized String tableroComoString()
    {
        StringBuilder sb = new StringBuilder();
        for (String[] fila : tablero)
        {
            for (String c : fila)
            {
                sb.append(c).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean pintarMapa()
    {
        for (int fila = 0; fila < tablero.length; fila++)
        {
            for (int columna = 0; columna < tablero[fila].length; columna++)
            {
                if (columna != (tablero.length - 1))
                {
                    System.out.printf("| %s ", tablero[fila][columna]);
                } else
                { /* fila | numero
                        0 | 7 8 9
                        1 | 4 5 6
                        2 | 1 2 3
                  */
                    int numeroReferencia = 9 - (fila * 3);
                    System.out.printf("| %s | %s | %d | %d | %d |",
                            tablero[fila][columna], " ".repeat(5),
                            numeroReferencia - 2,
                            numeroReferencia - 1,
                            numeroReferencia);
                }
            }
            System.out.println();
        }
        return false;
    }

    public void rellenarMapa(String relleno)
    {
        for (int fila = 0; fila < tablero.length; fila++)
        {
            for (int columna = 0; columna < tablero[fila].length; columna++)
            {
                tablero[fila][columna] = relleno;
            }
        }
    }

    private boolean comprobarEmpate()
    {
        for (String[] fila : tablero)
            for (String c : fila)
                if (c.equals("-")) return false;
        return true;
    }

    private boolean comprobarVictoria()
    {
        for (int i = 0; i < 3; i++)
        {
            if (!tablero[i][0].equals("-") &&
                    tablero[i][0].equals(tablero[i][1]) &&
                    tablero[i][1].equals(tablero[i][2])) return true;

            if (!tablero[0][i].equals("-") &&
                    tablero[0][i].equals(tablero[1][i]) &&
                    tablero[1][i].equals(tablero[2][i])) return true;
        }

        return (!tablero[0][0].equals("-") &&
                tablero[0][0].equals(tablero[1][1]) &&
                tablero[1][1].equals(tablero[2][2]))
                || (!tablero[0][2].equals("-") &&
                tablero[0][2].equals(tablero[1][1]) &&
                tablero[1][1].equals(tablero[2][0]));
    }
}