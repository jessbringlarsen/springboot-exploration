@customer
Feature: Customer

    Scenario: create customer
        Given a non existing customer with name 'Ole Hansen'
        When the customer request to be created
        Then expect a customer with name 'Ole Hansen' to be created


    # ***********************************
    # #HandsOn - Implement DeleteCustomer
    # ***********************************
    Scenario: delete customer
        Given a non existing customer with name 'Ole Hansen'
        When the customer request to be created
        And the customer is deleted
        Then expect that the customer is not found
