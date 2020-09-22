Go to Kafka home. 
Then execute below commands. 

- Start Zookeeper : ./bin/zookeeper-server-start.sh config/zookeeper.properties
- Start Kafka : ./bin/kafka-server-start.sh config/server.properties
- Create bank-transaction topic
    - bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic bank-transaction
- Create bank-balance-exactly-once topic
    - bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic bank-balance-exactly-once cleanup.policy=compact
- Create Console Consumer to topic bank-balance-exactly-once
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic bank-balance-exactly-once --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

