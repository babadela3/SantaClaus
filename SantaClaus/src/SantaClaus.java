
public class SantaClaus implements Runnable{

    @Override
    public void run() {
        while (!Laponia.endOfTheWorld) {
            try {
                // wait until a group of elves or reindeers are ready
                Laponia.santaSem.acquire();
                System.out.print("Santa wakes up\n");
                // protect counters
                Laponia.counterMutex.acquire();
                if (Laponia.reindeerCount == Laponia.NUM_REINDEERS_IN_GROUP) {
                    // update reindeer count
                    Laponia.reindeerCount = 0;
                    // prep sleigh so that reindeers can get hitched
                    prepSleigh();
                    // wake up all reindeers that are waiting for Santa
                    Laponia.reindeerSem.release(Laponia.NUM_REINDEERS_IN_GROUP);
                } else if (Laponia.elfCount == Laponia.NUM_ELVES_IN_GROUP) {
                    // help the group of elves
                    helpElves();
                    // wake up the elves
                    Laponia.elfSem.release(Laponia.NUM_ELVES_IN_GROUP);
                }
                Laponia.counterMutex.release();
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("Santa is fading away");
    }


    private void prepSleigh() {
        System.out.print("Santa is prepping the sleigh\n");
        try {
            Thread.sleep(700);
        } catch (InterruptedException ignored) {
        }
    }

    private void helpElves() {
        System.out.print("Santa is helping the elves\n");
        try {
            Thread.sleep(1100);
        } catch (InterruptedException ignored) {
        }
    }
}
