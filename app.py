from flask import Flask, jsonify, request
from flask_cors import CORS
import random
import json
import os
import requests
from requests.adapters import HTTPAdapter
from requests.packages.urllib3.util.retry import Retry # type: ignore

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes

# Local JSON file path
# LOCAL_JSON_FILE = "R:/Experiments/Testing stuff/debugcountry/all_countries.json"
LOCAL_JSON_FILE = "all_countries.json"

# Function to load data from the local file
def load_local_data():
    if os.path.exists(LOCAL_JSON_FILE):
        print("Loading data from local JSON file...")
        try:
            with open(LOCAL_JSON_FILE, 'r', encoding='utf-8') as file:
                data = json.load(file)
                print(f"Loaded {len(data)} countries from the JSON file.")
                return data
        except UnicodeDecodeError as e:
            print(f"Unicode error while reading file: {e}")
        except json.JSONDecodeError as e:
            print(f"Error decoding JSON: {e}")
    else:
        print(f"Error: {LOCAL_JSON_FILE} not found.")
    return []

# Retry-enabled requests session
def create_retry_session():
    session = requests.Session()
    retry = Retry(total=3, backoff_factor=1, status_forcelist=[500, 502, 503, 504])
    adapter = HTTPAdapter(max_retries=retry)
    session.mount("http://", adapter)
    session.mount("https://", adapter)
    return session

# Load data
data = load_local_data()

# Endpoint to search for a country by name
@app.route('/search', methods=['GET'])
def search_country():
    country_name = request.args.get('country')
    if not country_name:
        return jsonify({"error": "No country name provided"}), 400

    country_name = country_name.capitalize()

    for country in data:
        if country["name"]["common"].lower() == country_name.lower():
            return jsonify(extract_country_data(country))

    return jsonify({"error": f"Country '{country_name}' not found"}), 404

# Endpoint to get a random country
@app.route('/random', methods=['GET'])
def random_country():
    if not data:
        return jsonify({"error": "No data available"}), 500
    country = random.choice(data)
    return jsonify(extract_country_data(country))

# Helper function to extract country data
def extract_country_data(country):
    currencies = country.get('currencies', {})
    currency_code = next(iter(currencies), "N/A")
    currency_info = currencies.get(currency_code, {})

    return {
        "country": country["name"]["common"],
        "country_code": country.get('cca2', "N/A"),
        "continent": country['continents'][0] if 'continents' in country else "N/A",
        "capital": country['capital'][0] if 'capital' in country and country['capital'] else "N/A",
        "population": country.get('population', "N/A"),
        "timezones": country.get('timezones', []),
        "currency": {
            "code": currency_code,
            "name": currency_info.get('name', "N/A"),
            "symbol": currency_info.get('symbol', "")
        },
        "flag_url": country['flags']['png'] if 'flags' in country else "N/A"
    }

if __name__ == '__main__':
    app.run(debug=True)
