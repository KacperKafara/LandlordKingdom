export type DetailedUserResponse = {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  login: string;
  language: string;
  lastSuccessfulLogin: string;
  lastFailedLogin: string;
  timezone: string;
  blocked: boolean;
  verified: boolean;
  active: boolean;
  roles: string[];
};
