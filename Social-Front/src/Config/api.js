import axios from "axios";

export const API_BASE_URL = "http://localhost:8080";

export const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    }
});

// Add a request interceptor to attach the token
api.interceptors.request.use(
    config => {
        const jwtToken = localStorage.getItem("jwt");
        if (jwtToken) {
            config.headers["Authorization"] = `Bearer ${jwtToken}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
