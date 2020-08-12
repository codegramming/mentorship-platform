# BUILD
FROM node:14.4.0-alpine3.10 AS build
WORKDIR /app

COPY package.json yarn.lock ./
RUN yarn install

COPY . ./

ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}

RUN yarn run build

# RUN
FROM nginx:1.19.0-alpine

COPY --from=build /app/build /var/www
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]