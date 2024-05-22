import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Automata {

    private ArrayList<String> conjuntoQ;
    private ArrayList<String> conjuntoSigma;
    private ArrayList<String> conjuntoR;
    private ArrayList<String> conjuntoF;
    private ArrayList<String[]> conjuntoTransiciones;
    private String estadoInicial;
    private String simboloInicialPila;
    private String estadoActual;
    private Stack<String> pila;
    private String result;

    public Automata() throws IOException {
        conjuntoQ = new ArrayList<>();
        conjuntoSigma = new ArrayList<>();
        conjuntoR = new ArrayList<>();
        conjuntoF = new ArrayList<>();
        conjuntoTransiciones = new ArrayList<>();
        pila = new Stack<>();

        String nombreFichero;
        File ruta;
        Scanner imputNombreFichero = new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero con los datos del autómata:");
        nombreFichero = imputNombreFichero.nextLine();
        ruta = new File(nombreFichero);
        String textoFichero;
        FileReader leerFichero = new FileReader(ruta);
        BufferedReader bufferLectura = new BufferedReader(leerFichero);
        int linea = 0;
        while ((textoFichero = bufferLectura.readLine()) != null) {
            if (textoFichero.matches("#.*"))
                continue;
            else if (textoFichero.matches("\b*"))
                continue;
            else {
                if (linea >= 6) {
                    String[] separarEspacios = textoFichero.split(" ");
                    conjuntoTransiciones.add(separarEspacios);
                } else {
                    String[] separarEspacios = textoFichero.split(" ");
                    for (String separarEspacio : separarEspacios) {
                        if (separarEspacio.matches("#.*"))
                            break;
                        else {
                            if (linea == 0)
                                conjuntoQ.add(separarEspacio);
                            else if (linea == 1)
                                conjuntoSigma.add(separarEspacio);
                            else if (linea == 2)
                                conjuntoR.add(separarEspacio);
                            else if (linea == 3)
                                estadoInicial = separarEspacio;
                            else if (linea == 4)
                                simboloInicialPila = separarEspacio;
                            else if (linea == 5)
                                conjuntoF.add(separarEspacio);
                        }
                    }
                }
                linea++;
            }
        }
        bufferLectura.close();
    }

    public void mostrarInformacionAutomata() {
        System.out.println("Información del autómata con pila");
        System.out.println();
        System.out.println("Estado inicial: " + estadoInicial);
        System.out.println("Símbolo inicial de la pila: " + simboloInicialPila);
        System.out.println("Conjunto de estados: " + conjuntoQ.toString());
        System.out.println("Alfabeto del lenguaje: " + conjuntoSigma.toString());
        System.out.println("Alfabeto de la pila: " + conjuntoR.toString());
        System.out.println("Conjunto de estados finales: " + conjuntoF.toString());
        System.out.println("Conjunto de transiciones:");
        for (String[] transicion : conjuntoTransiciones)
            System.out.println(Arrays.toString(transicion));
        System.out.println("");
    }

    public void ejecutarAutomata(String cadenaEntrada) {
        pila.clear();
        pila.push(simboloInicialPila);
        estadoActual = estadoInicial;
        boolean noTransiciones = true;
        int numeroIteraciones = cadenaEntrada.length();
        for (int i = 0; i < numeroIteraciones; i++) {
            if (noTransiciones) {
                String simboloTratado = cadenaEntrada.substring(0, 1);
                cadenaEntrada = cadenaEntrada.substring(1);
                noTransiciones = false;
                for (String[] conjuntoTransicion : conjuntoTransiciones) {
                    if (estadoActual.equals(conjuntoTransicion[0])
                            && simboloTratado.equals(conjuntoTransicion[1])
                            && pila.peek().equals(conjuntoTransicion[2])) {
                        estadoActual = conjuntoTransicion[3];
                        pila.pop();

                        if (conjuntoTransicion[4].length() > 1) {
                            String aux = conjuntoTransicion[4];
                            for (int k = 0; k < conjuntoTransicion[4].length(); k++) {
                                String aux1 = aux.substring(aux.length() - 1);
                                aux = aux.substring(0, aux.length() - 1);
                                pila.push(aux1);
                            }
                        } else {
                            if (!conjuntoTransicion[4].equals("@")) {
                                pila.push(conjuntoTransicion[4]);
                            }
                        }
                        noTransiciones = true;
                        break;
                    }
                }
            } else {
                break;
            }
        }
        cadenaEsAceptada(cadenaEntrada);
    }

    public void cadenaEsAceptada(String cadenaEntrada) {
        if (conjuntoF.get(0).equals("@")) {
            if (cadenaEntrada.isEmpty() && pila.isEmpty()) {
                result = "Cadena aceptada!";
            } else {
                result = "Cadena no aceptada!";
            }
        } else {
            for (String estadoFinal : conjuntoF) {
                if (cadenaEntrada.isEmpty() && estadoActual.equals(estadoFinal)) {
                    result = "Cadena aceptada!";
                    return;
                }
            }
            result = "Cadena no aceptada!";
        }
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public String getSimboloInicialPila() {
        return simboloInicialPila;
    }

    public ArrayList<String> getConjuntoQ() {
        return conjuntoQ;
    }

    public ArrayList<String> getConjuntoSigma() {
        return conjuntoSigma;
    }

    public ArrayList<String> getConjuntoR() {
        return conjuntoR;
    }

    public ArrayList<String> getConjuntoF() {
        return conjuntoF;
    }

    public ArrayList<String[]> getConjuntoTransiciones() {
        return conjuntoTransiciones;
    }

    public String getResult() {
        return result;
    }
}


