import java.util.Scanner;

public class MasterMind {

    static Scanner key = new Scanner(System.in);

    public static void main(String[] args) {
        String[] colores = {"ROJO", "AZUL", "VERDE", "AMARILLO", "NARANJA", "BLANCO"};
        String[][] combinaciones = new String[10][4]; //Pensaba almacenar cada ronda en una fila pero no lo he usado
        String[] solucion = new String[4];
        String[] entrada = new String[4];
        boolean[] muertos = new boolean[4];
        int ronda = 1;
        boolean victoria = false;

        System.out.println("[Colores disponibles]");
        for (int i = 0; i < colores.length; i++) {
            System.out.println(colores[i]);
        }

        System.out.println("[GENERANDO SOLUCION...]");
        generarCombinacion(solucion, colores);
        /*for (int i = 0; i < solucion.length; i++) {
            System.out.print(solucion[i] + "\t");
        }
        System.out.println();

         */

        do {
            System.out.println("[RONDA "+ronda+"]");
            leerCombinacionUsuario(entrada, colores);

            for (int i = 0; i < entrada.length; i++) {
                System.out.print(entrada[i] + "\t");
            }
            System.out.println();

            System.out.printf("""
                    Muertos: %d
                    Heridos: %d
                    """, contarMuertos(entrada, solucion, muertos), contarHeridos(entrada, solucion, muertos));

            if (contarMuertos(entrada, solucion, muertos) == 4) {
                victoria = true;
            }

            ronda++;
        } while (ronda <= 10 && !victoria);

        if (victoria) {
            System.out.println("HAS GANADO!");
        } else {
            System.out.println("HAS PERDIDO!");
        }
    }

    public static void generarCombinacion(String[] solucion, String [] colores) {
        String colorAleatorio;
        boolean colorRepetido;

        for (int i = 0; i < solucion.length; i++) {
            do {
                colorAleatorio = colores[(int)((Math.random()*6))];
                colorRepetido = false;

                for (int j = 0; j <= i; j++) {
                    if (colorAleatorio.equals(solucion[j])){
                        colorRepetido = true;
                    }
                }
            } while (colorRepetido);

            solucion[i] = colorAleatorio;
        }
    }

    public static void leerCombinacionUsuario (String[] entrada, String[] colores) {

        for (int i = 0; i < entrada.length; i++) {
            boolean entradaValida = false;
            do {
                System.out.print("Introduce el color de la posicion " + (i+1)+": ");
                entrada[i] = key.next().toUpperCase();
                if (entrada[i].equals(colores[0]) || entrada[i].equals(colores[1]) || entrada[i].equals(colores[2]) ||
                        entrada[i].equals(colores[3]) ||entrada[i].equals(colores[4]) ||entrada[i].equals(colores[5])) {
                    entradaValida = true;
                } else {
                    System.out.println("Entrada incorrecta!");
                }
            } while (!entradaValida);
        }
    }

    public static int contarMuertos(String[] entrada, String[] solucion, boolean[] muertos) {
        int numMuertos = 0;

        for (int i = 0; i < solucion.length; i++) {
            if (entrada[i].equals(solucion[i])) {
                numMuertos++;
                muertos[i] = true;
            }
        }
        return numMuertos;
    }

    public static int contarHeridos(String[] entrada, String[] solucion, boolean[] muertos) {
        int heridos = 0;
        boolean existe;

        for (int i = 0; i < entrada.length ; i++) {
            existe = false;
            if (!muertos[i]) {
                for (int j = 0; j < solucion.length && !existe; j++) {

                    if((i != j) && entrada[i].equals(solucion[j]) && !muertos[j]) {
                        existe = true;
                        muertos[j] = true;
                    }
                }
                if (existe) {
                    heridos++;
                }
            }
        }
        return heridos;
    }
}
