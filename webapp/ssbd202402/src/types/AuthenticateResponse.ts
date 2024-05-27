import { Theme } from "@/components/ThemeProvider";

export type AuthenticateResponse = {
  token: string;
  refreshToken: string;
  theme: Theme;
};
