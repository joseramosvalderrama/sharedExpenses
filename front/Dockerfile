FROM node:lts-alpine AS build-step

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

FROM nginx:stable-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build-step /app/build /frontend/build
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]