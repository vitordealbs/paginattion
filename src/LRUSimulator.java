import java.util.List;

/**
 * Algoritmo LRU (Least Recently Used)
 *
 * Remove a página que não foi usada há mais tempo.
 * Utiliza timestamp para rastrear o último uso de cada página.
 */
public class LRUSimulator extends PageReplacementSimulator {

    public LRUSimulator(int numFrames) {
        super(numFrames);
    }

    @Override
    public void simulate(List<Integer> references, boolean verbose) {
        for (int pageId : references) {
            this.references++;
            currentTime++;

            int frameIdx = findPageInMemory(pageId);

            if (frameIdx != -1) {
                // Hit - atualiza timestamp
                frames.get(frameIdx).setLastUsed(currentTime);

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
                    frames.get(freeFrame).setLastUsed(currentTime);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Carregou página %d no frame %d\n",
                                        this.references, pageId, freeFrame);
                    }
                } else {
                    // Precisa fazer evicção (remove o menos recentemente usado)
                    evictions++;

                    // Encontra frame com menor lastUsed
                    int victimFrame = 0;
                    int minLastUsed = frames.get(0).lastUsed();

                    for (int i = 1; i < numFrames; i++) {
                        if (frames.get(i).lastUsed() < minLastUsed) {
                            minLastUsed = frames.get(i).lastUsed();
                            victimFrame = i;
                        }
                    }

                    int victimPage = frames.get(victimFrame).pageId();
                    frames.get(victimFrame).setPageId(pageId);
                    frames.get(victimFrame).setLastUsed(currentTime);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Evictou página %d (LRU), carregou %d no frame %d\n",
                                        this.references, victimPage, pageId, victimFrame);
                    }
                }
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "LRU";
    }
}
