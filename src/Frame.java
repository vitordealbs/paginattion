/**
 * Representa um frame (moldura) de memória física.
 *
 * Record que armazena informações sobre uma moldura de página na memória física.
 * Usa Java 21 para uma estrutura imutável e concisa.
 */
public class Frame {
    private final int frameId;
    private Integer pageId;
    private boolean referenced;
    private boolean modified;
    private int frequency;
    private int lastUsed;

    public Frame(int frameId) {
        this.frameId = frameId;
        this.pageId = null;
        this.referenced = false;
        this.modified = false;
        this.frequency = 0;
        this.lastUsed = 0;
    }

    public int frameId() { return frameId; }
    public Integer pageId() { return pageId; }
    public boolean referenced() { return referenced; }
    public boolean modified() { return modified; }
    public int frequency() { return frequency; }
    public int lastUsed() { return lastUsed; }

    public void setPageId(Integer pageId) { this.pageId = pageId; }
    public void setReferenced(boolean referenced) { this.referenced = referenced; }
    public void setModified(boolean modified) { this.modified = modified; }
    public void setFrequency(int frequency) { this.frequency = frequency; }
    public void incrementFrequency() { this.frequency++; }
    public void setLastUsed(int lastUsed) { this.lastUsed = lastUsed; }

    public boolean isEmpty() { return pageId == null; }

    @Override
    public String toString() {
        return "Frame[%d]{page=%s, R=%d, M=%d, freq=%d}".formatted(
            frameId, pageId, referenced ? 1 : 0, modified ? 1 : 0, frequency
        );
    }
}
