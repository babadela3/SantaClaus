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
                    Laponia.attachesCounter++;
                    if (Laponia.attachesCounter == Laponia.totalAttaches) {
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

    private synchronized void getHitched() throws InterruptedException {
        Thread.sleep(5);
        System.out.println("Reindeer " + index + " has been hitched");
    }
}
