FROM node:lts-alpine

# Create app directory
WORKDIR /app

# Expose port for service
EXPOSE 3000

# Install and configure `serve`.
RUN npm install -g serve

# Install bash
RUN apk update && apk add bash

# Copy source code to image
COPY . .

# Install dependencies
RUN npm install

# Build app and start server from script
CMD ["/app/run.sh"]