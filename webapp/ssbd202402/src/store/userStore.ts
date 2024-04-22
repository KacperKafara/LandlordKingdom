import { decodeJwt } from "@/utils/jwt";
import { create } from "zustand";

type UserStore = {
  token?: string;
  id?: string;
  roles?: string[];
  setToken: (token: string) => void;
  clear: () => void;
};

export const useUserStore = create<UserStore>((set) => ({
  token: undefined,
  id: undefined,
  roles: undefined,
  setToken: (token: string) =>
    set(() => {
      const payload = decodeJwt(token);
      return {
        token,
        id: payload.iss,
        roles: payload.authorities,
      };
    }),
  clear: () =>
    set(() => ({ token: undefined, id: undefined, roles: undefined })),
}));
