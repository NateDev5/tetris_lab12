public class GameProcess implements Runnable {
    private Thread gameThread;
    private boolean running = false;

    private final int targetFPS = 60;
    private final long targetTime = 1000 / targetFPS;

    protected void startGame() {
        start();
    }

    private synchronized void start () {
        if(running) return;
        running = true;
        gameThread  = new Thread (this);
        gameThread.start();
        onStart();
    }

    public synchronized void stop () {
        if(!running) return;
        running = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();

        while (running) {
            long now = System.currentTimeMillis();
            long deltaTime = now - lastTime;

            if (deltaTime >= targetTime) {
                update();
                render();
                lastTime = now;
            }

            try {
                Thread.sleep(Math.max(0, targetTime - (System.currentTimeMillis() - lastTime)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stop();
    }

    protected void update() {
        throw new UnsupportedOperationException();
    }

    protected void render() {
        throw new UnsupportedOperationException();
    }

    protected void onStart () {
       throw new UnsupportedOperationException();
    }
}
