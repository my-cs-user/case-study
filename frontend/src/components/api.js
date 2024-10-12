import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // API'nin temel URL'si
  timeout: 10000, // 10 saniyelik bir timeout
  headers: {
    'Content-Type': 'application/json',
    // Diğer default headerlar
  }
});

// Örneğin her request'te auth token eklemek için interceptor:
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

// Error handling için response interceptor
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Genel hata mesajlarını burada yakalayabilirsiniz
    if (error.response && error.response.status === 401) {
      // Unauthorized durumunda kullanıcıyı logout edebilir ya da login sayfasına yönlendirebilirsiniz
    }
    return Promise.reject(error);
  }
);

export default api;