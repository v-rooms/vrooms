package io.vrooms.service;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.TokenOptions;
import io.vrooms.model.RoomToken;
import io.vrooms.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Service
public class RoomSessionService {

	private static final Logger logger = LoggerFactory.getLogger(RoomSessionService.class);

	private final OpenVidu openVidu;

	private Map<String, Session> sessionStore = new ConcurrentHashMap<>();

	@Autowired
	public RoomSessionService(OpenVidu openVidu) {
		this.openVidu = openVidu;
	}

	public void createSessionByRoomId(String roomId) throws OpenViduJavaClientException, OpenViduHttpException {
		final Session session = openVidu.createSession();
		sessionStore.putIfAbsent(roomId, session);
	}

	public RoomToken generateTokenByRoomId(String roomId, User user) throws TokenGenerateException {

		TokenOptions tokenOpts = new TokenOptions.Builder()
				.role(OpenViduRole.PUBLISHER)
				.data(user.getId()).build();

		if (nonNull(sessionStore) && nonNull(sessionStore.get(roomId))) {
			try {
				Session session = sessionStore.get(roomId);
				session.generateToken(tokenOpts);
				return new RoomToken(session.generateToken(tokenOpts));
			} catch (OpenViduJavaClientException e) {
				throw new TokenGenerateException("Couldn't generate token", e);
			} catch (OpenViduHttpException ex) {

				logger.warn("Invalid sessionId (User left unexpectedly). Session object is not valid.");

				if (ex.getStatus() == HttpStatus.NOT_FOUND.value()) {
					try {
						final Session session = openVidu.createSession();
						session.generateToken(tokenOpts);
						return new RoomToken(session.generateToken(tokenOpts));
					} catch (OpenViduJavaClientException | OpenViduHttpException e) {
						throw new TokenGenerateException("Couldn't generate token");
					}
				}
			}
		}

		throw new TokenGenerateException(format("Session is empty for %s room", roomId));
	}

	public Map<String, Session> getSessionStore() {
		return sessionStore;
	}

	public void setSessionStore(Map<String, Session> sessionStore) {
		this.sessionStore = sessionStore;
	}
}
