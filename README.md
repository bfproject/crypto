## Description

Display cryptocurrency pairs

Screenshot
| Marketplace  | Marketplace offline |
| ------------- | ------------- |
| ![Marketplace](https://github.com/bfproject/crypto/assets/13312345/89ec24fb-d00d-482b-849e-32c28f95fe46) | ![Marketplace_offline](https://github.com/bfproject/crypto/assets/13312345/30e63489-e294-4cd8-b09f-db19ca3cbd4f) |

The data is retrieved from https://docs.bitfinex.com/reference/rest-public-tickers

The project follows an MVI architecture.

A single activity approach is implemented.

Multimodules are used to separate the different layers.


Some of the main libraries used are:
- Jetpack compose to build the UI
- Hilt for dependency injection 
- Retrofit and okhttp to get data from the server
- Room to store the data in database
- Junit, Mockk and Turbine for testing
