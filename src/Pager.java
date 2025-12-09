import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal do Simulador de Substituição de Páginas
 *
 * Disciplina: Sistemas Operacionais
 * Universidade Federal do Ceará
 *
 * Este programa simula algoritmos clássicos de substituição de páginas:
 * FIFO, LRU, Ótimo, Segunda Chance, Clock, NRU, LFU, MFU
 *
 * Uso: java Pager --algo <ALGO> --frames <N> --trace <arquivo> [--verbose]
 */
public class Pager {

    public static void main(String[] args) {
        // Valida argumentos
        if (args.length < 6) {
            printUsage();
            System.exit(1);
        }

        String algo = null;
        int frames = 0;
        String traceFile = null;
        boolean verbose = false;

        // Parse dos argumentos
        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--algo" -> algo = args[++i].toLowerCase();
                    case "--frames" -> frames = Integer.parseInt(args[++i]);
                    case "--trace" -> traceFile = args[++i];
                    case "--verbose" -> verbose = true;
                    default -> {
                        System.err.println("Argumento desconhecido: " + args[i]);
                        printUsage();
                        System.exit(1);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar argumentos: " + e.getMessage());
            printUsage();
            System.exit(1);
        }

        // Valida campos obrigatórios
        if (algo == null || traceFile == null || frames <= 0) {
            System.err.println("Erro: --algo, --frames e --trace são obrigatórios");
            printUsage();
            System.exit(1);
        }

        // Lê arquivo de trace
        List<Integer> references;
        try {
            references = readTraceFile(traceFile);
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            System.exit(1);
            return;
        }

        if (references.isEmpty()) {
            System.err.println("Erro: Arquivo de trace vazio");
            System.exit(1);
        }

        // Cria simulador apropriado
        PageReplacementSimulator simulator = createSimulator(algo, frames);
        if (simulator == null) {
            System.err.println("Erro: Algoritmo '" + algo + "' não reconhecido");
            printAvailableAlgorithms();
            System.exit(1);
        }

        // Executa simulação
        simulator.simulate(references, verbose);

        // Imprime resultados
        simulator.printResults();
    }

    /**
     * Cria instância do simulador baseado no algoritmo escolhido
     */
    private static PageReplacementSimulator createSimulator(String algo, int frames) {
        return switch (algo) {
            case "fifo" -> new FIFOSimulator(frames);
            case "lru" -> new LRUSimulator(frames);
            case "otimo", "optimal" -> new OptimalSimulator(frames);
            case "segundachance", "secondchance" -> new SecondChanceSimulator(frames);
            case "clock" -> new ClockSimulator(frames);
            case "nru" -> new NRUSimulator(frames);
            case "lfu" -> new LFUSimulator(frames);
            case "mfu" -> new MFUSimulator(frames);
            default -> null;
        };
    }

    /**
     * Lê arquivo de trace e retorna lista de referências
     */
    private static List<Integer> readTraceFile(String filename) throws IOException {
        List<Integer> references = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        references.add(Integer.parseInt(line));
                    } catch (NumberFormatException e) {
                        System.err.println("Aviso: Linha inválida ignorada: " + line);
                    }
                }
            }
        }

        return references;
    }

    /**
     * Imprime instruções de uso
     */
    private static void printUsage() {
        String usage = """

                Uso: java Pager --algo <ALGO> --frames <N> --trace <arquivo> [--verbose]

                Argumentos obrigatórios:
                  --algo <ALGO>      Algoritmo de substituição a usar
                  --frames <N>       Número de molduras de página (frames)
                  --trace <arquivo>  Arquivo com sequência de referências

                Argumentos opcionais:
                  --verbose          Modo verboso (mostra detalhes da execução)

                Exemplo:
                  java Pager --algo lru --frames 3 --trace silberschatz2001.trace
                """;

        System.out.println(usage);
        printAvailableAlgorithms();
    }

    /**
     * Lista algoritmos disponíveis
     */
    private static void printAvailableAlgorithms() {
        String algorithms = """

                Algoritmos disponíveis:
                  fifo           - First-In, First-Out
                  lru            - Least Recently Used
                  otimo          - Algoritmo Ótimo
                  segundachance  - Segunda Chance
                  clock          - Clock (Relógio)
                  nru            - Not Recently Used
                  lfu            - Least Frequently Used
                  mfu            - Most Frequently Used
                """;

        System.out.println(algorithms);
    }
}
