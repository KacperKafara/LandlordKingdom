import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import "./index.css";
import { ProtectedRoutes, UnprotectedRoutes } from "./routes";
import AuthGuard from "./AuthGuard";

const router = createBrowserRouter([
  ...UnprotectedRoutes,
  {
    path: "/",
    Component: AuthGuard,
    children: ProtectedRoutes,
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router}></RouterProvider>
  </React.StrictMode>
);
