package recommend_br_use_case;

import entities.Session;

// THIS IS PLACEHOLDER GATEWAY
// It violates clean architecture by returning entity directly,
// but I am currently assuming that data obtained from this gateway can be directly
// used to create Session entity, so it should be possible to be replaced with
// actual gateway without changing crucial part of the code
// TODO: Replace this with actual gateway interface
public interface IDummySessionGateway {
    Session get(String sessionType);
}
