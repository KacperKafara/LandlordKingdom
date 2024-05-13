import { decodeJwt } from "@/utils/jwt";
import { create } from "zustand";

type UserStore = {
  token?: string;
  id?: string;
  roles?: string[];
  setToken: (token: string) => void;
  clearToken: () => void;
  activeRole?: string;
};

const LSToken = localStorage.getItem("token");

export const useUserStore = create<UserStore>((set) => ({
  token: LSToken === null ? undefined : LSToken,
  id: LSToken === null ? undefined : decodeJwt(LSToken).sub,
  roles: LSToken === null ? undefined : decodeJwt(LSToken).authorities,
  activeRole: LSToken === null ? undefined : decodeJwt(LSToken).authorities[0],
  setToken: (token: string) =>
    set(() => {
      const payload = decodeJwt(token);
      localStorage.setItem("token", token);
      return {
        token,
        id: payload.sub,
        roles: payload.authorities,
      };
    }),
  clearToken: () =>
    set(() => {
      localStorage.removeItem("token");
      return { token: undefined, id: undefined, roles: undefined };
    }),
}));
