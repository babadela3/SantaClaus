import java.util.concurrent.TimeUnit;

public class Elf implements Runnable{

    private final int index;

    public Elf(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            while (!Laponia.stopAttaches) {
                Laponia.elfMutex.acquire();
                Laponia.mutex.acquire();
                Laponia.elfCount++;
                if (Laponia.elfCount == 3) {
                    Laponia.santaSemaphore.release();
                } else {
                    Laponia.elfMutex.release();
                }
                Laponia.mutex.release();
                if(Laponia.elfSemaphore.tryAcquire(1, TimeUnit.SECONDS)) {
                    getHelped();
                }

                Laponia.mutex.acquire();
                Laponia.elfCount--;
                if (Laponia.elfCount == 0) {
                    Laponia.elfMutex.release();
                }
                Laponia.mutex.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getHelped() throws InterruptedException {
        Thread.sleep(5);
        System.out.println("Elf " + index + " has been helped");
    }
}
