package com.camel.code.bean;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MoveFilesBean extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://shared/in?noop=true&delete=false")
        .to("file://shared/out");
    }
}