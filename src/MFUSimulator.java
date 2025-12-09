import java.util.List;

/**
 * Algoritmo MFU (Most Frequently Used)
 *
 * Remove a página mais frequentemente usada.
 * Baseado na ideia de que páginas muito usadas recentemente podem não ser mais necessárias.
 */
public class MFUSimulator extends PageReplacementSimulator {

    public MFUSimulator(int numFrames) {
        super(numFrames);
    }

    @Override
    public void simulate(List<Integer> references, boolean verbose) {
        for (int pageId : references) {
            this.references++;

            int frameIdx = findPageInMemory(pageId);

            if (frameIdx != -1) {
                // Hit - incrementa frequência
                frames.get(frameIdx).incrementFrequency();

                if (verbose) {
                    System.out.printf("Ref %d: Hit - Página %d no frame %d (freq=%d)\n",
                                    this.references, pageId, frameIdx, frames.get(frameIdx).frequency());
                }
            } else {
                // Page fault
                pageFaults++;
                int freeFrame = findFreeFrame();

                if (freeFrame != -1) {
                    // Há frame livre
                    frames.get(freeFrame).setPageId(pageId);
                    frames.get(freeFrame).setFrequency(1);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Carregou página %d no frame %d\n",
                                        this.references, pageId, freeFrame);
                    }
                } else {
                    // Remove página com maior frequência
                    evictions++;

                    int victimFrame = 0;
                    int maxFrequency = frames.get(0).frequency();

                    for (int i = 1; i < numFrames; i++) {
                        if (frames.get(i).frequency() > maxFrequency) {
                            maxFrequency = frames.get(i).frequency();
                            victimFrame = i;
                        }
                    }

                    int victimPage = frames.get(victimFrame).pageId();
                    frames.get(victimFrame).setPageId(pageId);
                    frames.get(victimFrame).setFrequency(1);

                    if (verbose) {
                        System.out.printf("Ref %d: Page fault - Evictou página %d (freq=%d), carregou %d no frame %d\n",
                                        this.references, victimPage, maxFrequency, pageId, victimFrame);
                    }
                }
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "MFU";
    }
}
