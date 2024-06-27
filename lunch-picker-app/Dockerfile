# stage 1
FROM node:slim as build
WORKDIR /usr/local/app
COPY ./ /usr/local/app/

# Install all the dependencies
RUN npm install
RUN npm run build

# stage 2
FROM nginx:1.27.0-alpine as serve

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY dist/lunch-picker-app/browser /usr/share/nginx/html
