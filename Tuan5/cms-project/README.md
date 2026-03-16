# CMS Project — Kiến trúc 3 Layer

## Cấu trúc project

```
cms-project/
├── core/
│   └── pluginManager.js      # Microkernel core
├── plugins/
│   ├── seo/index.js          # Plugin: tạo SEO meta
│   └── markdown/index.js     # Plugin: render Markdown → HTML
├── backend/
│   ├── server.js             # Express API
│   ├── server.test.js        # Jest tests
│   ├── package.json
│   └── Dockerfile
├── frontend/
│   ├── src/
│   │   ├── main.jsx
│   │   └── App.jsx           # React UI
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   └── Dockerfile
└── docker-compose.yml
```

---

## Cách chạy

### Cách 1: Chạy thủ công (development)

**Backend:**
```bash
cd backend
npm install
node server.js
# → http://localhost:3001
```

**Chạy test:**
```bash
cd backend
npm test
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
# → http://localhost:3000
```

---

### Cách 2: Docker Compose (chạy tất cả 1 lệnh)

```bash
# Từ thư mục gốc cms-project/
docker-compose up --build

# Chạy nền
docker-compose up --build -d

# Dừng
docker-compose down
```

---

## API Endpoints

| Method | URL | Mô tả |
|--------|-----|-------|
| GET | /api/posts | Lấy tất cả posts |
| GET | /api/posts/:id | Lấy 1 post (có markdown + SEO) |
| POST | /api/posts | Tạo post mới |
| DELETE | /api/posts/:id | Xoá post |
| GET | /api/plugins | Liệt kê plugins đang chạy |

---

## Thêm plugin mới

1. Tạo file `plugins/tenPlugin/index.js`:
```js
module.exports = {
  name: "tenPlugin",
  execute(context) {
    // xử lý context
    return { ...context, ketQua: "..." };
  },
  hooks: {
    "post:beforeSave": (post) => post, // tuỳ chọn
  },
};
```

2. Đăng ký trong `backend/server.js`:
```js
const tenPlugin = require("../plugins/tenPlugin");
pluginManager.register("tenPlugin", tenPlugin);
```

3. Gọi plugin:
```js
pluginManager.run("tenPlugin", { ... });
```

Không cần sửa gì trong core!
