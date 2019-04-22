package com.example.test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.management.OperatingSystemMXBean;

@RestController
public class Controller {

	@RequestMapping(path = "/alpha")
	public String alphaInformation(@RequestBody String cust) throws JsonProcessingException {

		String json = cust;
		String jsonData = json;
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		Map<String, String> mapName = null;
		try {
			mapName = (Map<String, String>) om.readValue(jsonData, HashMap.class);
			// System.out.println(mapName.);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json = om.writeValueAsString(mapName);
		System.out.println("this is a =" + json);
		return json;
	}

	@RequestMapping(path = "/flatten")
	public String flattenWithInfo(@RequestBody String cust) {
		System.out.println(cust);
		JSONObject json2 = new JSONObject(cust);
		String A = json2.get("city-list").toString();
		String B = "";
		System.out.println(A);
		for (int i = 1; i < A.length() - 1; i++) {
			if (A.charAt(i) == '"') {
				continue;
			}
			B += A.charAt(i);
		}
		json2.remove("city-list");
		json2.putOnce("city-list", B);
		System.out.println(B);
		return json2.toString();
	}

	@RequestMapping(path = "/status", method = RequestMethod.GET)
	public String status() {
		String total = "";

		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		final long RAM_TOTAL = Runtime.getRuntime().totalMemory();
		final long RAM_FREE = Runtime.getRuntime().freeMemory();
		final long RAM_USED = RAM_TOTAL - RAM_FREE;
		final long RAM_TOTAL_MB = RAM_TOTAL / 8 / 1024;
		final long RAM_FREE_MB = RAM_FREE / 8 / 1024;
		final long RAM_USED_MB = RAM_USED / 8 / 1024;
		final double RAM_USED_PERCENTAGE = ((RAM_USED * 1.0) / RAM_TOTAL) * 100;
		total += "{" + "mem-used-pct" + ":" + RAM_USED_PERCENTAGE + "," + "disc-space-avail" + ":" + "[";
		System.out.println("This is Total Ram=" + RAM_USED_PERCENTAGE);
		NumberFormat nf = NumberFormat.getNumberInstance();
		for (Path root : FileSystems.getDefault().getRootDirectories()) {

			System.out.print(root + ": ");
			try {
				FileStore store = Files.getFileStore(root);
				String space = nf.format(store.getUsableSpace());
				total += "{" + "discname" + ":" + root + "," + "availbytes" + ":" + space + "}," + "\n";

			} catch (IOException e) {
				System.out.println("error querying space: " + e.toString());
			}
		}

		/*
		 * probably there is no java code through which we can find cpu directly we can
		 * find by command and all may be i am wrong if yes please correct me @Prabhakar
		 * Kumar Ojha
		 */

		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		total += "]," +"cpu-used-pct"+":"+ operatingSystemMXBean.getProcessCpuLoad() + "}";
		JSONObject json=new JSONObject();
		
		System.out.println("\n\n"+total);
		return total;

	}

}
