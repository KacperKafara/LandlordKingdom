import { useQuery } from "@tanstack/react-query";
import { noTokenApi } from "./api";

type OAuth2UrlResponse = {
  url: string;
};

export const useOAuthUrl = () => {
  const { data } = useQuery({
    queryKey: ["oauthUrl"],
    queryFn: async () => {
      const response = await noTokenApi.get<OAuth2UrlResponse>(
        "/auth/oauth2/url"
      );
      return response.data;
    },
  });

  return { oAuthUrl: data };
};
