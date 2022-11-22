package fileio_use_case.listeners;

public interface EventListener {
    void notify(String eventType, String file);
}
