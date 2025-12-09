import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Algoritmo Segunda Chance (Second Chance)
 *
 * Variação do FIFO que dá uma segunda chance para páginas recentemente acessadas.
 * Usa um bit de referência (R) para determinar se a página merece uma segunda chance.
 */
public class SecondChanceSimulator extends PageReplacementSimulator {
    private final Queue<Integer> queue; // Fila circular de frames

    public SecondChanceSimulator(int numFrames) {
        super(numFrames);
        this.queue = new LinkedList<>();
    }

    @Override
    public void simulate(List<Integer> references, boolean verbose) {
        for (int pageId : references) {
            this.references++;

            int frameIdx = findPageInMemory(pageId);

            if (frameIdx != -1) {
                // Hit - marca como referenciada (segunda chance)
                frames.get(frameIdx).setReferenced(true);

                if (verbose) {
                    System.out.printf("Ref %d: Hit - Página %d no frame %d (marcada como referenciada)\n",
                                    this.references, pageId, frameIdx);
                }
            } else {
                // Page fault
                pageFaults++;
                int freeFrame = findFreeFrame();

                if (freeFrame != -1) {
                    // Há frame livre
                    frames.get(freeFrame).setPageId(pageId);
                    frames.get(freeFrame).setReferenced(false);
                    queue.offer(freeFrame);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Carregou página %d no frame %d\n",
                                        this.references, pageId, freeFrame);
                    }
                } else {
                    // Procura vítima dando segunda chance
                    evictions++;

                    while (true) {
                        int candidateFrame = queue.poll();

                        if (!frames.get(candidateFrame).referenced()) {
                            // Vítima encontrada (R=0)
                            int victimPage = frames.get(candidateFrame).pageId();
                            frames.get(candidateFrame).setPageId(pageId);
                            frames.get(candidateFrame).setReferenced(false);
                            queue.offer(candidateFrame);

                            if (verbose) {
                                System.out.printf("Ref %d: Page fault - Evictou página %d, carregou %d no frame %d\n",
                                                this.references, victimPage, pageId, candidateFrame);
                            }
                            break;
                        } else {
                            // Dá segunda chance (reseta R e volta para fila)
                            frames.get(candidateFrame).setReferenced(false);
                            queue.offer(candidateFrame);

                            if (verbose) {
                                System.out.printf("Ref %d: Segunda chance dada ao frame %d\n",
                                                this.references, candidateFrame);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Segunda Chance";
    }
}
