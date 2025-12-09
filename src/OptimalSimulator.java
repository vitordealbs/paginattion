import java.util.List;

/**
 * Algoritmo Ótimo (Optimal)
 *
 * Remove a página que será usada mais tarde no futuro (ou nunca).
 * Este é o algoritmo com melhor desempenho teoricamente possível.
 */
public class OptimalSimulator extends PageReplacementSimulator {

    public OptimalSimulator(int numFrames) {
        super(numFrames);
    }

    @Override
    public void simulate(List<Integer> references, boolean verbose) {
        for (int i = 0; i < references.size(); i++) {
            int pageId = references.get(i);
            this.references++;

            int frameIdx = findPageInMemory(pageId);

            if (frameIdx != -1) {
                // Hit
                if (verbose) {
                    System.out.printf("Ref %d: Hit - Página %d já está no frame %d\n",
                                    this.references, pageId, frameIdx);
                }
            } else {
                // Page fault
                pageFaults++;
                int freeFrame = findFreeFrame();

                if (freeFrame != -1) {
                    // Há frame livre
                    frames.get(freeFrame).setPageId(pageId);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Carregou página %d no frame %d\n",
                                        this.references, pageId, freeFrame);
                    }
                } else {
                    // Precisa fazer evicção (remove a que será usada mais tarde)
                    evictions++;
                    int victimFrame = findOptimalVictim(references, i + 1);
                    int victimPage = frames.get(victimFrame).pageId();

                    frames.get(victimFrame).setPageId(pageId);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Evictou página %d (Ótimo), carregou %d no frame %d\n",
                                        this.references, victimPage, pageId, victimFrame);
                    }
                }
            }
        }
    }

    /**
     * Encontra a vítima ótima olhando para o futuro
     * @param references Lista completa de referências
     * @param startIdx Índice atual + 1 (início do futuro)
     * @return Índice do frame a ser substituído
     */
    private int findOptimalVictim(List<Integer> references, int startIdx) {
        int victimFrame = 0;
        int farthestUse = -1;

        for (int i = 0; i < numFrames; i++) {
            int pageId = frames.get(i).pageId();
            int nextUse = findNextUse(references, pageId, startIdx);

            // Se nunca será usada novamente, essa é a vítima ideal
            if (nextUse == -1) {
                return i;
            }

            // Guarda a página que será usada mais tarde
            if (nextUse > farthestUse) {
                farthestUse = nextUse;
                victimFrame = i;
            }
        }

        return victimFrame;
    }

    /**
     * Encontra a próxima posição onde a página será usada
     * @param references Lista de referências
     * @param pageId ID da página a procurar
     * @param startIdx Índice inicial da busca
     * @return Índice do próximo uso, ou -1 se nunca for usada
     */
    private int findNextUse(List<Integer> references, int pageId, int startIdx) {
        for (int i = startIdx; i < references.size(); i++) {
            if (references.get(i) == pageId) {
                return i;
            }
        }
        return -1; // Nunca será usada novamente
    }

    @Override
    public String getAlgorithmName() {
        return "Ótimo";
    }
}
