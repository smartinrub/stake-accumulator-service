Feature: Process player stake
  As an Business Operator
  I want to be able to get notified when a customer, playing a game, stake more than £X in a given Ys time window
  So that I can see the player account ID and cumulated amount within the time window

  Scenario: Do not send notification
    Given no messages stored
    When the message is received with amount 99
    Then do not receive message alert
    And do not store alert

  Scenario: Send notification with no messages stored
    Given no messages stored
    When the message is received with amount 101
    Then receive message alert with cumulated amount 101
    And store alert with amount 101

  Scenario: Send notification with messages stored within period of time
    Given message stored within period of time with amount 99
    When the message is received with amount 2
    Then receive message alert with cumulated amount 101
    And store alert with amount 101
