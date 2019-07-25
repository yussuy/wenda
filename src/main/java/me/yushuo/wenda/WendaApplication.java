package me.yushuo.wenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@MapperScan("me.yushuo.wenda.dao")
public class WendaApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(WendaApplication.class, args);
	}
}
