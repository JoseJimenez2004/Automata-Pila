import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Automata automata = new Automata(); 
        automata.mostrarInformacionAutomata();
        automata.ejecutarAutomata(""); // Pasar una cadena vacía o la cadena que quieras probar
    }
}

