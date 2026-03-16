FROM node:18-alpine
WORKDIR /app

# Copy core và plugins trước
COPY core/ ./core/
COPY plugins/ ./plugins/

# Copy backend
COPY backend/package*.json ./backend/
WORKDIR /app/backend
RUN npm install

COPY backend/ .

EXPOSE 3001
CMD ["node", "server.js"]
