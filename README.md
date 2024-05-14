Given the API: hƩps://open.er-api.com/v6/latest/USD
Returns the USD rates against mulƟple currency.

Task

 Create a test framework using Java/JVM language framework.
o BDD approach would be an added advantage.
 Write necessary tests to cover the accep0tance criteria below.
 Make sure code is modular and easily understood.
 Structure the code properly so that we making changes are easier.
 Upload the soluƟon in bitbucket/GitHub and share the link of the repo
 Include a README.md file.

Acceptance criteria

 API call is successful and returns valid price.
 Check the status code and status retuned by the API response.
o API could return mulƟple statuses like SUCCESS, FAILURE etc. Make sure this is
catered for.

 Fetch the USD price against the AED and make sure the prices are in range on 3.6 – 3.7
 Make sure API response Ɵme is not less then 3 seconds then current Ɵme in second.
o Timestamp is returned in the API response.
 Verify that 162 currency pairs are retuned by the API.
 Make sure API response matches the Json schema
o Generate a schema from the API response.


**Project set up:**

**dependencies needed:**
rest assured
jackson databind
testng
json path
json schema validator

**Test methods:**
testCheckValidPrice - Checks whether API call is successful and returns valid price.
testCheckStatus - Check the status code and status returned by the API response.
testVerifyAEDPrice- Checks whether the USD price against the AED and also checks whether the prices are in range between
	 * 3.6 â€“ 3.7.
testVerifyTimeStamp- Check whether API response time is not less then 3 seconds then current time.
verifyTotalCurrencyPairs - Verify whether 162 currency pairs are returned by the API or not.
testVerifyIfAPIResponseMatchesJSONSchema - generates a json schema and Verifies whether API response matches the Json schema

