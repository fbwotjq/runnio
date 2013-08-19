Feature: Uploading of Jar
  As a tester
  I want to upload the jar with my step definitions and tests
  In order to configure my cucumber execution

  Scenario: Uploading of jar
    Given a test jar
    When I upload it to my runnio
    Then I should be able to load 3 tags