Go to Kafka home. 
Then execute below commands. 

- Start Zookeeper : ./bin/zookeeper-server-start.sh config/zookeeper.properties
- Start Kafka : ./bin/kafka-server-start.sh config/server.properties
- Fav Color Kafka Console Producer
    - bin/kafka-console-producer.sh --broker-list localhost:9092 --topic fav-color-input
- User Fav Color Kafka Console Consumer
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic user-fav-color-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
- Favourite color Count Kafka Console Consumer
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic fav-color-count-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
- Run FavColorApp class 
