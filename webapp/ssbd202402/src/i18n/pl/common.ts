import { Role } from "@/store/userStore";

const error = {
  baseTitle: "Wystąpił błąd",
  baseDescription: "Coś poszło nie tak...",
  internalServerErrorDescription:
    "Ups! Coś poszło nie tak po naszej stronie. Proszę spróbuj ponownie później.",
};

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
  codeLengthMessage: "Kod uwierzytelniania dwufazowego musi mieć 8 cyfr",
  codeDescription: "Wprowaź kod uwierzytelniania dwufazowego",
  changeLanguage: "Język",
  submit: "Wyślij",
  backToLoginForm: "Wróć do formularza logowania",
  tokenError: {
    title: "Błąd danych",
    description: "Wprowadzono niepoprawny żeton",
  },
  googleLoginButton: "Zaloguj się z Google",
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
  active: "Aktywny",
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
  changePasswordToastDescriptionTokenNotValid:
    "Podany żeton nie jest poprawny, wymagane ponowne wysłanie maila.",
  changePasswordToastDescriptionForbidden: "Jesteś zablokowany.",
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
  resetUserPasswordToastDescriptionNotFound: "Podany użytkownik nie istanieje.",
  resetUserPasswordToastDescriptionForbidden:
    "Użytkownik jest nieaktywowany albo zablokowany.",
  resetUserEmailAction: "Zaktualizuj adres email",
  resetUserEmailSuccess:
    "Wiadomość email do zmiany adresu została wysłana na adres użytkownika",
  resetUserEmailError: "Błąd podczas inicjalizacji zmiany adresu email  ",
  updateEmailAddressTitle:
    "Czy na pewno chcesz zaktualizować adres email użytkownika?",
  updateEmailAddressDescription:
    "Na adres email użytkownika zostanie wysłany link do zmiany adresu email",
  updateEmailAddress: "Zaktualizuj adres email",
};

const block = {
  blockUserAction: "Zablokuj",
  unblockUserAction: "Odblokuj",
  toast: {
    title: {
      success: "Operacja powiodła się",
      fail: "Operacja nie powiodła się",
    },

    description: {
      blockSuccess: "Użytkownik pomyślnie zablokowany",
      unblockSuccess: "Użytkownik pomyślnie odblokowany",
      notFound: "Podany użytkownik nie istnieje",
      alreadyBlocked: "Użytkownik jest już zablokowany.",
      alreadyUnblocked: "Użytkownik jest już odblokowany",
      fail: "Coś poszło nie tak...",
    },
  },
};

const userFilter = {
  yes: "Tak",
  no: "Nie",
  both: "Oba",
  verified: "Zweryfikowany",
  blocked: "Zablokowany",
  login: "Login",
  email: "Email",
  submit: "Filtruj",
  role: "Rola",
  all: "Wszystkie",
  tenant: "Najemca",
  owner: "Właściciel",
  administrator: "Administrator",
};

const pageChanger = {
  numberOfElements: "Liczba elementów",
  page: "Strona",
  of: "z",
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
  updateUserData: "Aktualizuj",
};

const updateDataForm = {
  error: "Błąd podczas aktualizacji danych",
  success: "Dane zaktualizowane",
  precondinationFailed: "Nie pracujesz na najnowszych danych",
  triggerButton: "Aktualizuj dane",
  title: "Aktualizacja danych użytkownika",
  firstName: "Imię",
  lastName: "Nazwisko",
  language: "Język",
  updateButton: "Aktualizuj",
  updateUserData: "Aktualizuj dane użytkownika",
};

const mePage = {
  accountInfo: "Dane użytkownika",
  updateEmailAddress: "Zaktualizuj adres email",
  updateEmailAddressTitle: "Czy na pewno chcesz zaktualizować adres email?",
  updateEmailAddressDescription:
    "Na przypisany do konta adres email zostanie wysłany link do formularza zmiany adresu email",
  title: "Moje konto",
  basicInformation: "Podstawowe informacje",
  firstNameLabel: "Imię",
  lastNamelabel: "Nazwisko",
  emailLabel: "Email",
  lastSuccessfullLoginDateLabel: "Data ostatniego udanego logowania",
  lastSuccessfillLoginIPLabel: "Adres ip ostatniego udanego logowania",
  lastFailedfullLoginDateLabel: "Data ostatniego nieudanego logowania",
  lastFailedfillLoginIPLabel: "Adres ip ostatniego nieudanego logowania",
  updateData: "Zmień swoje dane",
  changeEmail: "Zmień swój email",
  changePassword: "Zmień swoje hasło",
  changeEmailDescription:
    "Naciśnij przycisk, aby otrzymać wiadomość email z linkiem do zmiany adresu email.",
};

const registerSuccessPage = {
  title: "Dziękujemy za stworzenie konta",
  description:
    "Wysłaliśmy ci email z linkiem slużącym do aktywacji konta. Użyj tego linku aby zweryfikować swój adres email. Dopóki nie zweryfikujesz adresu, nie możesz się zalogować.",
  loginButton: "Wróc do strony logowania",
};

const validation = {
  characters: "znak(i/ów)",
  minLength: "Pole musi zawierać minimalnie",
  maxLength: "Pole musi zawierać maksymalnie",
};

const roles = {
  administrator: "Administrator",
  tenant: "Najemnca",
  owner: "Właściciel",
} satisfies {
  [key in Role]: string;
};

const homePage = {
  manageProperties: "Zarządzaj swoimi nieruchomościami z łatwością",
  signIn: "Zaloguj się",
  signUp: "Zarejestruj się",
  or: "lub",
};

export default {
  error,
  homePage,
  common,
  roles,
  registerSuccessPage,
  navLinks,
  loginPage,
  registerPage,
  resetPasswordForm,
  resetPasswordPage,
  changePasswordForm,
  block,
  userListPage,
  userFilter,
  userDetailsPage,
  updateDataForm,
  mePage,
  userDataPage,
  updateEmailPage,
  validation,
  pageChanger,
  sessionExpired: "Sesja wygasła",
  sessionExpiredDescription: "Twoja sesja wygasła, zaloguj się ponownie",
  footer: "Landlord Kingdom - SSBD202402",
  logoPlaceholder: "Landlord Kingdom",
  confirm: "Potwierdź",
  cancel: "Anuluj",
};
