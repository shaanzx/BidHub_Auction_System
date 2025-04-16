package lk.shaanzx.online_auction_system_backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineAuctionSystemBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineAuctionSystemBackEndApplication.class, args);}
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
