package com.market.order;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {
    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.product}")
    private String queueProduct;

    @Value("${message.queue.payment}")
    private String queuePayment;

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);}
    @Bean
    public Queue queueProduct(){
        return new Queue(queueProduct);}
    @Bean
    public Queue queuePayment(){
        return new Queue(queuePayment);}

    @Bean
    public Binding bindingProduct(){
        // bind : 어느 큐로 이동할건지
        // to : exchange => 위에서 bean으로 등록해서 내부적으로 알아서 exchange를 반환
        // with : 바인딩 이름
        return BindingBuilder.bind(queueProduct()).to(exchange()).with(queueProduct);
    }
    @Bean
    public Binding bindingPayment(){
        return BindingBuilder.bind(queuePayment()).to(exchange()).with(queuePayment);
    }

}
