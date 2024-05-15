import { cn } from "@/lib/utils";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";
import { Role, useUserStore } from "@/store/userStore";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import MyAccountButton from "./MyAccountButton";
import { IoMdArrowDropdown } from "react-icons/io";

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
    footer: "bg-orange-500",
    nav: "border-b-4 border-orange-500",
    hover: "hover:bg-orange-300",
    accentColor: "bg-orange-300",
  },
  tenant: {
    footer: "bg-green-500",
    nav: "border-b-4 border-green-500",
    hover: "hover:bg-green-300",
    accentColor: "bg-green-300",
  },
  owner: {
    footer: "bg-blue-500",
    nav: "border-b-4 border-blue-500",
    hover: "hover:bg-blue-300",
    accentColor: "bg-blue-300",
  },
} satisfies {
  [key in LayoutType]: {
    nav: string;
    footer: string;
    hover: string;
    accentColor: string;
  };
};

const BaseLayout: FC<BaseLayoutProps> = ({ children, type, links = [] }) => {
  const { t, i18n } = useTranslation();
  const colors = config[type];
  const navigate = useNavigate();
  const { activeRole, setActiveRole } = useUserStore();
  const { roles } = useUserStore();

  const role_mapping: { [key: string]: string } = {
    ADMINISTRATOR: "admin",
    TENANT: "tenant",
    OWNER: "owner",
  };

  const onLogoClick = () => {
    navigate(`/${role_mapping[activeRole!]}`);
  };

  return (
    <div className="min-h-screen flex flex-col">
      <nav
        className={cn(
          "h-20 flex flex-row justify-between items-center px-10",
          config[type].nav
        )}
      >
        <div className="text-2xl font-bold">
          <p onClick={() => onLogoClick()} className="hover:cursor-pointer">
            {t("logoPlaceholder")}
          </p>
        </div>
        <div className="flex flex-row gap-3 items-center">
          {links.map((link, idx) => (
            <NavLink
              key={link.path + idx}
              to={link.path}
              className={({ isActive }) =>
                cn(
                  "px-2 py-1 rounded-md",
                  colors.hover,
                  isActive && colors.accentColor
                )
              }
            >
              {link.label}
            </NavLink>
          ))}
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button
                variant="ghost"
                className={cn("px-2 py-1 capitalize", colors.hover)}
              >
                {i18n.exists(`roles.${activeRole?.toLowerCase()}`)
                  ? t(`roles.${activeRole?.toLowerCase() as Role}`)
                  : ""}
                <IoMdArrowDropdown />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent className="w-56">
              <DropdownMenuLabel>{t("navLinks.roles")}</DropdownMenuLabel>
              <DropdownMenuSeparator />
              {roles?.map((role, idx) => (
                <DropdownMenuItem
                  onClick={() => setActiveRole(role)}
                  asChild
                  key={idx}
                >
                  <NavLink to={`/${role_mapping[role]}`}>
                    {i18n.exists(`roles.${role.toLowerCase()}`)
                      ? t(`roles.${role.toLowerCase() as Role}`)
                      : ""}
                  </NavLink>
                </DropdownMenuItem>
              ))}
            </DropdownMenuContent>
          </DropdownMenu>
          <MyAccountButton hover={colors.hover} />
        </div>
      </nav>
      <main className="flex-1 px-10 flex justify-center">{children}</main>
      <footer
        className={cn(
          "h-12 flex justify-center items-center text-xl px-10",
          config[type].footer
        )}
      >
        <span className="mr-2">
          {i18n.exists(`roles.${activeRole?.toLowerCase()}`)
            ? t(`roles.${activeRole?.toLowerCase() as Role}`)
            : ""}
        </span>
        {t("footer")}
      </footer>
    </div>
  );
};

export default BaseLayout;
