import { cn } from "@/lib/utils";
import { TFunction } from "i18next";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";

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
    color: "bg-orange-500",
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
  return (
    <div className="min-h-screen flex flex-col">
      <nav
        className={cn(
          "h-20 flex flex-row justify-between items-center px-10",
          config[type].color
        )}
      >
        <div className="text-2xl font-bold">{t("logoPlaceholder")}</div>
        <div className="flex flex-row gap-5">
          {[...links, ...fixedLinks(t)].map((link, idx) => (
            <NavLink
              key={link.path + idx}
              to={link.path}
              className={cn("px-2 py-1 rounded-md", colors.hover)}
            >
              {link.label}
            </NavLink>
          ))}
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
