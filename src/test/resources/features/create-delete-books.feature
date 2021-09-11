Feature: Books can be added to and removed from library
  Description: The purpose of this feature is to test various business rules while adding or removing a book

  Background: Multiple categories are added to library
    Given Existing categories
      | Fiction   |
      | Adventure |
      | Travel    |

  Scenario Outline: A book can be added to the library unless the categories are unrecognized
    Given A title <Title>
    And An author <Author>
    And Categories <Categories>
    When The book is attempted to be saved
    Then A response code <ResponseCode> is obtained

    Examples:
      | Title  | Author   | Categories     | ResponseCode |
      | Book A | John Doe | Fiction        | 201          |
      | Book B | John Doe | Fiction, Drama | 400          |

  Scenario: A duplicate book with the same name and author cannot be added
    Given A title Book A
    And An author John Doe
    And Categories
      | Fiction |
    When The book is attempted to be saved
    Then A response code 201 is obtained
    Given A title Book A
    And An author John Doe
    And Categories
      | Fiction   |
      | Adventure |
    When The book is attempted to be saved
    Then A response code 409 is obtained

  Scenario: An existing book can be deleted
    Given A title Book A
    And An author John Doe
    And Categories
      | Fiction |
    When The book is attempted to be saved
    Then A response code 201 is obtained
    When The book id 1 is attempted to be deleted
    Then A response code 204 is obtained
    When The book id 1 is attempted to be deleted
    Then A response code 404 is obtained
