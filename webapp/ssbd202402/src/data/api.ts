import axios from "axios";
export const api = axios.create({
  baseURL: "http://localhost:8080/ssbd02/",
  timeout: 1000,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use((config) => {
      const token = localStorage.getItem("token");
      if (token && !config.url?.includes("auth")) {
        config.headers.Authorization = `Bearer ${token}`;
      }

      return config;
    },
    (error) => {
      Promise.reject(error);
    }
);

api.interceptors.response.use((response) => response, (error) => {
  if (error.response.status === 401) {
    // handle unauthorized
  }
  return Promise.reject(error);
});