The Rewards Calculator is a Spring Boot application designed to calculate reward points for customers based on their transactions. The application uses a RESTful API to provide reward points for each customer per month and in total, based on the transaction data provided.

### Rewards Calculation

- **Points Calculation:**
  - 2 points for every dollar spent over $100 in each transaction.
  - 1 point for every dollar spent between $50 and $100 in each transaction.
  - Example: A $120 purchase earns 90 points (2 points x $20 + 1 point x $50).

## API Endpoints

URL: /api/rewards/calculate
Method: GET
Description: Calculates the reward points for a customer based on transactions within a specified date range
http://localhost:8080/api/rewards/calculate?customerId=1&start=2024-01-01T00:00:00&end=2024-03-31T23:59:59"
## Example Response
json
Copy code
{
  "JANUARY": 150,
  "FEBRUARY": 200,
  }

