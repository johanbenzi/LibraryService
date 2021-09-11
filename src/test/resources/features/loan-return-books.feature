Feature: Books can be loaned from and returned to library by users
  Description: The purpose of this feature is to test various business rules while adding or removing a book

  Background: Multiple categories are added to library
    Given Existing categories
      | Fiction   |
      | Adventure |
      | Travel    |
    And Existing Books
      | Title  | Author   | Categories      |
      | Book A | John Doe | Fiction         |
      | Book B | John Doe | Fiction, Travel |
      | Book C | John Doe | Fiction, Travel |
      | Book D | John Doe | Fiction, Travel |

  Scenario: Only upto 3 existing currently available books can be loaned by a user
    Given A user with id 1
    And Books and Authors
      | Title  | Author   |
      | Book A | John Doe |
      | Book B | John Doe |
    When The book is attempted to be loaned
    Then A response code 200 is obtained
    And The books are provided
      | Title  | Author   | Categories      |
      | Book A | John Doe | Fiction         |
      | Book B | John Doe | Fiction, Travel |
    Given Books and Authors
      | Title  | Author   |
      | Book A | John Doe |
    When The book is attempted to be loaned
    Then A response code 409 is obtained
    Given Books and Authors
      | Title  | Author   |
      | Book E | Jane Doe |
    When The book is attempted to be loaned
    Then A response code 404 is obtained
    Given Books and Authors
      | Title  | Author   |
      | Book C | John Doe |
    When The book is attempted to be loaned
    Then A response code 200 is obtained
    And The books are provided
      | Title  | Author   | Categories      |
      | Book C | John Doe | Fiction, Travel |
    Given Books and Authors
      | Title  | Author   |
      | Book D | John Doe |
    When The book is attempted to be loaned
    Then A response code 422 is obtained


  Scenario: Return loaned books
    Given A user with id 1
    And Books and Authors
      | Title  | Author   |
      | Book A | John Doe |
      | Book B | John Doe |
      | Book C | John Doe |
    And The book is attempted to be loaned
    And Books and Authors
      | Title  | Author   |
      | Book A | John Doe |
    When The book is attempted to be returned
    Then A response code 200 is obtained
    Given Books and Authors
      | Title  | Author   |
      | Book D | Jane Doe |
    When The book is attempted to be returned
    Then A response code 422 is obtained