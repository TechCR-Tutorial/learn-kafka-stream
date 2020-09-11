package techcr.kafka.favcolor;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

public class FavColorApp {

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "fav-color-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        List<String> favColors = Arrays.asList("BLUE", "RED", "GREEN");
        KStreamBuilder builder = new KStreamBuilder();

        KStream<String, String> favColorStream = builder.stream("fav-color-input");
        KStream<String, String> userFavColor = favColorStream
            .peek((key, value) -> System.out.println("Fav Color Entered Key: " + key + " Value: " + value))
            .filter((key, value) -> null != value && value.contains(","))
            .selectKey((key, value) -> value.split(",")[0].toUpperCase())
            .mapValues(value -> value.split(",")[1].toUpperCase())
            .filter((key, value) -> favColors.contains(value.toUpperCase()));

        userFavColor.to(Serdes.String(), Serdes.String(), "user-fav-color-output");

        KTable<String, String> userColorTable = builder.table("user-fav-color-output");
        KTable<String, Long> favColorCount = userColorTable.groupBy((key, value) -> new KeyValue<>(value, value))
            .count("FavColorCount");
        System.out.println(favColorCount.toString());
        favColorCount.to(Serdes.String(), Serdes.Long(), "fav-color-count-output");

        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();



        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private enum FavColor {
        BLUE,
        RED,
        GREEN;
    }
}
