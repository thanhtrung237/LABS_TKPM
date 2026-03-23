# Stage 1: Builder - môi trường chỉ cần lúc tạo image
FROM node:20 AS builder

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Runner - image cuối chỉ chứa những gì cần lúc chạy
FROM node:20-alpine

WORKDIR /app
COPY package*.json ./
RUN npm install --omit=dev
COPY --from=builder /app/dist ./dist

EXPOSE 3000
CMD ["node", "dist/index.js"]
