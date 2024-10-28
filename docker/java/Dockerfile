# Use an official OpenJDK image to compile and run Java code
FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Java source code to the container
COPY . .

# Compile and run the Java program (happens at runtime)
CMD javac Main.java && java Main
