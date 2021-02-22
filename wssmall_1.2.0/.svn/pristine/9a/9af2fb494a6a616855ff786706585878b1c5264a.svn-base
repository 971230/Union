package com.ztesoft.mq.client;

import com.ztesoft.serial.CodecFactory;

public class EventProcessService
{
  private EventAdapterControl listenerController;
  private String topic;
  private String action;
  private EventSingleListenHandler processor;
  private CodecFactory codecFactory;

  EventProcessService()
  {
  }

  EventProcessService(EventAdapterControl listenerController, String topic, String action, EventSingleListenHandler processor)
  {
    this.listenerController = listenerController;
    this.topic = topic;
    this.action = action;
    this.processor = processor;
  }

  EventProcessService(EventAdapterControl listenerController, String topic, String action, EventSingleListenHandler processor, CodecFactory codecFactory)
  {
    this.listenerController = listenerController;
    this.topic = topic;
    this.action = action;
    this.processor = processor;
    this.codecFactory = codecFactory;
  }

  public void init() throws Exception {
    if (this.listenerController == null) {
      throw new IllegalArgumentException(
        "EventListenerController can not be null.");
    }
    try
    {
      if (this.codecFactory != null)
        this.listenerController.add(this.topic, this.action, this.processor, this.codecFactory);
      else {
        this.listenerController.add(this.topic, this.action, this.processor);
      }

      if (!(this.listenerController.isStart()))
        this.listenerController.start();
    }
    catch (Exception e) {
      throw new IllegalArgumentException(
        "Init EventListernerService fail." + e.getMessage());
    }
  }

  

  public String getTopic() {
    return this.topic;
  }

  public String getAction() {
    return this.action;
  }

  public EventSingleListenHandler getProcessor() {
    return this.processor;
  }

  public CodecFactory getCodecFactory() {
    return this.codecFactory;
  }


  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setProcessor(EventSingleListenHandler processor) {
    this.processor = processor;
  }

  public void setCodecFactory(CodecFactory codecFactory) {
    this.codecFactory = codecFactory;
  }

public EventAdapterControl getListenerController() {
	return listenerController;
}

public void setListenerController(EventAdapterControl listenerController) {
	this.listenerController = listenerController;
}
  
}