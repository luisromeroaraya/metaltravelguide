# Step 1: Use Node.js to build the Angular application
FROM node:16 AS build
WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the Angular application source code
COPY . .

# Build the Angular application for production
RUN npm run build

# Step 2: Use an Nginx image to serve the Angular application
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy the built Angular application from the build stage
COPY --from=build /app/dist/frontend ./

# Expose the default Nginx port
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
