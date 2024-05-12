import { sign } from "crypto";

const loginPage = {
  forgotPassword: "Zapomniałeś hasła?",
  loginButton: "Zaloguj się",
  login: "Login",
  password: "Hasło",
  loginHeader: "Zaloguj się",
  register: "Zarejestruj się",
  loginRequired: "Login jest wymagany",
  passwordRequired: "Hasło jest wymagane",
  loginError: "Błąd logowania",
  invalidCredentials: "Nieprawidłowe dane logowania",
  loginNotAllowed: "Logowanie jest zablokowane, sprawdź email",
  tryAgain: "Spróbuj ponownie",
};

const userDetailsPage = {
  goBack: "<-- Wróć placeholder",
  firstName: "Imię",
  lastName: "Nazwisko",
  login: "Login",
  email: "Email",
  language: "Język",
  lastSuccessfulLogin: "Ostatnie udane logowanie",
  lastFailedLogin: "Ostatnie nieudane logowanie",
  blocked: "Zablokowany",
  verified: "Zweryfikowany",
  actions: "Akcje",
};

const updateEmailPage = {
  emailNotValid: "Email nie jest poprawny",
  email: "Email*",
  updateEmailButton: "Zaktualizuj email",
  updateEmailSuccess: "Email został zaktualizowany",
  updateEmailError: "Wystąpił błąd podczas aktualizacji emaila",
  updateEmailTitle: "Wprowadź nowy adres email",
  success: "Sukces",
  error: "Błąd",
};

const registerPage = {
  firstNameRequired: "Imię jest wymagane",
  lastNameRequired: "Nazwisko jest wymagane",
  emailRequired: "Email jest wymagany",
  loginRequired: "Login jest wymagany",
  passwordRequired: "Hasło musi składać się z co najmniej 8 znaków",
  passwordMatch: "Hasła muszą być takie same",
  registerHeader: "Zarejestruj się",
  firstName: "Imię*",
  lastName: "Nazwisko*",
  email: "Email*",
  login: "Login*",
  password: "Hasło*",
  confirmPassword: "Powtórz hasło*",
  registerButton: "Zarejestruj się",
  registerSuccess:
    "Link do potwierdzenia rejestracji został wysłany na podany adres email.",
  registerError: "Wystąpił błąd podczas rejestracji.",
  tryAgain: "Spróbuj ponownie",
  registerErrorIdenticalFields:
    "Użytkownik o podanym loginie lub emailu już istnieje",
};

const resetPasswordForm = {
  description:
    "Podaj adres email, na który zostanie wysłany link do zresetowania hasła",
  email: "Email*",
  emailRequired: "Email jest wymagany",
  resetPassword: "Zresetuj hasło",
  loginButton: "Wróć do formularza logowania",
  resetUserPasswordToastTitleSuccess: "Operacja powiodła się",
  resetUserPasswordToastDescriptionSuccess:
    "Link został wysłany na podany adres email",
  resetUserPasswordToastTitleFail: "Operacja nie powiodła się",
  resetUserPasswordToastDescriptionNotFound:
    "Użytkownik o podanym adresu email nie istnieje",
  resetUserPasswordToastDescriptionFail: "Coś poszło nie tak...",
};

const resetPasswordPage = {
  header: "Zresetuj hasło",
  password: "Hasło*",
  confirmPassword: "Powtórz hasło*",
  confirmButton: "Zresetuj hasło",
  homeButton: "Powrót",
  changePasswordToastTitleSuccess: "Operacja powiodła się",
  changePasswordToastDescriptionSuccess: "Twoje hasło zostało zmienione",
  changePasswordToastTitleFail: "Operacja nie powiodła się",
  changePasswordToastDescriptionFail: "Coś poszło nie tak...",
};

const changePasswordForm = {
  oldPassword: "Obecne hasło*",
  newPassword: "Nowe hasło*",
  confirmPassword: "Potwierdź nowe hasło*",
  submit: "Zmień",
  success: "Hasło zmieniono pomyślnie",
  errorTitle: "Wystąpił błąd",
  errorDescriptionNotFound: "Nie znaleziono użytkownika",
  errorDescriptionBadRequest: "Wprowadzono niepoprawne hasło",
  alertDialogTitle: "Potwierdzenie zmiany hasła",
  alertDialogDescription: "Czy na pewno chcesz zmienić hasło?",
  alertDialogCancel: "Nie",
  alertDialogAction: "Tak",
};

const userListPage = {
  firstName: "Imię",
  lastName: "Nazwisko",
  login: "Login",
  email: "Email",
  actions: "Akcje",
  viewDetails: "Szczegóły",
  resetUserPasswordAction: "Zresetuj hasło",
  resetUserPasswordTitle: "Zresetuj hasło użytkownika",
  resetUserPasswordDescription:
    "Czy na pewno chcesz zresetować hasło użytkownika ",
  resetUserPasswordToastTitleSuccess: "Operacja powiodła się",
  resetUserPasswordToastDescriptionSuccess:
    "Do użytkownika została wysłana wiadomość email",
  resetUserPasswordToastTitleFail: "Operacja nie powiodła się",
  resetUserPasswordToastDescriptionFail: "Coś poszło nie tak...",
  resetUserEmailAction: "Zaktualizuj adres email",
  resetUserEmailSuccess: "Wiadomość email do zmiany adresu została wysłana na adres użytkownika",
  resetUserEmailError: "Błąd podczas inicjalizacji zmiany adresu email  ",
};

const common = {
  yes: "tak",
  no: "nie",
};

const navLinks = {
  account: "Moje konto",
  signOut: "Wyloguj",
  users: "Użytkownicy",
  roles: "Zmień poziom dostępu",
};

const userDataPage = {
  firstNameNotEmpty: "Imię nie może być puste",
  lastNameNotEmpty: "Imię nie może być puste",
  firstName: "Imię*",
  lastName: "Nazwisko*",
  language: "Język*",
  error: "Błąd",
  success: "Dane zaktualizowane",
};

const mePage = {
  accountInfo: "Dane użytkownika",
  updateEmailAddress: "Zaktualizuj adres email",
};

export default {
  common,
  navLinks,
  loginPage,
  registerPage,
  resetPasswordForm,
  resetPasswordPage,
  changePasswordForm,
  userListPage,
  userDetailsPage,
  mePage,
  userDataPage,
  updateEmailPage,
  footer: "Landlord Kingdom - SSBD202402",
  logoPlaceholder: "Landlord Kingdom",
  confirm: "Potwierdź",
  cancel: "Anuluj",
};
