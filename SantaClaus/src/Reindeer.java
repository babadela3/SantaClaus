public class Reindeer implements Runnable{

    private final int index;

    public Reindeer(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        while (!Laponia.endOfTheWorld) {
            try {

                Thread.sleep(Laponia.generator.nextInt(300));

                // protect the counters
                Laponia.counterMutex.acquire();
                System.out.printf("Reindeer %d arrived\n", index);
                // increment no of reindeers
                Laponia.reindeerCount++;
                if (Laponia.reindeerCount == Laponia.NUM_REINDEERS_IN_GROUP) {
                    // stop if end of the world
                    Laponia.stopCounter--;
                    if (Laponia.stopCounter == 0) {
                        Laponia.endOfTheWorld = true;
                        Laponia.stopSem.release();
                    }

                    // wake up santa
                    Laponia.santaSem.release();
                }
                Laponia.counterMutex.release();

                // wait in reindeer queue
                Laponia.reindeerSem.acquire();

                // get hitched to the sleigh
                getHitched();
            } catch (InterruptedException ignored) {
            }
        }
        System.out.printf("Reindeer %d is fading away\n", index);
    }

    private void getHitched() {
        System.out.printf("Reindeer %d is getting hitched\n", index);
            try {
                Thread.sleep(Laponia.generator.nextInt(300));
            } catch (InterruptedException ignored) {
            }
    }
}
