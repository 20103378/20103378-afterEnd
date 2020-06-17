package cc.mrbird.febs.cable.system.mqtt;
import cc.mrbird.febs.cable.system.entity.Device;
import cc.mrbird.febs.cable.system.entity.Gas;
import cc.mrbird.febs.cable.system.entity.GasConcentration;
import cc.mrbird.febs.cable.system.properties.FebsServerSystemProperties;
import cc.mrbird.febs.cable.system.service.IDeviceService;
import cc.mrbird.febs.cable.system.service.IGasConcentrationService;
import cc.mrbird.febs.cable.system.service.IGasService;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.common.redis.service.RedisService;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.time.LocalDateTime;
import java.util.*;

/**
 * MQTT配置，消费者
 */
@Configuration
@IntegrationComponentScan
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MqttReceiverConfig {
    /**
     * 订阅的bean名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";

    private final RedisService redisService;
    private final IGasService gasService;
    private final IGasConcentrationService gasConcentrationService;
    private final IDeviceService deviceService;
    private final FebsServerSystemProperties properties;

    // 客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息
    private static final byte[] WILL_DATA;

    static {
        WILL_DATA = "offline".getBytes();
    }

//    @Value("${mqtt.username}")
//    private String username;
//
//    @Value("${mqtt.password}")
//    private String password;

//    @Value("${mqtt.url}")
//    private String url;
//
//    @Value("${mqtt.receiver.clientId}")
//    private String clientId;
//
//    @Value("${mqtt.receiver.defaultTopic}")
//    private String defaultTopic;

    /**
     * MQTT客户端
     */
    @Bean
    public MqttPahoClientFactory receiverMqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(new String[]{properties.getUrl()});
        factory.setConnectionTimeout(10);
        factory.setKeepAliveInterval(20);
        return factory;
    }

    /**
     * MQTT信息通道（消费者）
     */
    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }


    /**
     * MQTT消息订阅绑定（消费者）
     */
    @Bean
    public MessageProducer inbound() {
// 可以同时消费（订阅）多个Topic
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        properties.getClientId(), receiverMqttClientFactory(), properties.getDefaultTopic(), "112132324324324524", "topic1", "topic2", "topic3", "topic4");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
// 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * MQTT消息处理器（消费者）
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {

                String payload = message.getPayload().toString();
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                // 根据topic分别进行消息处理。
                if (topic.equals("112132324324324524") || topic.equals("topic2") || topic.equals("topic3") || topic.equals("topic4")) {

                    System.out.println(topic + ": 处理消息 " + payload);

                    String params = JSON.parseObject(payload).get("params").toString();
                    Set<Map.Entry<String, Object>> set = JSON.parseObject(params).entrySet();
                    if (set.iterator().next() != null) {
                        String key = set.iterator().next().getKey();
                        Double value = Double.parseDouble(set.iterator().next().getValue().toString());
                        Gas gas = gasService.fingByGasName(key);
                        Device device = deviceService.findByDeviceId(topic);
                        if (gas != null && device != null) {
                            GasConcentration gasConcentration = new GasConcentration();
                            gasConcentration.setDeviceId(device.getId());
                            gasConcentration.setGasId(gas.getId());
                            gasConcentration.setConcentration(value);
                            gasConcentration.setTime(new Date());
                            gasConcentrationService.createGasConcentration(gasConcentration);
                            //获取缓存内容  //存入redis
                            Map<Object, Object> resultMap = redisService.hmget(device.getDeviceId()+"data");
                            if (resultMap == null || resultMap.size() == 0) {
                                resultMap = new HashMap<>(10);
                            }
                            List<String[]> list = (List<String[]>) resultMap.get(gas.getGasName());
                            if(list==null){
                                list  = new ArrayList<String[]>();
                            }
                            if(list.size()>=10){
                                list.remove(0);
                            }
                            list.add(new String[]{DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN),value.toString()});
                            resultMap.put(gas.getGasName(), list);
                            redisService.hmset(device.getDeviceId()+"data", resultMap);
                        }
                    }

                } else if (topic.equals(properties.getDefaultTopic())) {
                    System.out.println(properties.getDefaultTopic() + ": 处理消息 " + payload);
                } else {
                    System.out.println(topic + ": 丢弃消息 " + payload);
                }
            }
        };
    }

    public static void main(String[] args) {
        String str = "{params:{H2S:123}}";
        System.out.println(JSON.parseObject(str).get("params"));
        String params = JSON.parseObject(str).get("params").toString();
        System.out.println(JSON.parseObject(params).get("H2S"));
        System.out.println(JSON.parseObject(params).entrySet().iterator().next().getKey());
    }
}