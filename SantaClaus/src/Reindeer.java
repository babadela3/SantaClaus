public class Reindeer implements Runnable{

    private final int index;

    public Reindeer(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            while (!Laponia.stopAttaches) {
                Laponia.mutex.acquire();
                Laponia.reindeerCount++;
                if (Laponia.reindeerCount == 9) {
                    System.out.println("Attach: " + Laponia.attachesCounter);
                    if (Laponia.attachesCounter.addAndGet(1) == Laponia.totalAttaches + 1) {
                        Laponia.stopAttaches = true;
                    }
                    Laponia.santaSemaphore.release();
                }
                Laponia.mutex.release();
                Laponia.reindeerSemaphore.acquire();
                getHitched();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void getHitched(){
        System.out.println("Reindeer " + index + " has been hitched");
    }
}
