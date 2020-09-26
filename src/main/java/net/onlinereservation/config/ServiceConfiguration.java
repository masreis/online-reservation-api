package net.onlinereservation.config;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ServiceConfiguration {

	@Bean
	public ModelMapper modelMapper1() {
		ModelMapper modelMapper = new ModelMapper();
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}
}
