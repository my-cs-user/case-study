import React, { useState, useEffect } from 'react';
import api from './api'; // Import the axios instance with interceptors
import './AuthPage.css';
import {Alert} from "react-bootstrap"; // Assuming you have a CSS file for styling

const AuthPage = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [loginForm, setLoginForm] = useState({ username: '', password: '' });
    const [registerForm, setRegisterForm] = useState({ username: '', password: '' });
    const [message, setMessage] = useState(null);
    const [messageType, setMessageType] = useState('');

    useEffect(() => {
        // On mount, check if the user is logged in by calling the hello API
        const token = sessionStorage.getItem('authToken');
        if (token) {
            fetchHello();
        }
    }, []);

    const handleInputChange = (e, formType) => {
        const { name, value } = e.target;
        if (formType === 'login') {
            setLoginForm({ ...loginForm, [name]: value });
        } else {
            setRegisterForm({ ...registerForm, [name]: value });
        }
    };

    const handleLogin = async () => {
        try {
            const response = await api.post('/auth/generateToken', loginForm);
            sessionStorage.setItem('authToken', response.data);
            setIsLoggedIn(true);
            displayMessage('Login successful!', 'success');
        } catch (error) {
            displayMessage('Login failed. Please check your credentials.', 'error');
        }
    };

    const handleRegister = async () => {
        try {
            await api.post('/auth/register', registerForm);
            displayMessage('Register successful!', 'success');
        } catch (error) {
            displayMessage('Registration failed. Try again.', 'error');
        }
    };

    const fetchHello = async () => {
        try {
            await api.get('/auth/hello');
            setIsLoggedIn(true);
        } catch {
            sessionStorage.removeItem('authToken');
            setIsLoggedIn(false);
        }
    };

    const handleLogout = () => {
        sessionStorage.removeItem('authToken');
        setIsLoggedIn(false);
    };

    const displayMessage = (msg, type) => {
        setMessage(msg);
        setMessageType(type);
        if (type === 'error') {
            console.error(msg);
        }
        if (window.messageTimeout) {
            clearTimeout(window.messageTimeout);
        }
        window.messageTimeout = setTimeout(() => {
            setMessage(null);
            setMessageType('');
        }, 5000);
    };

    return (
        <div className="auth-page">
            <div className="auth-container">

                {/* Message Alert */}
                {message && (
                    <Alert variant={messageType === 'error' ? 'danger' : 'success'}>
                        {message}
                    </Alert>
                )}
                {!isLoggedIn ? (
                    <div className="form-wrapper">
                        <div className="login-section">
                            <h2>Login</h2>
                            <input
                                type="text"
                                name="username"
                                placeholder="Username"
                                value={loginForm.username}
                                onChange={(e) => handleInputChange(e, 'login')}
                            />
                            <input
                                type="password"
                                name="password"
                                placeholder="Password"
                                value={loginForm.password}
                                onChange={(e) => handleInputChange(e, 'login')}
                            />
                            <button onClick={handleLogin}>Login</button>
                        </div>
                        <div className="register-section">
                            <h2>Create User</h2>
                            <input
                                type="text"
                                name="username"
                                placeholder="Username"
                                value={registerForm.username}
                                onChange={(e) => handleInputChange(e, 'register')}
                            />
                            <input
                                type="password"
                                name="password"
                                placeholder="Password"
                                value={registerForm.password}
                                onChange={(e) => handleInputChange(e, 'register')}
                            />
                            <button onClick={handleRegister}>Register</button>
                        </div>
                    </div>
                ) : (
                    <div className="logout-section">
                        <h2>Welcome!</h2>
                        <button onClick={handleLogout}>Logout</button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default AuthPage;
