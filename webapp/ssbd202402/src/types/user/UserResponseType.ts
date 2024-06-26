export type UserResponse = {
  blocked: boolean;
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  login: string;
  language: string;
  lastFailedLogin?: string;
  lastSuccessfulLogin?: string;
  lastFailedLoginIP?: string;
  lastSuccessfulLoginIP?: string;
  timezone?: string;
  roles: string[];
  theme: string;
};
