import express from 'express';

const app = express();
const PORT = 3000;

app.get('/', (req: express.Request, res: express.Response) => {
  res.json({ message: 'Hello from Docker multi-stage build!' });
});

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
