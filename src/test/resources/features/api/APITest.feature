@api
  Feature: Validate API
    Scenario: Verify user can create new user
      Given user set up request for "api/v1/auth" end point
      And set query parameter "username" to "admin@demo.io"
      And set query parameter "password" to "Demo123!"
      When user perform POST request
      Then verify status code is 200
      And verify response body has a token
      When user set up request for "api/v1/user" end point
      And set token for authorization
      And set body for the create user request
      And set header value "Content-Type" to "application/json"
      When user perform POST request
      Then verify status code is 201
      And validate new user created in database
