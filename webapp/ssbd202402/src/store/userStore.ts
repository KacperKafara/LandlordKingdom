import { decodeJwt } from "@/utils/jwt";
import { create } from "zustand";

type UserStore = {
  token?: string;
  id?: string;
  roles?: string[];
  refreshToken?: string;
  activeRole?: string;
  setToken: (token: string) => void;
  setRefreshToken: (token: string) => void;
  clearRefreshToken: () => void;
  clearToken: () => void;
  setActiveRole: (role: string) => void;
};

export type Role = "administrator" | "tenant" | "owner";

export const roleMapping: Record<string, string> = {
  ADMINISTRATOR: "admin",
  TENANT: "tenant",
  OWNER: "owner",
};

export const rolePriority: Record<string, number> = {
  ADMINISTRATOR: 0,
  OWNER: 1,
  TENANT: 2,
};

const getActiveRole = (roles: string[]): string =>
  roles.sort((a, b) => rolePriority[a] - rolePriority[b]).at(0)!;

const LSToken = localStorage.getItem("token");
const LSRefreshToken = localStorage.getItem("refreshToken");
const LSActiveRole = localStorage.getItem("activeRole");

const decodedLSToken = LSToken === null ? undefined : decodeJwt(LSToken!);

export const useUserStore = create<UserStore>((set) => ({
  token: LSToken === null ? undefined : LSToken,
  id: LSToken === null ? undefined : decodedLSToken!.sub,
  roles: LSToken === null ? undefined : decodedLSToken!.authorities,
  activeRole: LSActiveRole === null ? undefined : LSActiveRole,
  refreshToken: LSRefreshToken === null ? undefined : LSRefreshToken,
  setToken: (token: string) =>
    set(() => {
      const payload = decodeJwt(token);
      localStorage.setItem("token", token);
      localStorage.setItem("activeRole", getActiveRole(payload.authorities));
      return {
        token,
        id: payload.sub,
        roles: payload.authorities,
        activeRole: getActiveRole(payload.authorities),
      };
    }),
  clearToken: () =>
    set(() => {
      localStorage.removeItem("token");
      localStorage.removeItem("activeRole");
      return {
        token: undefined,
        id: undefined,
        roles: undefined,
        activeRole: undefined,
      };
    }),
  setRefreshToken: (token: string) =>
    set(() => {
      localStorage.setItem("refreshToken", token);
      return { refreshToken: token };
    }),
  clearRefreshToken: () =>
    set(() => {
      localStorage.removeItem("refreshToken");
      return { refreshToken: undefined };
    }),
  setActiveRole: (role: string) =>
    set(() => {
      localStorage.setItem("activeRole", role);
      return {
        activeRole: role,
      };
    }),
}));
