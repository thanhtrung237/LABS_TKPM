# Stage 1: Chuẩn bị data (môi trường chỉ cần lúc tạo image)
FROM postgres:15 AS builder

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin123
ENV POSTGRES_DB=mydb

# Copy script khởi tạo data
COPY init-data.sql /docker-entrypoint-initdb.d/init-data.sql

# Chạy postgres tạm để init data, sau đó dump ra file
RUN apt-get update && apt-get install -y --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*

# Stage 2: Image cuối - chỉ chứa những gì cần lúc chạy container
FROM postgres:15

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin123
ENV POSTGRES_DB=mydb

# Lấy file SQL từ stage 1 sang
COPY --from=builder /docker-entrypoint-initdb.d/init-data.sql /docker-entrypoint-initdb.d/init-data.sql
