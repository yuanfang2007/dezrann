package bzh.dezrann.websocket;


import bzh.dezrann.config.Config;
import bzh.dezrann.recording.databean.Record;
import bzh.dezrann.recording.databean.Recording;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

public class ReplayEndpoint extends Endpoint {

	private EntityManager entityManager;

	public ReplayEndpoint(){
		this.entityManager = Config.injector.getInstance(EntityManager.class);
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		session.addMessageHandler(new Whole<String>() {
			@Override
			public void onMessage(String message) {
				try{
					Query query = entityManager.createQuery("from Record where recordingId = :id order by timestamp", Record.class);
					query.setParameter("id", Integer.parseInt(message));
					List<Record> records = query.getResultList();
					while(true){
						Long pastTime = records.get(0).getTimestamp();
						try{
							for(Record record : records){
								Thread.sleep(record.getTimestamp()-pastTime);
								pastTime = record.getTimestamp();
								session.getBasicRemote().sendText(record.getJson());
							}
						}catch(Exception e){
							e.printStackTrace();
							break;
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}