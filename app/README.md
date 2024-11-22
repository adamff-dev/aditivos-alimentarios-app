# Aditivos Alimenticios - Android App

## Description

The **Aditivos Alimenticios** app allows users to search for food additives by number or name and display their toxicity levels. The data is retrieved through web scraping from a table on [www.aditivos-alimentarios.com](https://www.aditivos-alimentarios.com/) and stored locally in a database for offline searches. The database is updated when an internet connection is available.

## Features

- Search for additives by number or name.
- Display the toxicity level of each additive.
- Supports searching for multiple terms at once.
- Stores additive data locally for offline use.
- Automatically updates the database when an internet connection is available.

## Technologies

- **Kotlin**: Programming language used for Android app development.
- **SQLite**: Local database for storing food additives data.
- **Web Scraping**: Data retrieval from [aditivos-alimentarios.com](https://www.aditivos-alimentarios.com/) using scraping techniques.
- **Coroutines**: Used for background operations and threading.

## Requirements

- Android 6.0 or higher
- Kotlin 1.5 or higher
- Android Studio 4.2 or higher

## Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/adamff-dev/aditivos-alimentarios-app.git

2. Open the project in Android Studio.

3. Build and run the project on an Android emulator or device.

## Usage

- To search for additives, enter the term (number or name) in the search bar and press "Search".
- The results will display the additive name, number, and its toxicity level.
- The app supports searching for multiple terms at once, separated by commas.

## Contributing

If you'd like to contribute to the project, feel free to fork the repository, make your changes, and submit a pull request. All contributions are welcome!ç
