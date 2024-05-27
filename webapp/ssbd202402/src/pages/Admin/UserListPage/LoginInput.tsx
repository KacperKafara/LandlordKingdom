import { FC, useState } from "react";
import { Input } from "@/components/ui/input";
import { useDebounce } from "use-debounce";
import { useAutocompletionQuery } from "@/data/useAutocompletion";

interface LoginInputProps {
  onLoginChange: (login: string) => void;
  autocompleteData?: string[];
}

const LoginInput: FC<LoginInputProps> = ({ onLoginChange }) => {
  const [loginPattern, setLoginPattern] = useState<string>("");
  const [isLoginFieldFocused, setIsLoginFieldFocused] =
    useState<boolean>(false);
  const [debouncedLoginPattern] = useDebounce(loginPattern, 200);
  const { data: autocompleteData } = useAutocompletionQuery(
    debouncedLoginPattern
  );

  const handleAutocompleteClick = (login: string) => {
    setLoginPattern(login);
    onLoginChange(login);
    setIsLoginFieldFocused(false);
  };

  return (
    <div className="relative">
      <Input
        type="text"
        value={loginPattern}
        onFocus={() => setIsLoginFieldFocused(true)}
        onBlur={() => setTimeout(() => setIsLoginFieldFocused(false), 50)}
        onChange={(e) => setLoginPattern(e.target.value)}
        placeholder=". . ."
      />
      {isLoginFieldFocused &&
        autocompleteData &&
        autocompleteData.length > 0 && (
          <ul
            className={`
              bg-color absolute z-10 mt-1 max-h-60 w-full overflow-y-auto
              rounded-md bg-secondary-foreground shadow-md
            `}
          >
            {autocompleteData.map((login) => (
              <li
                key={login}
                onMouseDown={() => handleAutocompleteClick(login)}
                className={`
                  px-4 py-2 text-primary-foreground hover:bg-secondary
                `}
              >
                {login}
              </li>
            ))}
          </ul>
        )}
    </div>
  );
};

export default LoginInput;
