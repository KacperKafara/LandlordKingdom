import { FC } from "react";
import { Navigate } from "react-router-dom";

const AdminPage: FC = () => {
  return <Navigate to="users" />;
};

export default AdminPage;
