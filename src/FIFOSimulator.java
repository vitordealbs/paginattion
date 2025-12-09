import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Algoritmo FIFO (First-In, First-Out)
 *
 * Remove a página mais antiga na memória (primeira a entrar).
 * Utiliza uma fila para manter a ordem de chegada das páginas.
 */
public class FIFOSimulator extends PageReplacementSimulator {
    private final Queue<Integer> queue; // Fila para controlar ordem FIFO

    public FIFOSimulator(int numFrames) {
        super(numFrames);
        this.queue = new LinkedList<>();
    }

    @Override
    public void simulate(List<Integer> references, boolean verbose) {
        for (int pageId : references) {
            this.references++;

            int frameIdx = findPageInMemory(pageId);

            if (frameIdx == -1) {
                // Page fault
                pageFaults++;
                int freeFrame = findFreeFrame();

                if (freeFrame != -1) {
                    // Há frame livre
                    frames.get(freeFrame).setPageId(pageId);
                    queue.offer(freeFrame);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Carregou página %d no frame %d\n",
                                        references, pageId, freeFrame);
                    }
                } else {
                    // Precisa fazer evicção (FIFO - remove o mais antigo)
                    evictions++;
                    int victimFrame = queue.poll();
                    int victimPage = frames.get(victimFrame).pageId();

                    frames.get(victimFrame).setPageId(pageId);
                    queue.offer(victimFrame);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Evictou página %d, carregou %d no frame %d\n",
                                        references, victimPage, pageId, victimFrame);
                    }
                }
            } else if (verbose) {
                System.out.printf("Ref %d: Hit - Página %d já está no frame %d\n",
                                references, pageId, frameIdx);
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "FIFO";
    }
}
