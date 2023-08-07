# Freelancing

This is a web-based REST API designed to manage and track work shifts for freelancers. It allows clients to schedule shifts for freelancers, calculate payments, and provides filtering options for easy shift retrieval.

## Features

- Create, edit, and manage shifts for freelancers.
- Calculate payment for each shift based on hourly rates and duration.
- Filter shifts based on various criteria such as start date, end date, worker ID, and client ID.
- User-friendly API endpoints for easy integration with frontend applications (TODO!).

## Tech Stack

- Java (Spring Boot) for API.
- MySQL database for data storage.
- RESTful API for communication between frontend and backend.
- Git for version control.
- GitHub for code hosting and project management.

## API Endpoints

- `GET /api/shifts/getAllShifts`: Get a list of all shifts.
- `POST /api/shifts/getFiltered`: Get filtered shifts based on criteria provided in the request body.

## Usage

1. Create shifts using the API or your preferred frontend interface.
2. Query existing shifts using the provided API endpoints, applying filters as needed.
3. Add users/clients/shifts to the database using the API endpoints.
4. Calculate payments and manage shifts easily.

## Contributing

If anyone actually sees this project....... Contributions are more than welcome! If you find any bugs or want to add new features, feel free to open an issue or submit a pull request. Please even comment your suggestions.

## Planned updates

1. Unify the existing code structures to make them more consistent.
2. An actual user interface with which to interact with this project!
3. Add my exisitng MySQL database.
4. Add secure user authentication, with hashing and salting.
