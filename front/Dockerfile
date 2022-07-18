# Selecciona la imagen base
FROM node:lts-alpine
# Definimos el directorio de trabajo en /usr/src/app/
WORKDIR /usr/src/app/
# Copiamos los ficheros de la aplicación
COPY src /usr/src/app/src
COPY public /usr/src/app/public
COPY package.json /usr/src/app/
COPY tsconfig.json /usr/src/app/
# Instalamos las dependencias que necesita la app
RUN npm install
# Indica el puerto que expone el contenedor
EXPOSE 3000
# Comando que se ejecuta cuando se arranque el contenedor
CMD ["npm", "start"]