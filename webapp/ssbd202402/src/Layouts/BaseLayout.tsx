import { cn } from "@/lib/utils";
import { FC, useState } from "react";
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
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerTrigger,
} from "@/components/ui/drawer";
import { GiHamburgerMenu } from "react-icons/gi";

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
  const [open, setOpen] = useState(false);
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
        <div className="block text-xl xl:hidden">
          <Drawer direction="left" onOpenChange={setOpen} open={open}>
            <DrawerTrigger onClick={() => setOpen(true)}>
              <GiHamburgerMenu />
            </DrawerTrigger>
            <DrawerContent className="h-screen w-2/5 min-w-48 rounded-none pl-5">
              <div className="flex flex-col gap-2">
                <DrawerClose className="self-end">
                  <Button
                    onClick={() => setOpen(false)}
                    variant="ghost"
                    className="h-4 w-2 hover:bg-inherit"
                  >
                    X
                  </Button>
                </DrawerClose>
                {links.map((link, idx) => (
                  <NavLink
                    key={idx}
                    to={link.path}
                    className={cn("block w-full hover:bg-accent")}
                    onClick={() => setOpen(false)}
                  >
                    {i18n.exists(`navLinks.${link.label}`)
                      ? //  @ts-expect-error error handled
                        t(`navLinks.${link.label}`)
                      : link.label}
                  </NavLink>
                ))}
              </div>
            </DrawerContent>
          </Drawer>
        </div>
        <div className="hidden text-2xl font-bold xl:block">
          <p onClick={() => onLogoClick()} className="hover:cursor-pointer">
            {t("logoPlaceholder")}
          </p>
        </div>
        <div className="flex flex-row items-center gap-3">
          <div className="hidden gap-3 xl:flex">
            {links.map((link, idx) => (
              <NavLink
                key={link.path + idx}
                to={link.path}
                className={({ isActive }) =>
                  cn(
                    "rounded-md px-2 py-1",
                    colors.hover,
                    isActive && colors.accentColor
                  )
                }
              >
                {i18n.exists(`navLinks.${link.label}`)
                  ? //  @ts-expect-error error handled
                    t(`navLinks.${link.label}`)
                  : link.label}
              </NavLink>
            ))}
          </div>
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
          <MyAccountButton hover={colors.hover} />
        </div>
      </nav>
      <main className="flex-1 px-10">{children}</main>
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
