package com.galaxia.engdev.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "neptune")
public class NeptuneConfig {

	@NotNull
	private String serviceCode;
}
