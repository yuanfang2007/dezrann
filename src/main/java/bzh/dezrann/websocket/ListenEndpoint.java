package bzh.dezrann.websocket;

import bzh.dezrann.Forwards;
import bzh.dezrann.config.Config;
import bzh.dezrann.Sessions;
import javax.websocket.*;

public class ListenEndpoint extends Endpoint {

	private Sessions sessions;
	private Forwards forwards;

	public ListenEndpoint(){
		this.sessions = Config.injector.getInstance(Sessions.class);
		this.forwards = Config.injector.getInstance(Forwards.class);
	}

	@Override
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		session.addMessageHandler((MessageHandler.Whole<String>) message -> {
			if(forwards.containsKey(session)){
				Session watcher = forwards.get(session);
				watcher.getAsyncRemote().sendText(message);
			}
		});
		sessions.put(session.getId(), session);
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		sessions.remove(session.getId());
	}
}