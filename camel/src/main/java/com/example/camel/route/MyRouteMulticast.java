package com.example.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyRouteMulticast extends RouteBuilder {


    /**
     * 重点是要在每个分支中后end结尾
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        from("file:C:\\Users\\xjf\\Desktop\\hh\\check?autoCreate=true&delete=true")
                // 多播
                .multicast()
                .parallelProcessing()
                .filter((exchange) -> {
                    Map<String, Object> headers = exchange.getIn().getHeaders();
                    if ((Long) headers.get("CamelFileLength") > 100000000L) {
                        return true;
                    }
                    return false;
                })
                .to("file:E:/2021/hhh/big?autoCreate=true").end()
                .filter((exchange) -> {
                    Map<String, Object> headers = exchange.getIn().getHeaders();
                    if ((Long) headers.get("CamelFileLength") < 10000000L) {
                        return true;
                    }
                    return false;
                }).to("file:E:/2021/hhh/toSmall?autoCreate=true").end()
                .filter((exchange) -> {
                    Map<String, Object> headers = exchange.getIn().getHeaders();
                    if ((Long) headers.get("CamelFileLength") <= 100000000L &&(Long) headers.get("CamelFileLength") >=10000000L) {
                        return true;
                    }
                    return false;
                }).to("file:E:/2021/hhh/small?autoCreate=true").end();
    }


}