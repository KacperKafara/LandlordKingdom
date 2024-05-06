import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import "./index.css";
import { ProtectedRoutes, UnprotectedRoutes } from "./routes";
import AuthGuard from "./AuthGuard";
import "@/i18n";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Toaster } from "./components/ui/toaster";

const router = createBrowserRouter([
  ...UnprotectedRoutes,
  {
    path: "/",
    Component: AuthGuard,
    children: ProtectedRoutes,
  },
]);

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router}></RouterProvider>
      <Toaster />
    </QueryClientProvider>
  </React.StrictMode>
);
