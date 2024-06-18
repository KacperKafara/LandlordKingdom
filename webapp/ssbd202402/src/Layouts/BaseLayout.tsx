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
import { ModeToggle } from "@/components/ui/toggle-theme";
import { useChangeRoleView } from "@/data/useChangeRoleView";
import RoleRequestDialog from "./RoleRequestDialog";
import { useDialogStore } from "@/store/dialogStore";

export type NavigationLink = {
  path: string;
  label: string;
};
export type LayoutType = "admin" | "tenant" | "owner" | "me";

type BaseLayoutProps = {
  type: LayoutType;
  children: React.ReactElement;
  links?: NavigationLink[];
};

const config = {
  admin: {
    footer: "bg-red-500",
    nav: "border-b-4 border-red-500",
    hover: "hover:bg-red-500 hover:text-black",
    accentColor: "bg-red-500 text-black",
  },
  tenant: {
    footer: "bg-green-500",
    nav: "border-b-4 border-green-500",
    hover: "hover:bg-green-500 hover:text-black",
    accentColor: "bg-green-500",
  },
  owner: {
    footer: "bg-blue-500",
    nav: "border-b-4 border-blue-500",
    hover: "hover:bg-blue-500 hover:text-black",
    accentColor: "bg-blue-500",
  },
  me: {
    footer: "bg-purple-500",
    nav: "border-b-4 border-purple-500",
    hover: "hover:bg-purple-500 hover:text-black",
    accentColor: "bg-purple-500",
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
  const { roles, activeRole, setActiveRole } = useUserStore();
  const { roleChanged } = useChangeRoleView();
  const { openDialog } = useDialogStore();

  const role_mapping: { [key: string]: string } = {
    ADMINISTRATOR: "admin",
    TENANT: "tenant",
    OWNER: "owner",
  };

  const onLogoClick = () => {
    navigate(`/${role_mapping[activeRole!]}`);
  };

  return (
    <div className="flex min-h-screen flex-col">
      {roles?.includes("TENANT") && !roles.includes("OWNER") && (
        <RoleRequestDialog />
      )}
      <nav
        className={cn(
          "flex h-20 flex-row items-center justify-between px-10",
          config[type].nav
        )}
      >
        <div className="text-2xl font-bold">
          <p onClick={() => onLogoClick()} className="hover:cursor-pointer">
            {t("logoPlaceholder")}
          </p>
        </div>
        <div className="flex flex-row items-center gap-3">
          <ModeToggle />
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button
                variant="ghost"
                className={cn("px-2 py-1 capitalize ", colors.hover)}
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
                  onClick={() => {
                    setActiveRole(role);
                    roleChanged({ role });
                  }}
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
              {roles?.includes("TENANT") && !roles.includes("OWNER") && (
                <>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={() => openDialog("roleRequest")}>
                    {t("roleRequestDialog.requestOwnerRole")}
                  </DropdownMenuItem>
                </>
              )}
            </DropdownMenuContent>
          </DropdownMenu>
          <MyAccountButton links={links} hover={colors.hover} type={type} />
        </div>
      </nav>
      <main className="flex flex-1 justify-center px-10">
        <div className="w-10/12 py-10">{children}</div>
      </main>
      <footer
        className={cn(
          "flex h-12 items-center justify-center px-10 text-xl text-black",
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
