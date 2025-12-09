import java.util.List;

/**
 * Algoritmo NRU (Not Recently Used)
 *
 * Remove páginas baseado em 4 classes de prioridade usando bits R (Referenced) e M (Modified):
 * - Classe 0: R=0, M=0 (não referenciada, não modificada) - MELHOR candidata
 * - Classe 1: R=0, M=1 (não referenciada, modificada)
 * - Classe 2: R=1, M=0 (referenciada, não modificada)
 * - Classe 3: R=1, M=1 (referenciada, modificada) - PIOR candidata
 */
public class NRUSimulator extends PageReplacementSimulator {
    private int clockTick; // Simula interrupções de relógio para resetar bit R

    public NRUSimulator(int numFrames) {
        super(numFrames);
        this.clockTick = 0;
    }

    @Override
    public void simulate(List<Integer> references, boolean verbose) {
        for (int pageId : references) {
            this.references++;
            clockTick++;

            int frameIdx = findPageInMemory(pageId);

            if (frameIdx != -1) {
                // Hit - marca como referenciada
                frames.get(frameIdx).setReferenced(true);

                if (verbose) {
                    System.out.printf("Ref %d: Hit - Página %d no frame %d (R=1)\n",
                                    this.references, pageId, frameIdx);
                }
            } else {
                // Page fault
                pageFaults++;
                int freeFrame = findFreeFrame();

                if (freeFrame != -1) {
                    // Há frame livre
                    frames.get(freeFrame).setPageId(pageId);
                    frames.get(freeFrame).setReferenced(true);
                    frames.get(freeFrame).setModified(false);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Carregou página %d no frame %d\n",
                                        this.references, pageId, freeFrame);
                    }
                } else {
                    // Remove da classe mais baixa
                    evictions++;
                    int victimFrame = findNRUVictim();
                    int victimPage = frames.get(victimFrame).pageId();

                    frames.get(victimFrame).setPageId(pageId);
                    frames.get(victimFrame).setReferenced(true);
                    frames.get(victimFrame).setModified(false);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Evictou página %d (NRU), carregou %d no frame %d\n",
                                        this.references, victimPage, pageId, victimFrame);
                    }
                }
            }

            // Simula interrupção de relógio periódica (reseta bit R)
            if (clockTick % 10 == 0) {
                resetReferenceBits();
                if (verbose) {
                    System.out.printf("Ref %d: Interrupção de relógio - bits R resetados\n", this.references);
                }
            }
        }
    }

    /**
     * Encontra vítima baseado nas 4 classes NRU
     */
    private int findNRUVictim() {
        // Procura classe por classe (0 a 3)
        for (int targetClass = 0; targetClass < 4; targetClass++) {
            for (int i = 0; i < numFrames; i++) {
                int pageClass = getPageClass(frames.get(i));
                if (pageClass == targetClass) {
                    return i;
                }
            }
        }
        return 0; // Fallback
    }

    /**
     * Calcula a classe da página (0-3) baseada nos bits R e M
     */
    private int getPageClass(Frame frame) {
        int r = frame.referenced() ? 1 : 0;
        int m = frame.modified() ? 1 : 0;
        return (r << 1) | m; // Combina R e M em um valor 0-3
    }

    /**
     * Reseta os bits de referência (simula interrupção de relógio)
     */
    private void resetReferenceBits() {
        for (Frame frame : frames) {
            if (!frame.isEmpty()) {
                frame.setReferenced(false);
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "NRU";
    }
}
