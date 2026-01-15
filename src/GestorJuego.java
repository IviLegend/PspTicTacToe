

public class GestorJuego
{
    public String[][] mapa = new String[3][3];
    public String relleno = "-";
    public int jugadorActual = 1;

    public GestorJuego()
    {
        for (int f = 0; f < 3; f++)
            for (int c = 0; c < 3; c++) mapa[f][c] = relleno;
    }

    public synchronized String intentarMovimiento(int pos, int id)
    {
        if (id != jugadorActual) return "NO_ES_TU_TURNO";

        int f = 0, c = 0;
        switch (pos)
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
                return "ERROR";
            }
        }

        if (!mapa[f][c].equals(relleno)) return "OCUPADA";

        mapa[f][c] = (id == 1) ? "X" : "O";

        if (verVictoria(mapa[f][c])) return "FIN_GANADOR";
        if (verEmpate()) return "FIN_EMPATE";

        jugadorActual = (jugadorActual == 1) ? 2 : 1;
        return "OK";
    }

    public synchronized String getTableroProtocolo()
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

    private boolean verVictoria(String ficha)
    {
        // Horizontal y Vertical
        for (int i = 0; i < 3; i++)
        {
            if (mapa[i][0].equals(ficha) && mapa[i][1].equals(ficha) && mapa[i][2].equals(ficha)) return true;
            if (mapa[0][i].equals(ficha) && mapa[1][i].equals(ficha) && mapa[2][i].equals(ficha)) return true;
        }
        // Diagonales
        if ((mapa[0][0].equals(ficha) && mapa[1][1].equals(ficha) && mapa[2][2].equals(ficha)) ||
                (mapa[0][2].equals(ficha) && mapa[1][1].equals(ficha) && mapa[2][0].equals(ficha))) return true;

        return false;
    }

    private boolean verEmpate()
    {
        for (String[] fila : mapa)
            for (String cas : fila) if (cas.equals(relleno)) return false;
        return true;
    }
}