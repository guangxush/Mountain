## 一、Kafka基本介绍
- Kafka是一种分布式消息队列系统，按照一定的顺序持久化保存数据，可以按需读取，具备一定的故障保护和性能伸缩能力;
- Kafka中数据单元被称为消息(Message)，可以理解为数据库中的一条记录；
- 消息分批次写入Kafka，消息可以用不同的模式去组织(例如JSON)，消息的模式称之为Schema;
- 消息可以通过主题(Topic)进行分类，不同的消息具有不同的Topic;
- 主题被存放在不同的分区(Partition)之中，存放数据的一个独立的Kafka服务器被称为Broker,Broker是服务器集群的一部分,一个分区从属于一个Broker,该Broker称为分区的首领,一个分区分给多个Broker,会产生分区的复制；
- 生产者(Producer)用于发送数据到Kafka，基于不通过的业务逻辑，一个消息发布到一个特定的Topic上，通过负载均衡的原则将消息映射到不同的Partition上；
- 消费者(Consumer)可以订阅一个或者多个主题，进行消息的读取，消费者通过检查分区的偏移量顺序的读取消息，一个或者多个Consumer可以共同读取一个主题，他们构成一个消费者组(Consumer)。
## 二、Kafka启动与配置
- Kafka安装(MAC)
```brew install kafka```
- Kafka启动
```
  zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
  kafka-server-start /usr/local/etc/kafka/server.properties
  ```
- 创建topic 
 ```
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic shgx
```
- 查看创建的topic 
 ```
kafka-topics --list --zookeeper localhost:2181
```
- 发送一些消息 
 ```
kafka-console-producer --broker-list localhost:9092 --topic shgx
    >test1
    >test2
    >test3
    Ctrl+Z停止
 ```
- 消费消息
 ```
kafka-console-consumer --bootstrap-server localhost:9092 --topic shgx --from-beginning
    test1
    test2
    test3
 ```
## 三、SpingBoot依赖
在pom.xml中添加kafka依赖
```
<dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
</dependency>
```
## 四、模型框架
- 背景：本Project实现了Kafka消息的生产与消费，其中消息以微博消息为例设置了Schema,Producer包括WebSerives以及FileLoad两种方式，Consumer用于验证消息是否发送成功，实现了Kafka简易版的配置，用于理解Kafka处理数据的过程
- 结构框架
![项目框架.png](https://upload-images.jianshu.io/upload_images/7632302-6add2f342011d847.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 1.Kafka-Schema
```java
public class SchemaData {
    private Long userId;
    private String title;
    private String message;
    private int likes;
    private String comments;
    private String sendTime;
    private Boolean delete;
    //省略get set方法以及toString方法
}
```
### 2. Kafka-Producer
#### 2.1 WebServices发送数据
- 2.1.1 POST接收数据
```
@RequestMapping(value = "/producer", method = RequestMethod.POST)
@ResponseStatus(value = HttpStatus.OK)
public String postToProduce (@RequestBody SchemaData[] schemaDataArray) {
     postProducer.produceFromService(schemaDataArray);
     return "Send Success!!!";
}
```
- 2.1.2 数据处理方式
```
public void produceFromService(SchemaData[] schemaDataArray) {
        for (SchemaData message : schemaDataArray) {
            log.info("++++++++++++  message = {}", gson.toJson(message));
            kafkaTemplate.send(topic, gson.toJson(message));
        }
 }
```
####  2.2 从File中读取数据
```
public  void produceFromFile (ArrayList<LineIterator> fileLineIterators){
        for (LineIterator lineIterator : fileLineIterators) {
            while(lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                //parseFile工具用于将一行文件转为SchemaData
                SchemaData message = fileWatcher.parseFile(line);
                log.info("++++++++++++ message = {}", gson.toJson(message));
                //将meaasge发送给指定的topic
                kafkaTemplate.send(topic, gson.toJson(message));
            }
        }
 }
```
### 3.Kafka-Consumer
```
@KafkaListener(topics = {TOPIC})
public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("------------ record =" + record);
            log.info("------------ message =" + message);
        }
 }   
```
### 五、项目代码
仅供参考，转载请注明出处
[github源码](https://github.com/guangxush/SpringBoot_Kafka)
欢迎提问交流