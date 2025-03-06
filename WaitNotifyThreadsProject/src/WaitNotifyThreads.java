public class WaitNotifyThreads {

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            Waiter waiter = new Waiter(i);
            Notifier notifier = new Notifier(i, waiter);
            waiter.setNotifier(notifier);
            waiter.start();
            notifier.start();
        }
    }
}

class Waiter extends Thread {
    private Notifier notifier;

    public Waiter(int id) {
        super("Waiter " + id);
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void run() {
        synchronized (notifier) {
            try {
                System.out.println(getName() + " is waiting.");
                notifier.wait();
                System.out.println(getName() + " has been notified.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Notifier extends Thread {
    private Waiter waiter;

    public Notifier(int id, Waiter waiter) {
        super("Notifier " + id);
        this.waiter = waiter;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100); // Simulate some work
            synchronized (waiter) {
                waiter.notify();
            }
            System.out.println(getName() + " has notified " + waiter.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


