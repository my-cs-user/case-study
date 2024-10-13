import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 10000,
    headers: { 'Content-Type': 'application/json' },
});

// Interceptor to add token to every request
api.interceptors.request.use(
    (config) => {
        const token = sessionStorage.getItem('authToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Interceptor to handle errors globally
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            // Clear session and redirect to login
            sessionStorage.removeItem('authToken');
            alert('You Must Login');
            window.location.href = '/login'; // Redirect to login
        }
        return Promise.reject(error);
    }
);

export default api;
