package com.example.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.AntPathMatcherGenericFileFilter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        // 多个from
        from("file:C:\\Users\\xjf\\Desktop\\hh\\god?autoCreate=true&delete=true")
                .from("file:C:\\Users\\xjf\\Desktop\\hh\\god2?autoCreate=true&delete=true")
                .filter((exchange) -> {
                            Map<String, Object> headers = exchange.getIn().getHeaders();
                            System.out.println("CamelFileLastModified=" + headers.get("CamelFileLastModified"));
                            System.out.println("CamelFileLength=" + headers.get("CamelFileLength"));
                            System.out.println("CamelFileName=" + headers.get("CamelFileName"));
                            if ((Long) headers.get("CamelFileLength") > 10L) {
                                System.out.println("move--------");
                                return true;
                            }
                            System.out.println("no-find--------");
                            return false;
                        }
                )
                .to("file:E:/2021/hhh/out?autoCreate=true");
    }


}