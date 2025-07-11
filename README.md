# Aditivos Alimentarios - Android App

## Description

The **Aditivos Alimentarios** app allows users to search for food additives by number or name and view their toxicity levels. Data is retrieved from a local database, which is automatically updated when a new version is available and the device has an internet connection.

## Features

- Search for additives by number or name.
- Display the toxicity level of each additive.
- Supports searching for multiple terms at once, separated by commas.
- Stores data locally for offline access.
- Automatically updates the local database when a new version is detected.

## Screenshot

<div style="display: flex; gap: 20px;">
  <img src="https://i.imgur.com/ujSpHQp.jpeg" width="300" />
  <img src="https://i.imgur.com/ebp2eUF.jpeg" width="300" />
  <img src="https://i.imgur.com/3XZDzhs.jpeg" width="300" />
</div>

## Contribution

We welcome contributions of any kind — code, documentation, bug reports, or feature suggestions.

If you find this project helpful and want to support its development, consider making a donation.

Your support helps keep the project active and maintained. Thank you! 🙌

<a target="_blank" href="https://www.buymeacoffee.com/rSiZtB3"><img style="width: 200px" src="https://i.imgur.com/KCk0bxY.png" /></a>

## Technologies

- **Kotlin**: Programming language used for Android app development.
- **SQLite**: Local database for storing food additives data.
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

# Database Source
The app's local database is generated by a separate Python project: Food Additives Scraper.

This scraper collects food additive data from aditivos-alimentarios.com using Selenium and Python. It automatically detects installed browsers, manages WebDrivers, and saves the results to a structured database used by this Android app.

You can find and contribute to the scraper project here:
👉 https://github.com/adamff-dev/food-additives-scraper

## Contributing

If you'd like to contribute to the project, feel free to fork the repository, make your changes, and submit a pull request. All contributions are welcome!ç
