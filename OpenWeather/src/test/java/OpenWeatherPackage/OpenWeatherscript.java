package OpenWeatherPackage;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.simple.*;
import org.testng.annotations.*;

import io.restassured.response.Response;

public class OpenWeatherscript {
	String ID = null;
	String appID = "5796abbde9106b7da4febfae8c44c232";
	
	@BeforeClass
    public void setup() {
        baseURI = "https://api.openweathermap.org";
    }
	
	

	
	@Test(priority = 1)
	public void openWeather_01_SearchAPI(){
		 JSONObject requestbody = new JSONObject();
		 		 		 
		 Response response =given().
		 header("Origin","https://openweathermap.org").
		 header("Accept-Language","en-US,en;q=0.9").
		 header("Connection","keep-alive").
		 header("Referer","https://openweathermap.org/").
		 when().
		 get("/data/2.5/find?q=Jordan&appid="+ appID +"&units=metric").
		 then().
		 statusCode(200).
		 body("message", equalTo("accurate"))
		 .extract()
		 .response();
		 
		 ID= response.path("list[0].id");		 
	}
	
	@Test(priority = 2)
	public void OpenWeather_02_Cities(){
		 Response response =given().
		 header("Origin","https://openweathermap.org").
		 header("Accept-Language","en-US,en;q=0.9").
		 header("Connection","keep-alive").
		 header("Referer","https://openweathermap.org/"). 
		when().
		 get("https://a.maps.owm.io/weather/cities/5/18/12.geojson?appid="+ appID).
		 then().
		 statusCode(200).
		 body("type", equalTo("FeatureCollection")).
		 extract()
		 .response();
		 
	}
	
	@Test(priority = 3)
	public void OpenWeather_03_Search_By_ID(){
		 Response response =given().
		 header("Origin","https://openweathermap.org").
		 header("Accept-Language","en-US,en;q=0.9").
		 header("Connection","keep-alive").
		 header("Referer","https://openweathermap.org/"). 
		when().
		 get("data/2.5/weather?id="+ ID +"&appid="+ appID).
		 then().
		 statusCode(200).
		 body("id", equalTo(ID)).
		 extract()
		 .response();
		 long responseTime = response.time();
		 System.out.print("response time:"+ responseTime);
	}
	
}
