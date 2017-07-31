package info.xiaomo.tool.config;

public class MessageConfig extends BeanConfig{
	
	/**
	 * 消息id
	 */
	private int id;
	
	/**
	 * 队列id
	 */
	private int queueId;
	
	/**
	 * 通信方向
	 */
	private MessageDirection dir;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public MessageDirection getDir() {
		return dir;
	}
	
	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public void setDir(MessageDirection dir) {
		this.dir = dir;
	}
	
}
