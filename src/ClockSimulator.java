import java.util.List;

/**
 * Algoritmo Clock (Relógio)
 *
 * Versão circular do Segunda Chance usando um ponteiro que se move como ponteiro de relógio.
 * Mais eficiente que Segunda Chance pois não precisa de uma fila explícita.
 */
public class ClockSimulator extends PageReplacementSimulator {
    private int clockHand; // Ponteiro do relógio (índice atual)

    public ClockSimulator(int numFrames) {
        super(numFrames);
        this.clockHand = 0;
    }

    @Override
    public void simulate(List<Integer> references, boolean verbose) {
        for (int pageId : references) {
            this.references++;

            int frameIdx = findPageInMemory(pageId);

            if (frameIdx != -1) {
                // Hit - marca como referenciada
                frames.get(frameIdx).setReferenced(true);

                if (verbose) {
                    System.out.printf("Ref %d: Hit - Página %d no frame %d (bit R=1)\n",
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

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Carregou página %d no frame %d\n",
                                        this.references, pageId, freeFrame);
                    }
                } else {
                    // Usa ponteiro circular para encontrar vítima
                    evictions++;

                    while (true) {
                        if (!frames.get(clockHand).referenced()) {
                            // Vítima encontrada (R=0)
                            int victimPage = frames.get(clockHand).pageId();
                            frames.get(clockHand).setPageId(pageId);
                            frames.get(clockHand).setReferenced(false);

                            if (verbose) {
                                System.out.printf("Ref %d: Page fault - Evictou página %d, carregou %d no frame %d (clock)\n",
                                                this.references, victimPage, pageId, clockHand);
                            }

                            // Avança o ponteiro
                            clockHand = (clockHand + 1) % numFrames;
                            break;
                        } else {
                            // Dá segunda chance (reseta R)
                            frames.get(clockHand).setReferenced(false);

                            if (verbose) {
                                System.out.printf("Ref %d: Clock passou pelo frame %d (resetou R)\n",
                                                this.references, clockHand);
                            }

                            // Avança o ponteiro
                            clockHand = (clockHand + 1) % numFrames;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Clock";
    }
}
