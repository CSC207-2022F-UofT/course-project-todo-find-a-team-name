package fileio_use_case.listeners;

public class sessionsUpdateListener implements EventListener {
    private final String fileName;
    public sessionsUpdateListener(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void notify(String eventType, String fileName) {
        System.out.println("Session has " + eventType);
    }
}
