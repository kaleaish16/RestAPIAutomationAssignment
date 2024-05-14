package assesment_sdet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExchangeRates {

	String url = "https://open.er-api.com/v6/latest/USD";

	// API call is successful and returns valid price.
	@Test(priority = 1)
	public void testCheckValidPrice() {
		given().log().all()
		.when().get(url)
		.then().log().all().statusCode(200);
	}

	/*
	 * Check the status code and status returned by the API response. API could
	 * return multiple statuses like SUCCESS, FAILURE etc. Make sure this is catered
	 * for.
	 */
	@Test(priority = 2)
	public void testCheckStatus() {
		Response res = given().log().all().contentType(ContentType.JSON)
                      .when().get(url);

		int statusCode = res.getStatusCode();
		JSONObject js = new JSONObject(res.getBody().asString());
		String status = js.has("status") ? js.getString("status") : "SUCCESS";

		assertEquals(200, statusCode, "API response status code is not 200");
		String expectedStatus = "SUCCESS";
		assertEquals(status, expectedStatus, "API response status is not as expected");
	}

	/*
	 * Fetch the USD price against the AED and make sure the prices are in range on
	 * 3.6 – 3.7.
	 */
	@Test(priority = 3)
	public void testVerifyAEDPrice() {
		Response res = given().contentType(ContentType.JSON)

				.when().get(url);

		Assert.assertEquals(res.getStatusCode(), 200);
		JSONObject response = new JSONObject(res.getBody().asString());

		double AEDRate = response.getJSONObject("rates").getDouble("AED");

		assertTrue(AEDRate >= 3.6 && AEDRate <= 3.7, "AED price is not in the range of 3.6 to 3.7");
		System.out.println("Current rate of AED is: " + AEDRate);

	}

	/*
	 * Make sure API response time is not less then 3 seconds then current time in
	 * second. Timestamp is returned in the API response.
	 */
	@Test(priority = 4)
	public void testVerifyTimeStamp() {
		long currentTime = System.currentTimeMillis();
		System.out.println("Response time in milliseconds: " + currentTime);

		Response res = given().contentType(ContentType.JSON)
                      .when().get(url);

		JSONObject js = new JSONObject(res.getBody().asString());
		long respTimestampinms = System.currentTimeMillis();

		if (js.has("timestamp")) {
			respTimestampinms = js.getLong("timestamp") * 1000;
		} else {

			long apiResTime = respTimestampinms - currentTime;
			System.out.println("API Repsonse time is " + apiResTime + " milliseconds");
			assertTrue(apiResTime >= 3000, "API response time is less than 3 seconds");
		}

	}

	@Test(priority = 5)
	// Verify that 162 currency pairs are returned by the API.

	public void verifyTotalCurrencyPairs() {
		Response res = given().contentType(ContentType.JSON)

				.when().get(url);

		JSONObject jo = new JSONObject(res.getBody().asString()); // Converting response to JsonObject
		JSONObject rateobj = jo.getJSONObject("rates");

		JSONArray currencyArray = rateobj.names();

		if (currencyArray != null && currencyArray.length() == 162) {
			System.out.println("162 currency pairs are returned by the API.");
		} else {
			System.out.println("API did not return 162 currency pairs.");
		}

		System.out.println(currencyArray);

	}

	@Test(priority = 6)
	/*
	 * Make sure API response matches the Json schema. Generate a schema from the
	 * API response.
	 */
	public void testVerifyIfAPIResponseMatchesJSONSchema() {

		Response res = given().contentType(ContentType.JSON)
                       .when().get(url);

		JSONObject js = new JSONObject(res.getBody().asString());
		String schema = js.toString();// generating JSONSChema from response
		System.out.println("JSON Schema: " + schema);
		res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
	}
}
