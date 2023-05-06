
package com.site.blog.my.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Redis配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class RedisConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;


    @Bean
    public StringRedisSerializer keySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        //序列化时将类的数据类型存入json，以便反序列化的时候转换成正确的类型
        ObjectMapper objectMapper = new ObjectMapper();

        // 解决jackson2无法反序列化LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern(pattern)));
        }
    }

    public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
        }
    }

    @Primary
    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
        LettuceConnectionFactory redisConnectionFactory,
        StringRedisSerializer keySerializer,
        Jackson2JsonRedisSerializer<Object> valueSerializer) {

        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //首先解决key的序列化方式
        redisTemplate.setKeySerializer(keySerializer);
        //解决value的序列化方式
        redisTemplate.setValueSerializer(valueSerializer);

        //解决hash类型的key序列化方式
        redisTemplate.setHashKeySerializer(keySerializer);
        //解决hash类型的value序列化方式
        redisTemplate.setHashValueSerializer(valueSerializer);
        return redisTemplate;
    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        JedisPoolConfig config = new JedisPoolConfig();
//        factory.setPoolConfig(config);
//        factory.setHostName(redisProperties.getHost());
//        factory.setPassword(redisProperties.getPassword());
//        factory.setPort(redisProperties.getPort());
//        factory.setDatabase(0);
//        return factory;
//    }

    @Bean
    public HashOperations<Object, Object, Object> hashOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<Object, Object> valueOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<Object, Object> listOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<Object, Object> setOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<Object, Object> zSetOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
