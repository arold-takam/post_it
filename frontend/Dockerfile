# --- Étape 1 : Build (Compilation) ---
FROM node:20-alpine AS build
WORKDIR /app

# Installation des dépendances
COPY package*.json ./
RUN npm install

# Copie du code et Build
COPY . .
# On lance les tests Vitest ici ! Si un test échoue, le build s'arrête.
RUN npm run test -- --run
RUN npm run build

# --- Étape 2 : Serveur de production (Nginx) ---
FROM nginx:stable-alpine

# On copie le build de Vite dans le dossier Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# On ajoute une petite config Nginx pour gérer le routage React (évite les 404 au refresh)
RUN echo 'server { \
    listen 80; \
    location / { \
        root /usr/share/nginx/html; \
        index index.html; \
        try_files $uri $uri/ /index.html; \
    } \
    location /api/v1 { \
        proxy_pass http://backend:8080/api/v1; \
    } \
}' > /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]