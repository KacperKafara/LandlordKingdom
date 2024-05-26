import { FC } from "react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "./ui/dropdown-menu";
import { Button } from "./ui/button";
import { IoLanguage } from "react-icons/io5";
import { useLanguageStore } from "@/i18n/languageStore";

const LanguageSelector: FC = () => {
  const { setLanguage } = useLanguageStore();
  return (
    <DropdownMenu>
      <DropdownMenuTrigger
        className="absolute right-1 top-1 w-fit self-end"
        asChild
      >
        <Button variant="ghost">
          <IoLanguage className="h-5 w-5" />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <DropdownMenuItem
          onClick={() => {
            setLanguage("en");
          }}
        >
          English
        </DropdownMenuItem>
        <DropdownMenuItem
          onClick={() => {
            setLanguage("pl");
          }}
        >
          Polski
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export default LanguageSelector;
