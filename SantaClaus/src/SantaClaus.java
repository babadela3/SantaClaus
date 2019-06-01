
public class SantaClaus implements Runnable{

    @Override
    public void run() {
        try {
            while (!Laponia.stopAttaches) {
                Laponia.santaSemaphore.acquire();
                Laponia.mutex.acquire();
                if (Laponia.reindeerCount == 9) {
                    Laponia.reindeerCount = 0;
                    prepSleigh();
                    Laponia.reindeerSemaphore.release(9);
                } else {
                    if (Laponia.elfCount == 3) {
                        helpElves();
                        Laponia.elfSemaphore.release(3);
                    }
                }
                Laponia.mutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prepSleigh() {
        System.out.println("Santa Claus prepares sleigh");
    }

    private void helpElves() {
        System.out.println("Santa Claus helps elves");
    }
}
