import { cn } from "@/lib/utils";
import { TFunction } from "i18next";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";
import { useUserStore } from "@/store/userStore";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"

export type NavigationLink = {
  path: string;
  label: string;
};
type LayoutType = "admin" | "tenant" | "owner";

type BaseLayoutProps = {
  type: LayoutType;
  children: React.ReactElement;
  links?: NavigationLink[];
};

const config = {
  admin: {
    color: "border-b-4 border-orange-500",
    hover: "hover:bg-orange-300",
  },
  tenant: {
    color: "bg-green-500",
    hover: "hover:bg-green-300",
  },
  owner: {
    color: "bg-blue-500",
    hover: "hover:bg-blue-300",
  },
} satisfies {
    [key in LayoutType]: {
      color: string;
      hover: string;
    };
  };

const fixedLinks: (t: TFunction) => NavigationLink[] = (t) => [
  { path: "/account", label: t("navLinks.account") },
];

const BaseLayout: FC<BaseLayoutProps> = ({ children, type, links = [] }) => {
  const { t } = useTranslation();
  const colors = config[type];
  const navigate = useNavigate();
  const userStore = useUserStore();
  const { roles } = useUserStore();
  const handleLoginButtonClick = () => {
    userStore.clearToken();
    navigate("/login");
  };
  const role_mapping: { [key: string]: string } = {
    "ADMINISTRATOR": "admin",
    "TENANT": "tenant",
    "OWNER": "owner",
  }

  const onRoleItemClick = (role: string) => {
    userStore.activeRole = role;
    console.log(userStore.activeRole);
  }
  const onLogoClick = () => {
    navigate(`/${role_mapping[userStore.activeRole!]}`);
  }

  return (
    <div className="min-h-screen flex flex-col">
      <nav
        className={cn(
          "h-20 flex flex-row justify-between items-center px-10",
          config[type].color
        )}
      >
        <div className="text-2xl font-bold">
          <p onClick={() => onLogoClick()} className="hover:cursor-pointer">
            {t("logoPlaceholder")}
          </p>

        </div>
        <div className="flex flex-row gap-5 items-center">
          {[...links, ...fixedLinks(t)].map((link, idx) => (
            <NavLink
              key={link.path + idx}
              to={link.path}
              className={cn("px-2 py-1 rounded-md", colors.hover)}
            >
              {link.label}
            </NavLink>
          ))}
          <button
            onClick={handleLoginButtonClick}
            className={cn("px-2 py-1 rounded-md", colors.hover)}
          >
            {t("navLinks.signOut")}
          </button>
          <DropdownMenu>
            <DropdownMenuTrigger className={cn(" px-2 py-1", colors.hover)} asChild>
              <Button variant="ghost">{userStore.activeRole}</Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent className="w-56">
              <DropdownMenuLabel>{t("navLinks.roles")}</DropdownMenuLabel>
              <DropdownMenuSeparator />
              {
                roles?.map((role, idx) => (
                  <DropdownMenuItem onClick={() => onRoleItemClick(role)} asChild key={idx}>
                    <NavLink to={`/${role_mapping[role]}`}>{role}</NavLink>
                  </DropdownMenuItem>
                ))
              }
            </DropdownMenuContent>
          </DropdownMenu>
        </div>

      </nav>
      <main className="flex-1 px-10">{children}</main>
      <footer
        className={cn(
          "h-12 flex justify-center items-center text-xl px-10",
          config[type].color
        )}
      >
        {t("footer")}
      </footer>
    </div>
  );
};

export default BaseLayout;
