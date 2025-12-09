import java.util.List;
import java.util.ArrayList;

/**
 * Classe base abstrata para algoritmos de substituição de páginas.
 *
 * Define a estrutura comum e métodos auxiliares para todos os algoritmos.
 */
public abstract class PageReplacementSimulator {
    protected final int numFrames;
    protected final List<Frame> frames;
    protected int pageFaults;
    protected int evictions;
    protected int references;
    protected int currentTime;

    /**
     * Construtor base para o simulador
     * @param numFrames Número de molduras de página disponíveis
     */
    public PageReplacementSimulator(int numFrames) {
        this.numFrames = numFrames;
        this.frames = new ArrayList<>(numFrames);
        for (int i = 0; i < numFrames; i++) {
            frames.add(new Frame(i));
        }
        this.pageFaults = 0;
        this.evictions = 0;
        this.references = 0;
        this.currentTime = 0;
    }

    /**
     * Verifica se uma página está na memória
     * @param pageId ID da página a verificar
     * @return índice do frame contendo a página, ou -1 se não estiver na memória
     */
    protected int findPageInMemory(int pageId) {
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i).pageId() != null && frames.get(i).pageId() == pageId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Encontra um frame livre
     * @return índice do frame livre, ou -1 se não houver
     */
    protected int findFreeFrame() {
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Simula o algoritmo com a lista de referências fornecida
     * @param references Lista de IDs de páginas referenciadas
     * @param verbose Se true, imprime informações detalhadas durante a execução
     */
    public abstract void simulate(List<Integer> references, boolean verbose);

    /**
     * Retorna o nome do algoritmo
     */
    public abstract String getAlgorithmName();

    /**
     * Imprime os resultados da simulação
     */
    public void printResults() {
        double faultRate = references > 0 ? (pageFaults * 100.0 / references) : 0.0;

        System.out.println("Algoritmo: " + getAlgorithmName());
        System.out.println("Frames: " + numFrames);
        System.out.println("Referências: " + references);
        System.out.println("Faltas de página: " + pageFaults);
        System.out.printf("Taxa de faltas: %.2f%%\n", faultRate);
        System.out.println("Evicções: " + evictions);
        System.out.println("Conjunto residente final:");

        // Coleta frame_ids e page_ids
        StringBuilder frameIds = new StringBuilder("frame_ids: ");
        StringBuilder pageIds = new StringBuilder("page_ids:  ");

        for (Frame frame : frames) {
            if (!frame.isEmpty()) {
                frameIds.append(frame.frameId()).append(" ");
                pageIds.append(frame.pageId()).append(" ");
            }
        }

        System.out.println(frameIds.toString().trim());
        System.out.println(pageIds.toString().trim());
    }

    // Getters para as métricas
    public int getPageFaults() { return pageFaults; }
    public int getEvictions() { return evictions; }
    public int getReferences() { return references; }
}
