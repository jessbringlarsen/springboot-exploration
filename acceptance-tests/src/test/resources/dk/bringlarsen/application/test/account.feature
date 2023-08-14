@feature
Feature: Account

    Scenario: create account
        Given an existing customer
        When an account is request to be created
        Then expect an account to be created with a balance of 0

    Scenario: list accounts
        Given an existing customer
        When an account is request to be created
        Then expect 1 number of accounts to be created

    Scenario: deposit money
        Given an existing account named 'Savings' with balance of '100' dollars
        When '100' dollars are deposited into the account
        Then the account must have a balance of '200'

    Scenario: withdraw money
        Given an existing account named 'Savings' with balance of '100' dollars
        When '50' dollars are withdrawn from the account
        Then the account must have a balance of '50'

    Scenario: withdraw money on empty account
        Given an existing account named 'Savings' with balance of '0' dollars
        When '50' dollars are withdrawn from the account
        Then the operation is refused
