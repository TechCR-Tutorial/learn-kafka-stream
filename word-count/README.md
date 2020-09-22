Go to Kafka home. 
Then execute below commands. 

- Start Zookeeper : ./bin/zookeeper-server-start.sh config/zookeeper.properties
- Start Kafka : ./bin/kafka-server-start.sh config/server.properties
- WordCount Kafka Console Producer
    - bin/kafka-console-producer.sh --broker-list localhost:9092 --topic word-count-input
- WordCount Kafka Console Consumer
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic word-count-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
- Run WordCountApp class. 
