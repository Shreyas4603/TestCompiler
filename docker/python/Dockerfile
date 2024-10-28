# Use an official Python runtime as a parent image
FROM python:3.9-slim

# Set the working directory in the container
WORKDIR /app



# Copy the Python code to the container (this will be mounted at runtime)
COPY . .

# Run the Python script with arguments (input will be passed via CMD in the Spring Boot service)
CMD ["python", "main.py"]
