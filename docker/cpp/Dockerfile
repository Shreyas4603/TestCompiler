# Use an official GCC image to compile C++ code
FROM gcc:latest

# Set the working directory in the container
WORKDIR /app

# Copy the C++ code to the container
COPY . .

# Compile the C++ code
RUN g++ -o main main.cpp

# Run the compiled C++ program
CMD ["./main"]
