package com.bcgplatinion.serverinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.json.*;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServerinfoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ServerinfoApplication.class, args);
	}

	@GetMapping(path = "/api/serverinfos", produces = "application/json")
	public String getServerInfos() {

		String env = System.getenv("VCAP_APPLICATION");
		if (env == null) return ("Error : not running on Cloud Foundry environment");

		JSONObject cfVars = new JSONObject(env);
		String space = cfVars.getString("space_name");
		String organization = cfVars.getString("organization_name");
		String appName = cfVars.getString("application_name");


		return String.format("{\"organization\": \"%s\", \"space\": \"%s\", \"app\": \"%s\"}", organization, space, appName);
	}

	@GetMapping(path = "/api/ping", produces = "application/json")
	public String ping() {

		String env = System.getenv("VCAP_APPLICATION");
		if (env == null) return ("Error : not running on Cloud Foundry environment");

		JSONObject cfVars = new JSONObject(env);
		String space = cfVars.getString("space_name");
		String appName = cfVars.getString("application_name");


		return String.format("{\"message\": \"pong\", \"from\": \"%s - %s\"}", space, appName);
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}
