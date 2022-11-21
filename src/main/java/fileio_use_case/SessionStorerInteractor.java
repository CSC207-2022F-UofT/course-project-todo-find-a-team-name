package fileio_use_case;

import entities.Session;
import java.util.HashMap;

/**
 * SessionStorerInteractor stores session, Fall (F) and Winter (S). Can add and get a session
 */
public class SessionStorerInteractor {
    private final HashMap<String, Session> allSessions;

    public SessionStorerInteractor() {
        this.allSessions = new HashMap<>();
    }

    /** Adds session from map **/
    public void addSession(String sessionType, Session session) {
        allSessions.put(sessionType, session);
    }

    /** Returns a session from map
     * @param sessionType Fall(F) or Winter(S)
     * @return Session - A session object if SessionType is found or else null
     */
    public Session getSession(String sessionType) {
        if (this.allSessions.containsKey(sessionType)) {
            return this.allSessions.get(sessionType);
        }
        return null;
    }

    /** Returns all Session (a HashMap representation)
     * @return allSession - HashMap<String, Session>
     **/
    public HashMap<String, Session> getAllSessions() {
        return this.allSessions;
    }


}