import { FC, useState } from "react";
import { Input } from "@/components/ui/input";
import { useDebounce } from "use-debounce";
import { useAutocompletionQuery } from "@/data/useAutocompletion";
import { cn } from "@/lib/utils";

interface LoginInputProps {
  onLoginChange: (login: string) => void;
  autocompleteData?: string[];
}

const LoginInput: FC<LoginInputProps> = ({ onLoginChange }) => {
  const [loginPattern, setLoginPattern] = useState<string>("");
  const [isLoginFieldFocused, setIsLoginFieldFocused] =
    useState<boolean>(false);
  const [debouncedLoginPattern] = useDebounce(loginPattern, 100);
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
        onBlur={() => setTimeout(() => setIsLoginFieldFocused(false), 25)}
        onChange={(e) => setLoginPattern(e.target.value)}
        placeholder=". . ."
      />
      {isLoginFieldFocused &&
        autocompleteData &&
        autocompleteData.length > 0 && (
          <ul
            className={`
              absolute z-10 mt-1 max-h-60 w-full overflow-y-auto 
              rounded-md border bg-popover p-1 text-sm shadow-lg placeholder:text-muted-foreground 
            `}
          >
            {autocompleteData.map((login) => (
              <li
                key={login}
                onMouseDown={() => handleAutocompleteClick(login)}
                className={cn(
                  "relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors",
                  "hover:cursor-pointer hover:bg-accent hover:text-accent-foreground"
                )}
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
