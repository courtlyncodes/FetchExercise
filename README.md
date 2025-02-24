# Fetch Exercise

This repository implements functionality for fetching data from a given API, sorting the items by their item numbers and then by name, and displaying them in a list.

## Features
- Fetches list items from a provided API.
- Displays the items in a lazy horizontal grid with items are grouped by their list number.
- Each group of items is given a different font color for readability.

## Tests
The project includes both unit tests and instrumentation tests to ensure the app functions as expected:

### Unit Tests:
- Repository returns data: Ensures the repository returns a non-empty list of items.
- Repository fetches from cache: Ensures that when the data is cached in the database, the repository fetches it from the cache instead of making another network call.
- Filters out blank or null names: Ensures that only items with non-null and non-blank names are returned.

### Android Instrumentation Test:
- ApiService network call test: Ensures the API service makes network calls as expected and the data returned from the API matches the expected format.

## Getting Started
- Clone this repository: `git clone https://github.com/yourusername/fetch-exercise.git`
- Open the project in Android Studio or your preferred IDE.
- Sync the project with Gradle files.
