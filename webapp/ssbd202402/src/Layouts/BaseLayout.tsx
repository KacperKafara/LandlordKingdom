import { cn } from "@/lib/utils";
import { FC } from "react";

type LayoutType = "admin" | "tenant" | "owner";

type BaseLayoutProps = {
  type: LayoutType;
  children: React.ReactElement;
};

const config = {
  admin: {
    color: "bg-red-700",
  },
  tenant: {
    color: "green",
  },
  owner: {
    color: "blue",
  },
} satisfies {
  [key in LayoutType]: {
    color: string;
  };
};

const BaseLayout: FC<BaseLayoutProps> = ({ children, type }) => {
  return (
    <div>
      <header className={cn("h-20", config[type].color)}></header>
      <main>{children}</main>
      <footer className={cn("h-10", config[type].color)}>
        Landlord Kingdom SSBD202402
      </footer>
    </div>
  );
};

export default BaseLayout;
