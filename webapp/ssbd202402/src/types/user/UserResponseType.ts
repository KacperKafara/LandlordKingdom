export type UserResponse = {
  blocked: any;
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
  roles: string[];
};
