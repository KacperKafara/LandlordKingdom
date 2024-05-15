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

const LSToken = localStorage.getItem("token");
const LSRefreshToken = localStorage.getItem("refreshToken");

export const useUserStore = create<UserStore>((set) => ({
  token: LSToken === null ? undefined : LSToken,
  id: LSToken === null ? undefined : decodeJwt(LSToken).sub,
  roles: LSToken === null ? undefined : decodeJwt(LSToken).authorities,
  activeRole: LSToken === null ? undefined : decodeJwt(LSToken).authorities[0],
  refreshToken: LSRefreshToken === null ? undefined : LSRefreshToken,
  setToken: (token: string) =>
    set(() => {
      const payload = decodeJwt(token);
      localStorage.setItem("token", token);
      return {
        token,
        id: payload.sub,
        roles: payload.authorities,
        activeRole: payload.authorities[0],
      };
    }),
  clearToken: () =>
    set(() => {
      localStorage.removeItem("token");
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
  setActiveRole: (role: string) => set(() => ({ activeRole: role })),
}));
