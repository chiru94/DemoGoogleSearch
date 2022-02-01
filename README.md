# DemoGoogleSearch

The GoogleSearchTest.java works in the following way

- Case: Write selenium automation which has the following behavior.
Given a keyword, an URL, two numbers (N, M). It does a Google search using the keyword and tests.
1. if the URL has at least M listing in the first N pages.
2. If there is a Google Play store entry with a rating > 3

- Prerequisites: Selenium-Java (v3.141.59), TestNG (v7.4.0), ChromeDriver (version depending upon the machine on which it is executed)

- Initially capture xpaths to locate webelements (locators for Search Text, Button, Search Result Links, Search Result Rating, Pagination numbers, etc.)

- Create a TestNG class with following methods with the Annotations
  - @BeforeClass (beforeClass()) - Used to initiate Chrome browser
  - @BeforeMethod (OpenGooglePage()) - To go to Google.com and search for the keyword provided (e.g. - netflix)
  - @Test (GoogleSearchMethod1()) - To perform test automation for scenario 1 using the locators & Assert/Verify if it matches the requirement
  - @Test (GoogleSearchMethod2()) - To perform test automation for scenario 2 using the locators & Assert/Verify if it matches the requirement
  - @AfterClass (afterClass()) - To close the browser after test case execution
