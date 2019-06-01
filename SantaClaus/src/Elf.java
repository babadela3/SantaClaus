public class Elf implements Runnable{

    private int index;

    public Elf(int index) {
        this.index = index;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(Laponia.generator.nextInt(500));

            while (!Laponia.endOfTheWorld) {
                Laponia.elfMutex.acquire();
                System.out.printf("Elf %d entered\n", index);
                Laponia.counterMutex.acquire();
                Laponia.elfCount++;
                if (Laponia.elfCount == Laponia.NUM_ELVES_IN_GROUP) {
                    // wake up santa, keep the elf mutex on so other elves cannot enter
                    // while the current group of elves is getting help from santa
                    Laponia.santaSem.release();
                } else {
                    // signal other elves that are waiting so that they can join the queue
                    Laponia.elfMutex.release();
                }
                Laponia.counterMutex.release();
                // wait until a group of elves is complete
                Laponia.elfSem.acquire();
                // get help from santa
                getHelp();
                Laponia.counterMutex.acquire();
                // decrement elf count
                Laponia.elfCount--;
                if (Laponia.elfCount == 0) {
                    // after last elf exits, release elf mutex so other elves can join
                    Laponia.elfMutex.release();

                }
                Laponia.counterMutex.release();
            }
        } catch (InterruptedException e) {
        }
        System.out.printf("Elf %d is fading away\n", index);

    }

    private void getHelp() {
        System.out.printf("Elf %d is getting help\n", index);
        try {
            Thread.sleep(Laponia.generator.nextInt(400));
        } catch (InterruptedException ignored) {
        }
    }
}
