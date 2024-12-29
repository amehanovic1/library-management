FROM node:16-alpine AS build

WORKDIR /app

COPY package.json /app
COPY package-lock.json /app

RUN npm install -g @angular/cli \ && apt-get update \ && apt-get install -y git \ && npm install

COPY . .

RUN npm run build --prod

FROM nginx:alpine

COPY --from=build /app/dist/lms-frontend /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
