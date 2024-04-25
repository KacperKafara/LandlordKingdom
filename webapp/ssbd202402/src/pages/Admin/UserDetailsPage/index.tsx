import { FC, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { fetchUser } from "@/data/fetchUser";
import { useQuery } from "react-query";
import { NavLink, useNavigate, useParams } from "react-router-dom";

const UserDetailsPage: FC = () => {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const { id } = useParams<{ id: string }>(); 

    const { data } = useQuery(['user', id], () => id ? fetchUser(id) : Promise.resolve(null), {
      onError: () => {
          navigate("/admin/users");
      }
  });

  useEffect(() => {
      if (!id) {
          navigate("/admin/users");
      }
  }, [id, navigate]);
    
    return (
        <>
        
        <NavLink to={`/admin/users`}>
          {t("userDetailsPage.goBack")}
        </NavLink>
        <div>
          {data?.firstName}
        </div>
        <div>
          {data?.lastSuccessfulLogin}
        </div>
        <div>
          {data?.lastFailedLogin}
        </div>
        
          
        </>
      );
    };
    
    export default UserDetailsPage;