Feature: Categories can be added to the library
  Description: The purpose of this feature is to test various business rules while adding a category

  Scenario : A category if unique can be added to the library
    Given A category Fiction
    When The category is saved
    Then A response code of 201 is obtained
    And An ID is returned
    Given A category Fiction
    When The category is saved
    Then A response code of 409 is obtained

